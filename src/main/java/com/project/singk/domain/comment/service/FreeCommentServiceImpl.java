package com.project.singk.domain.comment.service;

import com.project.singk.domain.comment.controller.port.FreeCommentService;
import com.project.singk.domain.comment.controller.response.CommentResponse;
import com.project.singk.domain.comment.domain.CommentCreate;
import com.project.singk.domain.comment.domain.CommentSimplified;
import com.project.singk.domain.comment.domain.FreeComment;
import com.project.singk.domain.comment.service.port.FreeCommentRepository;
import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.domain.like.service.port.FreeCommentLikeRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.domain.post.service.port.FreePostRepository;
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
public class FreeCommentServiceImpl implements FreeCommentService {

    private final FreeCommentRepository freeCommentRepository;
    private final MemberRepository memberRepository;
    private final FreePostRepository freePostRepository;
    private final FreeCommentLikeRepository freeCommentLikeRepository;
    private final S3Repository s3Repository;

    @Override
    public List<CommentResponse> getFreeComments(Long memberId, Long postId) {
        List<CommentSimplified> comments = freeCommentRepository.findAllByPostId(postId);

        Map<Long, CommentResponse> m = new HashMap<>();
        List<CommentResponse> result = new ArrayList<>();
        comments.forEach(comment -> {
            CommentResponse c = CommentResponse.freeType(
                    comment,
                    freeCommentLikeRepository.existsByMemberIdAndCommentId(memberId, comment.getId()),
                    s3Repository.getPreSignedGetUrl(comment.getMember().getImageUrl())
            );
            m.put(c.getId(), c);
            if (c.getParentId() == null) result.add(c);
            else m.get(c.getParentId()).getChildren().add(c);
        });

        return result;
    }

    @Override
    public PkResponseDto createFreeComment(Long memberId, Long postId, Long parentId, CommentCreate commentCreate) {
        Member member = memberRepository.getById(memberId);

        FreePost freePost = freePostRepository.getById(postId);
        freePost = freePost.updateComments(freePost.getComments() + 1);
        freePost = freePostRepository.save(freePost);

        FreeComment freeComment = freeCommentRepository.save(FreeComment.from(
                commentCreate,
                member,
                freePost,
                parentId));

        return PkResponseDto.of(freeComment.getId());
    }

    @Override
    public PkResponseDto updateFreeComment(Long memberId, Long commentId, CommentCreate commentCreate) {
        FreeComment freeComment = freeCommentRepository.getById(commentId);

        if (!freeComment.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.FORBIDDEN_COMMENT);
        }

        freeComment = freeComment.update(commentCreate);
        freeComment = freeCommentRepository.save(freeComment);

        return PkResponseDto.of(freeComment.getId());
    }

    @Override
    public void deleteFreeComment(Long memberId, Long commentId) {
        FreeComment freeComment = freeCommentRepository.getById(commentId);

        if (!freeComment.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.FORBIDDEN_COMMENT);
        }

        freeCommentRepository.deleteById(commentId);
    }

}

