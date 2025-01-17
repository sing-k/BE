package com.project.singk.domain.post.service;

import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.domain.ActivityType;
import com.project.singk.domain.activity.service.port.ActivityHistoryRepository;
import com.project.singk.domain.comment.service.port.FreeCommentRepository;
import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.domain.like.controller.port.FreeLikeService;
import com.project.singk.domain.like.service.port.FreePostLikeRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.controller.port.FreePostService;
import com.project.singk.domain.post.controller.response.FreePostResponse;
import com.project.singk.domain.post.domain.FreePostCreate;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.domain.post.service.port.FreePostRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.OffsetPageResponse;
import com.project.singk.global.domain.PkResponseDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class FreePostServiceImpl implements FreePostService {
    private final FreePostRepository freePostRepository;
    private final FreeCommentRepository freeCommentRepository;
    private final MemberRepository memberRepository;
    private final S3Repository s3Repository;
    private final FreeLikeService freeLikeService;
    private final ActivityHistoryRepository activityHistoryRepository;

    @Override
    public PkResponseDto createFreePost(Long memberId, FreePostCreate freePostCreate) {
        Member member = memberRepository.getById(memberId);

        FreePost freePost = FreePost.from(freePostCreate, member);
        freePost = freePostRepository.save(freePost);

        // 게시글 작성자 활동 점수 반영
        ActivityHistory activity = ActivityHistory.from(ActivityType.WRITE_POST, member);
        activity = activityHistoryRepository.save(activity);

        // 회원 통계 반영
        MemberStatistics memberStatistics = member.getStatistics();
        memberStatistics = memberStatistics.updateActivity(activity);
        memberStatistics = memberStatistics.updateFreePost(false);
        member = member.updateStatistic(memberStatistics);

        member = memberRepository.save(member);

        return PkResponseDto.of(freePost.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public FreePostResponse getFreePost(Long memberId, Long postId){
        FreePost freePost = freePostRepository.getById(postId);
        return FreePostResponse.from(
                freePost,
                freeLikeService.getPostLike(memberId, postId),
                s3Repository.getPreSignedGetUrl(freePost.getMember().getImageUrl())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public OffsetPageResponse<FreePostResponse> getFreePosts(Long memberId, int offset, int limit, String sort, String filter, String keyword) {
        Page<FreePost> posts = freePostRepository.findAll(offset, limit, sort, filter, keyword);
        return OffsetPageResponse.of(
                offset,
                limit,
                (int) posts.getTotalElements(),
                posts.stream()
                        .map(post -> FreePostResponse.from(
                                post,
                                freeLikeService.getPostLike(memberId, post.getId()),
                                s3Repository.getPreSignedGetUrl(post.getMember().getImageUrl())
                        ))
                        .toList()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public OffsetPageResponse<FreePostResponse> getMyFreePosts(Long memberId, int offset, int limit) {
        Page<FreePost> posts = freePostRepository.findAllByMemberId(memberId, offset, limit);
        return OffsetPageResponse.of(
                offset,
                limit,
                (int) posts.getTotalElements(),
                posts.stream()
                        .map(post -> FreePostResponse.from(
                                post,
                                freeLikeService.getPostLike(memberId, post.getId()),
                                s3Repository.getPreSignedGetUrl(post.getMember().getImageUrl())
                        ))
                        .toList()
        );
    }


    @Override
    public PkResponseDto updateFreePost(Long memberId, Long postId, FreePostCreate freePostCreate){
        FreePost freePost = freePostRepository.getById(postId);

        if (!freePost.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.FORBIDDEN_POST);
        }

        freePost = freePost.update(freePostCreate);
        freePost = freePostRepository.save(freePost);
        return PkResponseDto.of(freePost.getId());
    }

    @Override
    public void deleteFreePost(Long memberId, Long postId){
        Member member = memberRepository.getById(memberId);
        FreePost freePost = freePostRepository.getById(postId);

        if (!freePost.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.FORBIDDEN_POST);
        }
        freeCommentRepository.deleteByPostId(postId);
        freePostRepository.deleteById(postId);

        // 게시글 작성자 활동 점수 반영
        ActivityHistory activity = ActivityHistory.from(ActivityType.DELETE_POST, member);
        activity = activityHistoryRepository.save(activity);

        // 회원 통계 반영
        MemberStatistics memberStatistics = member.getStatistics();
        memberStatistics = memberStatistics.updateActivity(activity);
        memberStatistics = memberStatistics.updateFreePost(true);
        member = member.updateStatistic(memberStatistics);

        member = memberRepository.save(member);
    }
}
