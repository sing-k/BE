package com.project.singk.domain.review.infrastructure;

import com.project.singk.domain.album.infrastructure.entity.AlbumEntity;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumReviewJpaRepository extends JpaRepository<AlbumReviewEntity, Long> {
    boolean existsByMemberAndAlbum(MemberEntity member, AlbumEntity albumEntity);
}
