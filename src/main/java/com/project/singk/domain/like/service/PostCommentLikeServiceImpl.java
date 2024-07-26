package com.project.singk.domain.like.service;

import com.project.singk.domain.comment.domain.PostComment;
import com.project.singk.domain.comment.service.port.PostCommentRepository;
import com.project.singk.domain.like.controller.port.PostCommentLikeService;
import com.project.singk.domain.like.domain.PostCommentLike;
import com.project.singk.domain.like.service.port.PostCommentLikeRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCommentLikeServiceImpl implements PostCommentLikeService {
    private final PostCommentLikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostCommentRepository commentRepository;

    @Override
    public PkResponseDto addLike(Long memberId,Long commentId){
        Member member = memberRepository.getById(memberId);
        PostComment comment = commentRepository.findById(commentId);
        comment.updateLikeCount(comment.getLikeCount()+1);
        commentRepository.save(comment);
        PostCommentLike like = PostCommentLike.builder()
                .comment(comment)
                .member(member)
                .build();
        like = likeRepository.save(like);
        return PkResponseDto.of(like.getId());
    }

    @Override
    public void delete(Long id){
        likeRepository.delete(id);
    }
}
