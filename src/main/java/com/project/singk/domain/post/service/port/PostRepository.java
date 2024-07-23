package com.project.singk.domain.post.service.port;

import com.project.singk.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepository {
    Post save(Post post);
    void delete(Long id);
    Post findById(Long id);
    Page<Post> findWithPaging(Pageable pageable); // 최신순, 좋아요순
    List<Post> findAll();
    List<Post> findByUserId(Long userId);
    Page<Post> findByUserIdWithPaging(Long userId, Pageable pageable);
}
