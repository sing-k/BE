package com.project.singk.domain.post.infrastructure;

import com.project.singk.domain.post.domain.Post;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.infrastructure.entity.PostEntity;
import com.project.singk.domain.post.infrastructure.entity.RecommendPostEntity;
import com.project.singk.domain.post.infrastructure.jpa.RecommendPostJpaRepository;
import com.project.singk.domain.post.service.port.RecommendPostRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class RecommendPostRepositoryImpl implements RecommendPostRepository {

    private final RecommendPostJpaRepository recommendPostJpaRepository;


    @Override
    public RecommendPost save(RecommendPost post) {
        return recommendPostJpaRepository.save(RecommendPostEntity.from(post)).toModel();
    }

    @Override
    public void delete(Long id) {recommendPostJpaRepository.deleteById(id);
    }

    @Override
    public RecommendPost findById(Long id) {
        return recommendPostJpaRepository.findById(id).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND)).toModel();
    }

    @Override
    public Page<RecommendPost> findWithPaging(Pageable pageable) {
        return recommendPostJpaRepository.findAllByIsDeletedFalse(pageable).map(RecommendPostEntity::toModel);
    }

    @Override
    public List<RecommendPost> findAll() {
        return recommendPostJpaRepository.findAll().stream().map(RecommendPostEntity::toModel).toList();
    }
    //Todo : 채우기
    @Override
    public List<RecommendPost> findByUserId(Long userId) {
        return new ArrayList<>();
    }

    @Override
    public Page<RecommendPost> findByUserIdWithPaging(Long userId, Pageable pageable) {
        return null;
    }
}
