package com.project.singk.domain.post.controller.port;

import com.project.singk.domain.post.controller.response.PostResponse;
import com.project.singk.domain.post.controller.response.RecommendPostResponse;
import com.project.singk.domain.post.domain.PostCreate;
import com.project.singk.domain.post.domain.RecommendPostUpdate;
import com.project.singk.global.domain.PkResponseDto;

import java.util.List;

public interface PostService {
    PkResponseDto create(Long memberId, PostCreate post);
    PostResponse findById(Long id);
    List<PostResponse> findAll();
    PkResponseDto updateById(Long id, PostCreate req);
    void deleteById(Long id);
}
