package com.project.singk.domain.post.service;

import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.controller.port.RecommendPostService;
import com.project.singk.domain.post.domain.RecommendPostCreate;
import com.project.singk.domain.post.service.port.RecommendPostRepository;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RecommendPostServiceImpl implements RecommendPostService {
    private final RecommendPostRepository recommendPostRepository;
    private final MemberRepository memberRepository;

    @Override
    public PkResponseDto createRecommendPost(Long memberId, RecommendPostCreate post, MultipartFile image) {
        return null;
    }
}
