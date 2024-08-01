package com.project.singk.domain.post.service;

import com.project.singk.domain.album.domain.AlbumImage;
import com.project.singk.domain.album.service.port.AlbumImageRepository;
import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.domain.common.service.port.UUIDHolder;
import com.project.singk.domain.like.service.port.RecommendPostLikeRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.controller.port.RecommendPostService;
import com.project.singk.domain.post.controller.response.RecommendPostListResponse;
import com.project.singk.domain.post.controller.response.RecommendPostResponse;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.domain.RecommendPostCreate;
import com.project.singk.domain.post.domain.RecommendType;
import com.project.singk.domain.post.domain.RecommendPostUpdate;
import com.project.singk.domain.post.service.port.RecommendPostRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.PageResponse;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendPostServiceImpl implements RecommendPostService {
    private final RecommendPostRepository recommendPostRepository;
    private final RecommendPostLikeRepository recommendPostLikeRepository;
    private final MemberRepository memberRepository;
    private final AlbumImageRepository albumImageRepository;
    private final S3Repository s3Repository;
    private final UUIDHolder uuidHolder;

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
            post = RecommendPost.albumType(recommendPostCreate, images.get(0).getImageUrl(), member);
        }
        else if (RecommendType.YOUTUBE.equals(type)) {
            // 유튜브 링크 URL
            post = RecommendPost.youtubeType(recommendPostCreate, member);
        }

        RecommendPost recommendPost = recommendPostRepository.save(post);

        return PkResponseDto.of(recommendPost.getId());
    }

    @Override
    public RecommendPostResponse getRecommendPost(Long memberId, Long postId){
        RecommendPost recommendPost = recommendPostRepository.getById(postId);

        return RecommendPostResponse.from(
                recommendPost,
                recommendPostLikeRepository.existsByMemberIdAndPostId(memberId, postId),
                getLinkByRecommend(recommendPost),
                s3Repository.getPreSignedGetUrl(recommendPost.getMember().getImageUrl())
        );
    }

    @Override
    public PageResponse<RecommendPostListResponse> getRecommendPosts(Long memberId, int offset, int limit, String sort, String filter, String keyword) {
        Page<RecommendPost> posts = recommendPostRepository.findAll(offset, limit, sort, filter, keyword);
        return PageResponse.of(
                offset,
                limit,
                (int) posts.getTotalElements(),
                posts.stream()
                        .map(post -> {
                            String link = getLinkByRecommend(post);
                            return RecommendPostListResponse.from(
                                    post,
                                    recommendPostLikeRepository.existsByMemberIdAndPostId(memberId, post.getId()),
                                    link,
                                    s3Repository.getPreSignedGetUrl(post.getMember().getImageUrl())
                            );
                        })
                        .toList()
        );
    }

    @Override
    public PageResponse<RecommendPostListResponse> getMyRecommendPosts(Long memberId, int offset, int limit, String sort, String filter, String keyword) {
        Page<RecommendPost> posts = recommendPostRepository.findAllByMemberId(memberId, offset, limit, sort, filter, keyword);
        return PageResponse.of(
                offset,
                limit,
                (int) posts.getTotalElements(),
                posts.stream()
                        .map(post -> {
                            String link = getLinkByRecommend(post);
                            return RecommendPostListResponse.from(
                                    post,
                                    recommendPostLikeRepository.existsByMemberIdAndPostId(memberId, post.getId()),
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

        if (!recommendPost.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.FORBIDDEN_POST);
        }

        recommendPostRepository.deleteById(postId);
    }

}
