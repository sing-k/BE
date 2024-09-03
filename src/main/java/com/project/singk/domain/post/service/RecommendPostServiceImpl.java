package com.project.singk.domain.post.service;

import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.domain.ActivityType;
import com.project.singk.domain.activity.service.port.ActivityHistoryRepository;
import com.project.singk.domain.album.domain.AlbumImage;
import com.project.singk.domain.album.service.port.AlbumImageRepository;
import com.project.singk.domain.comment.service.port.RecommendCommentRepository;
import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.domain.common.service.port.UUIDHolder;
import com.project.singk.domain.like.controller.port.RecommendLikeService;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.controller.port.RecommendPostService;
import com.project.singk.domain.post.controller.response.RecommendPostResponse;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.domain.RecommendPostCreate;
import com.project.singk.domain.post.domain.RecommendType;
import com.project.singk.domain.post.domain.RecommendPostUpdate;
import com.project.singk.domain.post.service.port.RecommendPostRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.OffsetPageResponse;
import com.project.singk.global.domain.PkResponseDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class RecommendPostServiceImpl implements RecommendPostService {
    private final RecommendPostRepository recommendPostRepository;
    private final RecommendCommentRepository recommendCommentRepository;
    private final MemberRepository memberRepository;
    private final AlbumImageRepository albumImageRepository;
    private final S3Repository s3Repository;
    private final UUIDHolder uuidHolder;
    private final RecommendLikeService recommendLikeService;
    private final ActivityHistoryRepository activityHistoryRepository;

    @Override
    public PkResponseDto createRecommendPost(Long memberId, RecommendPostCreate recommendPostCreate, MultipartFile image) {
        Member member = memberRepository.getById(memberId);

        RecommendType type = RecommendType.valueOf(recommendPostCreate.getType());

        // 추천 타입에 따른 객체 생성
        RecommendPost post = null;
        if (RecommendType.IMAGE.equals(type)) {

            // 이미지 체크
            if (image == null || image.isEmpty()) {
                throw new ApiException(AppHttpStatus.INVALID_FILE);
            }

            // S3에 이미지 저장
            String key = "img/" + uuidHolder.randomUUID();
            s3Repository.putObject(key, image);
            post = RecommendPost.imageType(recommendPostCreate, key, member);
        }
        else if (RecommendType.ALBUM.equals(type)) {
            // 앨범 커버 이미지 URL
            List<AlbumImage> images = albumImageRepository.findAllByAlbumId(recommendPostCreate.getLink());
            String imageUrl = images.get(0).getImageUrl() + "/" + recommendPostCreate.getLink();
            post = RecommendPost.albumType(recommendPostCreate, imageUrl, member);
        }
        else if (RecommendType.YOUTUBE.equals(type)) {
            // 유튜브 링크 URL
            post = RecommendPost.youtubeType(recommendPostCreate, member);
        }

        RecommendPost recommendPost = recommendPostRepository.save(post);

        // 게시글 작성자 활동 점수 반영
        ActivityHistory activity = ActivityHistory.from(ActivityType.WRITE_POST, member);
        activity = activityHistoryRepository.save(activity);

        // 회원 통계 반영
        MemberStatistics memberStatistics = member.getStatistics();
        memberStatistics = memberStatistics.updateActivity(activity);
        memberStatistics = memberStatistics.updateRecommendPost(false);
        member = member.updateStatistic(memberStatistics);

        member = memberRepository.save(member);

        return PkResponseDto.of(recommendPost.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public RecommendPostResponse getRecommendPost(Long memberId, Long postId){
        RecommendPost recommendPost = recommendPostRepository.getById(postId);

        return RecommendPostResponse.from(
                recommendPost,
                recommendLikeService.getPostLike(memberId, postId),
                getLinkByRecommend(recommendPost),
                s3Repository.getPreSignedGetUrl(recommendPost.getMember().getImageUrl())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public OffsetPageResponse<RecommendPostResponse> getRecommendPosts(Long memberId, int offset, int limit, String sort, String filter, String keyword) {
        Page<RecommendPost> posts = recommendPostRepository.findAll(offset, limit, sort, filter, keyword);
        return OffsetPageResponse.of(
                offset,
                limit,
                (int) posts.getTotalElements(),
                posts.stream()
                        .map(post -> {
                            String link = getLinkByRecommend(post);
                            return RecommendPostResponse.from(
                                    post,
                                    recommendLikeService.getPostLike(memberId, post.getId()),
                                    link,
                                    s3Repository.getPreSignedGetUrl(post.getMember().getImageUrl())
                            );
                        })
                        .toList()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public OffsetPageResponse<RecommendPostResponse> getMyRecommendPosts(Long memberId, int offset, int limit) {
        Page<RecommendPost> posts = recommendPostRepository.findAllByMemberId(memberId, offset, limit);
        return OffsetPageResponse.of(
                offset,
                limit,
                (int) posts.getTotalElements(),
                posts.stream()
                        .map(post -> {
                            String link = getLinkByRecommend(post);
                            return RecommendPostResponse.from(
                                    post,
                                    recommendLikeService.getPostLike(memberId, post.getId()),
                                    link,
                                    s3Repository.getPreSignedGetUrl(post.getMember().getImageUrl())
                            );
                        })
                        .toList()
        );
    }

    private String getLinkByRecommend(RecommendPost post) {
        switch (post.getRecommend()) {
            case IMAGE -> {
                return s3Repository.getPreSignedGetUrl(post.getLink());
            }
            case ALBUM, YOUTUBE -> {
                return post.getLink();
            }
        }
        return null;
    }

    @Override
    public PkResponseDto updateRecommendPost(Long memberId, Long id, RecommendPostUpdate req){
        RecommendPost recommendPost = recommendPostRepository.getById(id);

        if (!recommendPost.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.FORBIDDEN_POST);
        }

        recommendPost = recommendPost.update(req);
        recommendPost = recommendPostRepository.save(recommendPost);

        return PkResponseDto.of(recommendPost.getId());
    }

    @Override
    public void deleteRecommendPost(Long memberId, Long postId){
        RecommendPost recommendPost = recommendPostRepository.getById(postId);
        Member member = memberRepository.getById(memberId);

        if (!recommendPost.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.FORBIDDEN_POST);
        }

        recommendCommentRepository.deleteByPostId(postId);
        recommendPostRepository.deleteById(postId);

        // 게시글 작성자 활동 점수 반영
        ActivityHistory activity = ActivityHistory.from(ActivityType.DELETE_POST, member);
        activity = activityHistoryRepository.save(activity);

        // 회원 통계 반영
        MemberStatistics memberStatistics = member.getStatistics();
        memberStatistics = memberStatistics.updateActivity(activity);
        memberStatistics = memberStatistics.updateRecommendPost(true);
        member = member.updateStatistic(memberStatistics);

        member = memberRepository.save(member);
    }

}
