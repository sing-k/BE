package com.project.singk.domain.review.domain;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.member.domain.Member;
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
@Builder
@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ALBUM_REVIEWS")
public class AlbumReview extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 500, nullable = false)
	private String content;

	@Column(nullable = false)
	private int score;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "album_id")
	private Album album;
}
