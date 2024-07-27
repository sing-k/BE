package com.project.singk.domain.like.service;

import com.project.singk.domain.like.controller.port.RecommendLikeService;
import com.project.singk.domain.like.domain.RecommendLike;
import com.project.singk.domain.like.service.port.RecommendLikeRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.service.port.RecommendPostRepository;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendLikeServiceImpl implements RecommendLikeService {
    private final RecommendLikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final RecommendPostRepository postRepository;

    @Override
    public PkResponseDto addLike(Long memberId, Long postId){
        Member member = memberRepository.getById(memberId);
        RecommendPost post = postRepository.getById(postId);
        post.updateLikeCount(post.getLikes()+1);
        postRepository.save(post);
        RecommendLike like = RecommendLike.builder()
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
