package com.project.singk.domain.comment.service;

import com.project.singk.domain.comment.controller.port.RecommendCommentService;
import com.project.singk.domain.comment.domain.RecommendComment;
import com.project.singk.domain.comment.domain.RecommendCommentCreate;
import com.project.singk.domain.comment.service.port.RecommendCommentRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.service.port.RecommendPostRepository;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendCommentServiceImpl implements RecommendCommentService {

    private final RecommendCommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private  final RecommendPostRepository postRepository;

    // Todo : auth 관련 처리
    @Override
    public PkResponseDto createComment(Long memberId, Long postId,Long parentId,RecommendCommentCreate req) {
        Member member = memberRepository.findById(memberId).orElse(null);
        RecommendPost post = postRepository.findById(postId);
        RecommendComment parent = commentRepository.findById(parentId);
        RecommendComment recommendComment = commentRepository.save(RecommendComment.byRequest(req,member,post,parent));
        return PkResponseDto.of(recommendComment.getId());
    }

    @Override
    public PkResponseDto updateComment(Long id, RecommendCommentCreate req) {
        RecommendComment recommendComment = commentRepository.findById(id);
        recommendComment.update(req);
        RecommendComment updatedComment = commentRepository.save(recommendComment);
        commentRepository.delete(id);
        return PkResponseDto.of(updatedComment.getId());
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.delete(id);
    }

}
