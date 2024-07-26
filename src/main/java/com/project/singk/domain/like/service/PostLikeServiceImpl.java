package com.project.singk.domain.like.service;

import com.project.singk.domain.like.controller.port.PostLikeService;
import com.project.singk.domain.like.domain.PostLike;
import com.project.singk.domain.like.service.port.PostLikeRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.domain.Post;
import com.project.singk.domain.post.service.port.PostRepository;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {
    private final PostLikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Override
    public PkResponseDto addLike(Long memberId, Long postId){
        Member member = memberRepository.getById(memberId);
        Post post = postRepository.findById(postId);
        post.updateLikeCount(post.getLikes()+1);
        postRepository.save(post);
        PostLike like = PostLike.builder()
                .post(post)
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
