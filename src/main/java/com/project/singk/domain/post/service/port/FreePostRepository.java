package com.project.singk.domain.post.service.port;

import com.project.singk.domain.post.domain.FreePost;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FreePostRepository {
    FreePost save(FreePost post);
    List<FreePost> saveAll(List<FreePost> post);
    FreePost getById(Long postId);
    Optional<FreePost> findById(Long postId);
    Page<FreePost> findAll(int offset, int limit, String sort, String filter, String keyword);
    Page<FreePost> findAllByMemberId(Long memberId, int offset, int limit);
    void deleteById(Long postId);
}
