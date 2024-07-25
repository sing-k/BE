package com.project.singk.domain.post.service.port;

import com.project.singk.domain.post.domain.Post;
import com.project.singk.domain.post.domain.RecommendPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecommendPostRepository {

    RecommendPost save(RecommendPost post);
    void delete(Long id);
    RecommendPost findById(Long id);
    Page<RecommendPost> findWithPaging(Pageable pageable);
    List<RecommendPost> findAll();
    List<RecommendPost> findByUserId(Long userId);
    Page<RecommendPost> findByUserIdWithPaging(Long userId, Pageable pageable);



}
