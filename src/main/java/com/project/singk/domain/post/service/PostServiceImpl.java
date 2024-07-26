package com.project.singk.domain.post.service;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.controller.port.PostService;
import com.project.singk.domain.post.controller.response.PostResponse;
import com.project.singk.domain.post.domain.PostCreate;
import com.project.singk.domain.post.domain.Post;
import com.project.singk.domain.post.service.port.PostRepository;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Override
    public PkResponseDto create(Long memberId, PostCreate postCreate) {
        Member member = memberRepository.getById(memberId);
        Post post = Post.byRequest(postCreate, member);
        post = postRepository.save(post);
        return PkResponseDto.of(post.getId());
    }

    @Override
    public PostResponse findById(Long id){
        Post post = postRepository.findById(id);
        return PostResponse.from(post);
    }

    @Override
    public List<PostResponse> findAll(){
        return null;
    }

    @Override
    public PkResponseDto updateById(Long id,PostCreate req){
        Post post = postRepository.findById(id);
        post.update(req);
        Post updatedPost = postRepository.save(post);
        return PkResponseDto.of(updatedPost.getId());
    }

    @Override
    public void deleteById(Long id){
        postRepository.delete(id);
    }
}
