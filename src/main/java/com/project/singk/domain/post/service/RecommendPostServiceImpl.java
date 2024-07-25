package com.project.singk.domain.post.service;

import com.google.j2objc.annotations.ObjectiveCName;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.controller.port.RecommendPostService;
import com.project.singk.domain.post.controller.response.RecommendPostResponse;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.domain.RecommendPostCreate;
import com.project.singk.domain.post.domain.RecommendPostUpdate;
import com.project.singk.domain.post.service.port.RecommendPostRepository;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendPostServiceImpl implements RecommendPostService {
    private final RecommendPostRepository recommendPostRepository;
    private final MemberRepository memberRepository;

    @Override
    public PkResponseDto createRecommendPost(Long memberId, RecommendPostCreate post, MultipartFile image) {
        Member member = memberRepository.findById(memberId).orElse(null);
        RecommendPost recommendPost = recommendPostRepository.save(RecommendPost.byRequest(post,member));
        return PkResponseDto.of(recommendPost.getId());
    }

    @Override
    public RecommendPostResponse findById(Long id){
        RecommendPost recommendPost = recommendPostRepository.findById(id);
        return RecommendPostResponse.from(recommendPost);
    }

    @Override
    public List<RecommendPostResponse> findAll(){
        return null;
    }

    @Override
    public PkResponseDto updateById(Long id, RecommendPostUpdate req){
        RecommendPost recommendPost = recommendPostRepository.findById(id);
        recommendPost.update(req);
        RecommendPost updatedRecommendPost = recommendPostRepository.save(recommendPost);
        return PkResponseDto.of(updatedRecommendPost.getId());
    }

    @Override
    public void deleteById(Long id){
        recommendPostRepository.delete(id);
    }
}
