package com.project.singk.domain.comment.service;

import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.domain.ActivityType;
import com.project.singk.domain.activity.service.port.ActivityHistoryRepository;
import com.project.singk.domain.comment.controller.port.RecommendCommentService;
import com.project.singk.domain.comment.controller.response.CommentResponse;
import com.project.singk.domain.comment.domain.RecommendComment;
import com.project.singk.domain.comment.domain.CommentCreate;
import com.project.singk.domain.comment.domain.CommentSimplified;
import com.project.singk.domain.comment.service.port.RecommendCommentRepository;
import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.domain.like.controller.port.RecommendLikeService;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.service.port.RecommendPostRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.domain.PkResponseDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class RecommendCommentServiceImpl implements RecommendCommentService {

    private final RecommendCommentRepository recommendCommentRepository;
    private final MemberRepository memberRepository;
    private final RecommendPostRepository recommendPostRepository;
    private final S3Repository s3Repository;
    private final RecommendLikeService recommendLikeService;
    private final ActivityHistoryRepository activityHistoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getRecommendComments(Long memberId, Long postId) {
        List<CommentSimplified> comments = recommendCommentRepository.findAllByPostId(postId);

        Map<Long, CommentResponse> m = new HashMap<>();
        List<CommentResponse> result = new ArrayList<>();
        comments.forEach(comment -> {
                    CommentResponse c = CommentResponse.recommendType(
                            comment,
                            recommendLikeService.getCommentLike(memberId, comment.getId()),
                            s3Repository.getPreSignedGetUrl(comment.getMember().getImageUrl())
                    );
                    m.put(c.getId(), c);
                    if (c.getParentId() == null) result.add(c);
                    else m.get(c.getParentId()).getChildren().add(c);
                });

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getMyRecommendComments(Long memberId, Long postId) {
        List<CommentSimplified> comments = recommendCommentRepository.findAllByPostId(postId);

        Map<Long, CommentResponse> m = new HashMap<>();
        List<CommentResponse> result = new ArrayList<>();
        comments.forEach(comment -> {
            CommentResponse c = CommentResponse.recommendType(
                    comment,
                    recommendLikeService.getCommentLike(memberId, comment.getId()),
                    s3Repository.getPreSignedGetUrl(comment.getMember().getImageUrl())
            );
            m.put(c.getId(), c);
            if (c.getParentId() == null) result.add(c);
            else m.get(c.getParentId()).getChildren().add(c);
        });

        return result;
    }

    @Override
    public PkResponseDto createRecommendComment(Long memberId, Long postId, Long parentId, CommentCreate commentCreate) {
        Member member = memberRepository.getById(memberId);

        // 게시글 댓글 수 업데이트
        RecommendPost post = recommendPostRepository.getById(postId);
        post = post.updateComments(post.getComments() + 1);
        post = recommendPostRepository.save(post);

        RecommendComment recommendComment = recommendCommentRepository.save(RecommendComment.from(
                commentCreate,
                member,
                post,
                parentId
        ));
        recommendComment = recommendCommentRepository.save(recommendComment);

        // 댓글 작성자 활동 점수 반영
        ActivityHistory activity = ActivityHistory.from(ActivityType.WRITE_COMMENT, member);
        activity = activityHistoryRepository.save(activity);

        // 회원 통계 반영
        MemberStatistics memberStatistics = member.getStatistics();
        memberStatistics = memberStatistics.updateActivity(activity);
        member = member.updateStatistic(memberStatistics);

        return PkResponseDto.of(recommendComment.getId());
    }

    @Override
    public PkResponseDto updateRecommendComment(Long memberId, Long commentId, CommentCreate commentCreate) {
        RecommendComment recommendComment = recommendCommentRepository.getById(commentId);

        if (!recommendComment.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.FORBIDDEN_COMMENT);
        }

        recommendComment = recommendComment.update(commentCreate);
        recommendComment = recommendCommentRepository.save(recommendComment);

        return PkResponseDto.of(recommendComment.getId());
    }

    @Override
    public void deleteRecommendComment(Long memberId, Long postId, Long commentId) {
        RecommendComment recommendComment = recommendCommentRepository.getById(commentId);
        Member member = memberRepository.getById(memberId);

        if (!recommendComment.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.FORBIDDEN_COMMENT);
        }

        RecommendPost recommendPost = recommendPostRepository.getById(postId);
        recommendPost = recommendPost.updateComments(recommendPost.getComments() - 1);
        recommendPost = recommendPostRepository.save(recommendPost);

        recommendCommentRepository.deleteById(commentId);

        // 댓글 작성자 활동 점수 반영
        ActivityHistory activity = ActivityHistory.from(ActivityType.DELETE_COMMENT, member);
        activity = activityHistoryRepository.save(activity);

        // 회원 통계 반영
        MemberStatistics memberStatistics = member.getStatistics();
        memberStatistics = memberStatistics.updateActivity(activity);
        member = member.updateStatistic(memberStatistics);

        member = memberRepository.save(member);
    }

}
