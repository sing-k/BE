package com.project.singk.domain.like.service;

import com.project.singk.domain.comment.domain.RecommendComment;
import com.project.singk.domain.comment.service.port.RecommendCommentRepository;
import com.project.singk.domain.like.controller.port.RecommendCommentLikeService;
import com.project.singk.domain.like.domain.RecommendCommentLike;
import com.project.singk.domain.like.service.port.RecommendCommentLikeRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendCommentLikeServiceImpl implements RecommendCommentLikeService {
    private final RecommendCommentLikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final RecommendCommentRepository commentRepository;

    @Override
    public PkResponseDto addLike(Long memberId, Long commentId){
        Member member = memberRepository.getById(memberId);
        RecommendComment comment = commentRepository.findById(commentId);
        comment.updateLikeCount(comment.getLikeCount()+1);
        commentRepository.save(comment);
        RecommendCommentLike like = RecommendCommentLike.builder()
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
