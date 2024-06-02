package com.project.singk.domain.vote.infrastructure;

import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.domain.review.infrastructure.AlbumReviewEntity;
import com.project.singk.domain.vote.domain.AlbumReviewVote;
import com.project.singk.domain.vote.domain.VoteType;
import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "ALBUM_REVIEW_VOTES")
@Getter
@NoArgsConstructor
public class AlbumReviewVoteEntity extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "vote_type")
	@Enumerated(EnumType.STRING)
	private VoteType type;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private MemberEntity member;

	@ManyToOne
	@JoinColumn(name = "album_id")
	private AlbumReviewEntity albumReview;

    @Builder
    public AlbumReviewVoteEntity(Long id, VoteType type, MemberEntity member, AlbumReviewEntity albumReview) {
        this.id = id;
        this.type = type;
        this.member = member;
        this.albumReview = albumReview;
    }

    public static AlbumReviewVoteEntity from (AlbumReviewVote albumReviewVote) {
        return AlbumReviewVoteEntity.builder()
                .id(albumReviewVote.getId())
                .type(albumReviewVote.getType())
                .albumReview(AlbumReviewEntity.from(albumReviewVote.getAlbumReview()))
                .member(MemberEntity.from(albumReviewVote.getMember()))
                .build();
    }

    public AlbumReviewVote toModel() {
        return AlbumReviewVote.builder()
                .id(this.id)
                .type(this.type)
                .albumReview(this.albumReview.toModel())
                .member(this.member.toModel())
                .build();
    }
}
