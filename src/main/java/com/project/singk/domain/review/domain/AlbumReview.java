package com.project.singk.domain.review.domain;

import org.hibernate.annotations.ColumnDefault;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.member.domain.Member;
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

	@ColumnDefault("0")
	@Column(name = "pros_count")
	private int prosCount;

	@ColumnDefault("0")
	@Column(name = "cons_count")
	private int consCount;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "album_id")
	private Album album;

	public void increaseVoteCount(VoteType type) {
		if (VoteType.PROS.equals(type)) {
			this.prosCount += 1;
		} else if (VoteType.CONS.equals(type)) {
			this.consCount += 1;
		}
	}
	public void decreaseVoteCount(VoteType type) {
		if (VoteType.PROS.equals(type)) {
			this.prosCount -= 1;
		} else if (VoteType.CONS.equals(type)) {
			this.consCount -= 1;
		}
	}

}
