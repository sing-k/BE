package com.project.singk.domain.post.service;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.controller.port.PostService;
import com.project.singk.domain.post.controller.request.PostCreateRequest;
import com.project.singk.domain.post.domain.Post;
import com.project.singk.domain.post.service.port.PostRepository;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Override
    public PkResponseDto create(Long memberId, PostCreateRequest postCreateRequest, MultipartFile thumbnail) {
        Member member = memberRepository.getById(memberId);
        Post post = Post.byRequest(postCreateRequest, member);
        post = postRepository.save(post);
        return PkResponseDto.of(post.getId());
    }
}
