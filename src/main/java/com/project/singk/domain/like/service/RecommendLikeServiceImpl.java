package com.project.singk.domain.like.service;

import com.project.singk.domain.comment.domain.RecommendComment;
import com.project.singk.domain.comment.service.port.RecommendCommentRepository;
import com.project.singk.domain.like.controller.port.RecommendLikeService;
import com.project.singk.domain.like.domain.RecommendCommentLike;
import com.project.singk.domain.like.domain.RecommendPostLike;
import com.project.singk.domain.like.service.port.RecommendCommentLikeRepository;
import com.project.singk.domain.like.service.port.RecommendPostLikeRepository;
import com.project.singk.domain.member.domain.Member;
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

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class RecommendLikeServiceImpl implements RecommendLikeService {
    private final RecommendPostLikeRepository recommendPostLikeRepository;
    private final RecommendCommentLikeRepository recommendCommentLikeRepository;
    private final MemberRepository memberRepository;
    private final RecommendPostRepository recommendPostRepository;
    private final RecommendCommentRepository recommendCommentRepository;

    @Override
    public boolean getPostLike(Long memberId, Long postId) {
        return recommendPostLikeRepository.existsByMemberIdAndPostId(memberId, postId);
    }

    @Override
    public boolean getCommentLike(Long memberId, Long commentId) {
        return recommendCommentLikeRepository.existsByMemberIdAndCommentId(memberId, commentId);
    }

    @Override
    public PkResponseDto createPostLike(Long memberId, Long postId) {
        // 회원 ID와 게시글 ID를 통해 좋아요 했는지 확인
        if (recommendPostLikeRepository.existsByMemberIdAndPostId(memberId, postId)) {
            throw new ApiException(AppHttpStatus.DUPLICATE_POST_LIKES);
        }

        Member member = memberRepository.getById(memberId);
        RecommendPost recommendPost = recommendPostRepository.getById(postId);

        // 자신의 게시글에는 공감 불가능
        if (recommendPost.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.INVALID_POST_LIKES);
        }

        // 게시글 공감수 수정
        recommendPost = recommendPost.updateLikes(recommendPost.getLikes() + 1);
        recommendPost = recommendPostRepository.save(recommendPost);

        // 게시글 공감 데이터 저장
        RecommendPostLike like = RecommendPostLike.from(member, recommendPost);
        like = recommendPostLikeRepository.save(like);

        return PkResponseDto.of(like.getId());
    }

    @Override
    public void deletePostLike(Long memberId, Long postId) {
        RecommendPostLike like = recommendPostLikeRepository.findByMemberIdAndPostId(memberId, postId)
                .orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_POST_LIKES));

        // 공감수 수정
        RecommendPost recommendPost = like.getPost();
        recommendPost = recommendPost.updateLikes(recommendPost.getLikes() - 1);
        recommendPost = recommendPostRepository.save(recommendPost);

        // 공감 데이터 삭제
        recommendPostLikeRepository.deleteById(like.getId());
    }

    @Override
    public PkResponseDto createCommentLike(Long memberId, Long commentId) {
        // 회원 ID와 댓글 ID를 통해 좋아요 했는지 확인
        if (recommendCommentLikeRepository.existsByMemberIdAndCommentId(memberId, commentId)) {
            throw new ApiException(AppHttpStatus.DUPLICATE_COMMENT_LIKES);
        }

        Member member = memberRepository.getById(memberId);
        RecommendComment recommendComment = recommendCommentRepository.getById(commentId);

        // 자신의 댓글에는 공감 불가능
        if (recommendComment.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.INVALID_COMMENT_LIKES);
        }

        // 댓글 공감수 수정
        recommendComment = recommendComment.updateLikes(recommendComment.getLikes() + 1);
        recommendComment = recommendCommentRepository.save(recommendComment);

        // 댓글 공감 데이터 저장
        RecommendCommentLike like = RecommendCommentLike.from(member, recommendComment);
        like = recommendCommentLikeRepository.save(like);

        return PkResponseDto.of(like.getId());
    }

    @Override
    public void deleteCommentLike(Long memberId, Long commentId) {
        RecommendCommentLike like = recommendCommentLikeRepository.findByMemberIdAndCommentId(memberId, commentId)
                .orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_COMMENT_LIKES));

        // 공감수 수정
        RecommendComment recommendComment = like.getComment();
        recommendComment = recommendComment.updateLikes(recommendComment.getLikes() - 1);
        recommendComment = recommendCommentRepository.save(recommendComment);

        // 공감 데이터 삭제
        recommendCommentRepository.deleteById(like.getId());
    }
}
