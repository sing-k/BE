package com.project.singk.domain.comment.service;

import com.project.singk.domain.comment.controller.port.PostCommentService;
import com.project.singk.domain.comment.domain.PostComment;
import com.project.singk.domain.comment.domain.PostCommentCreate;
import com.project.singk.domain.comment.service.port.PostCommentRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.domain.Post;
import com.project.singk.domain.post.service.port.PostRepository;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private  final PostRepository postRepository;

    // Todo : auth 관련 처리
    // 댓글 수
    @Override
    public PkResponseDto createComment(Long memberId, Long postId, Long parentId, PostCommentCreate req) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Post post = postRepository.findById(postId);
        post.updateCommentCount(post.getComments()+1);
        postRepository.save(post);
        PostComment parent = commentRepository.findById(parentId);
        PostComment recommendComment = commentRepository.save(PostComment.byRequest(req,member,post,parent));
        return PkResponseDto.of(recommendComment.getId());
    }

    @Override
    public PkResponseDto updateComment(Long id, PostCommentCreate req) {
        PostComment comment = commentRepository.findById(id);
        comment.update(req);
        PostComment updatedComment = commentRepository.save(comment);
        return PkResponseDto.of(updatedComment.getId());
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.delete(id);
    }

}

