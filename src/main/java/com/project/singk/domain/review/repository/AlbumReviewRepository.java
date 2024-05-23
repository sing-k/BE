package com.project.singk.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.singk.domain.review.domain.AlbumReview;

public interface AlbumReviewRepository extends JpaRepository<AlbumReview, Long> {
}
