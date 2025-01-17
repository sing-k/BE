package com.project.singk.domain.review.infrastructure;

import com.project.singk.domain.review.domain.AlbumReview;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import com.project.singk.domain.album.infrastructure.entity.AlbumEntity;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.global.domain.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ALBUM_REVIEWS")
@Getter
@NoArgsConstructor
public class AlbumReviewEntity extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 500, nullable = false)
	private String content;

	@Column(nullable = false)
	private int score;

	@ColumnDefault("0")
	@Column(name = "pros_count")
	private int prosCount;

	@ColumnDefault("0")
	@Column(name = "cons_count")
	private int consCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private MemberEntity member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "album_id")
	private AlbumEntity album;

    @Builder
    public AlbumReviewEntity(Long id, String content, int score, int prosCount, int consCount, MemberEntity member, AlbumEntity album) {
        this.id = id;
        this.content = content;
        this.score = score;
        this.prosCount = prosCount;
        this.consCount = consCount;
        this.member = member;
        this.album = album;
    }

    public static AlbumReviewEntity from(AlbumReview albumReview) {
        return AlbumReviewEntity.builder()
                .id(albumReview.getId())
                .content(albumReview.getContent())
                .score(albumReview.getScore())
                .prosCount(albumReview.getProsCount())
                .consCount(albumReview.getConsCount())
                .album(AlbumEntity.from(albumReview.getAlbum()))
                .member(MemberEntity.from(albumReview.getReviewer()))
                .build();
    }

    public AlbumReview toModel() {
        return AlbumReview.builder()
                .id(this.id)
                .content(this.content)
                .score(this.score)
                .prosCount(this.prosCount)
                .consCount(this.consCount)
                .album(this.album.toModel())
                .reviewer(this.member.toModel())
                .createdAt(this.getCreatedAt())
                .build();
    }
}
