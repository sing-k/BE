package com.project.singk.domain.review.service.port;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.review.controller.request.ReviewSort;
import com.project.singk.domain.review.domain.AlbumReview;

import java.util.List;
import java.util.Optional;

public interface AlbumReviewRepository {
    AlbumReview save(AlbumReview albumReview);
    List<AlbumReview> saveAll(List<AlbumReview> albumReviews);
    AlbumReview getById(Long id);
    Optional<AlbumReview> findById(Long id);
    boolean existsByMemberAndAlbum(Member member, Album album);
    List<AlbumReview> getAllByAlbumId(String albumId, ReviewSort sort);
    void delete(AlbumReview albumReview);
}
