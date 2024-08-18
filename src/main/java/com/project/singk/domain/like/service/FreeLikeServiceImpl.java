package com.project.singk.domain.like.service;

import com.project.singk.domain.comment.domain.FreeComment;
import com.project.singk.domain.comment.service.port.FreeCommentRepository;
import com.project.singk.domain.like.controller.port.FreeLikeService;
import com.project.singk.domain.like.domain.FreeCommentLike;
import com.project.singk.domain.like.domain.FreePostLike;
import com.project.singk.domain.like.service.port.FreeCommentLikeRepository;
import com.project.singk.domain.like.service.port.FreePostLikeRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.domain.post.service.port.FreePostRepository;
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
public class FreeLikeServiceImpl implements FreeLikeService {
    private final FreePostLikeRepository freePostLikeRepository;
    private final FreeCommentLikeRepository freeCommentLikeRepository;
    private final MemberRepository memberRepository;
    private final FreePostRepository freePostRepository;
    private final FreeCommentRepository freeCommentRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean getPostLike(Long memberId, Long postId) {
        return freePostLikeRepository.existsByMemberIdAndPostId(memberId, postId);
    }

    @Override
    public boolean getCommentLike(Long memberId, Long commentId) {
        return freeCommentLikeRepository.existsByMemberIdAndCommentId(memberId, commentId);
    }

    @Override
    public PkResponseDto createPostLike(Long memberId, Long postId) {
        // 회원 ID와 게시글 ID를 통해 좋아요 했는지 확인
        if (freePostLikeRepository.existsByMemberIdAndPostId(memberId, postId)) {
            throw new ApiException(AppHttpStatus.DUPLICATE_POST_LIKES);
        }

        Member member = memberRepository.getById(memberId);
        FreePost freePost = freePostRepository.getById(postId);

        // 자신의 게시글에는 공감 불가능
        if (freePost.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.INVALID_POST_LIKES);
        }

        // 게시글 공감수 수정
        freePost = freePost.updateLikes(freePost.getLikes() + 1);
        freePost = freePostRepository.save(freePost);

        // 게시글 공감 데이터 저장
        FreePostLike like = FreePostLike.from(member, freePost);
        like = freePostLikeRepository.save(like);

        return PkResponseDto.of(like.getId());
    }

    @Override
    public void deletePostLike(Long memberId, Long postId) {
        FreePostLike like = freePostLikeRepository.findByMemberIdAndPostId(memberId, postId)
                .orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_POST_LIKES));

        // 공감수 수정
        FreePost freePost = like.getPost();
        freePost = freePost.updateLikes(freePost.getLikes() - 1);
        freePost = freePostRepository.save(freePost);

        // 공감 데이터 삭제
        freePostLikeRepository.deleteById(like.getId());
    }

    @Override
    public PkResponseDto createCommentLike(Long memberId, Long commentId) {
        // 회원 ID와 게시글 ID를 통해 좋아요 했는지 확인
        if (freeCommentLikeRepository.existsByMemberIdAndCommentId(memberId, commentId)) {
            throw new ApiException(AppHttpStatus.DUPLICATE_COMMENT_LIKES);
        }

        Member member = memberRepository.getById(memberId);
        FreeComment comment = freeCommentRepository.getById(commentId);

        // 자신의 게시글에는 공감 불가능
        if (comment.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.INVALID_COMMENT_LIKES);
        }

        // 게시글 공감수 수정
        comment = comment.updateLikes(comment.getLikes() + 1);
        comment = freeCommentRepository.save(comment);

        // 게시글 공감 데이터 저장
        FreeCommentLike like = FreeCommentLike.from(member, comment);
        like = freeCommentLikeRepository.save(like);

        return PkResponseDto.of(like.getId());
    }

    @Override
    public void deleteCommentLike(Long memberId, Long commentId) {
        FreeCommentLike like = freeCommentLikeRepository.findByMemberIdAndCommentId(memberId, commentId)
                .orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_COMMENT_LIKES));

        // 공감수 수정
        FreeComment comment = like.getComment();
        comment = comment.updateLikes(comment.getLikes() - 1);
        comment = freeCommentRepository.save(comment);

        // 공감 데이터 삭제
        freeCommentLikeRepository.deleteById(like.getId());
    }
}
