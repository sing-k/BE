package com.project.singk.domain.comment.service;

import com.project.singk.domain.comment.controller.port.RecommendCommentService;
import com.project.singk.domain.comment.controller.response.CommentResponse;
import com.project.singk.domain.comment.domain.RecommendComment;
import com.project.singk.domain.comment.domain.CommentCreate;
import com.project.singk.domain.comment.domain.CommentSimplified;
import com.project.singk.domain.comment.service.port.RecommendCommentRepository;
import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.domain.like.service.port.RecommendCommentLikeRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.service.port.RecommendPostRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecommendCommentServiceImpl implements RecommendCommentService {

    private final RecommendCommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final RecommendPostRepository postRepository;
    private final RecommendCommentRepository recommendCommentRepository;
    private final RecommendCommentLikeRepository recommendCommentLikeRepository;
    private final S3Repository s3Repository;

    @Override
    public List<CommentResponse> getRecommendComments(Long memberId, Long postId) {
        List<CommentSimplified> comments = commentRepository.findAllByPostId(postId);

        Map<Long, CommentResponse> m = new HashMap<>();
        List<CommentResponse> result = new ArrayList<>();
        comments.forEach(comment -> {
                    CommentResponse c = CommentResponse.recommendType(
                            comment,
                            recommendCommentLikeRepository.existsByMemberIdAndCommentId(memberId, comment.getId()),
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
        RecommendPost post = postRepository.getById(postId);
        post = post.updateComments(post.getComments() + 1);
        post = postRepository.save(post);

        RecommendComment recommendComment = commentRepository.save(RecommendComment.from(
                commentCreate,
                member,
                post,
                parentId
        ));
        recommendComment = recommendCommentRepository.save(recommendComment);

        return PkResponseDto.of(recommendComment.getId());
    }

    @Override
    public PkResponseDto updateRecommendComment(Long memberId, Long commentId, CommentCreate commentCreate) {
        RecommendComment recommendComment = commentRepository.getById(commentId);

        if (!recommendComment.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.FORBIDDEN_COMMENT);
        }

        recommendComment = recommendComment.update(commentCreate);
        recommendComment = commentRepository.save(recommendComment);

        return PkResponseDto.of(recommendComment.getId());
    }

    @Override
    public void deleteRecommendComment(Long memberId, Long commentId) {
        RecommendComment recommendComment = commentRepository.getById(commentId);

        if (!recommendComment.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.FORBIDDEN_COMMENT);
        }

        commentRepository.deleteById(commentId);
    }

}
