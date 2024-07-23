package com.project.singk.domain.post.infrastructure;

import com.project.singk.domain.post.domain.Post;
import com.project.singk.domain.post.infrastructure.entity.PostEntity;
import com.project.singk.domain.post.infrastructure.jpa.PostJpaRepository;
import com.project.singk.domain.post.service.port.PostRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(PostEntity.from(post)).toModel();
    }

    @Override
    public void delete(Long id) {
        postJpaRepository.deleteById(id);
    }

    @Override
    public Post findById(Long id) {
        return postJpaRepository.findById(id).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND)).toModel();
    }

    @Override
    public Page<Post> findWithPaging(Pageable pageable) {
        return postJpaRepository.findAllByIsDeletedFalse(pageable).map(PostEntity::toModel);
    }

    @Override
    public List<Post> findAll() {
        return postJpaRepository.findAll().stream().map(PostEntity::toModel).toList();
    }

    @Override
    public List<Post> findByUserId(Long userId) {
        return new ArrayList<>();
    }

    @Override
    public Page<Post> findByUserIdWithPaging(Long userId, Pageable pageable) {
        return null;
    }
}
