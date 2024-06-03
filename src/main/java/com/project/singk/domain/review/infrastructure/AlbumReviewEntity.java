package com.project.singk.domain.review.infrastructure;

import com.project.singk.domain.review.domain.AlbumReview;
import org.hibernate.annotations.ColumnDefault;

import com.project.singk.domain.album.infrastructure.entity.AlbumEntity;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.vote.domain.VoteType;
import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

	@ManyToOne
	@JoinColumn(name = "member_id")
	private MemberEntity member;

	@ManyToOne
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
                .member(MemberEntity.from(albumReview.getWriter()))
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
                .writer(this.member.toModel())
                .build();
    }
}
