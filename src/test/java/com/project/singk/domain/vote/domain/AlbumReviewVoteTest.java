package com.project.singk.domain.vote.domain;

import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.Role;
import com.project.singk.domain.review.domain.AlbumReview;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AlbumReviewVoteTest {

    @Test
    public void VoteCreate로_AlbumReviewVote를_만들_수_있다() {
        // given
        Member member = Member.builder()
                .id(1L)
                .email("singk@gmail.com")
                .password("encodedPassword")
                .name("김철수")
                .nickname("SingK")
                .birthday(LocalDate.of(1999, 12, 30).atStartOfDay())
                .gender(Gender.MALE)
                .role(Role.ROLE_USER)
                .build();

        AlbumReview albumReview = AlbumReview.builder()
                .id(1L)
                .content("감상평입니다")
                .score(5)
                .prosCount(0)
                .consCount(0)
                .build();

        VoteCreate voteCreate = VoteCreate.builder()
                .type("PROS")
                .build();

        // when
        AlbumReviewVote albumReviewVote = AlbumReviewVote.from(voteCreate, member, albumReview);

        // then
        assertAll(
                () -> assertThat(albumReviewVote.getType()).isEqualTo(VoteType.PROS),
                () -> assertThat(albumReviewVote.getMember().getEmail()).isEqualTo("singk@gmail.com"),
                () -> assertThat(albumReviewVote.getAlbumReview().getContent()).isEqualTo("감상평입니다")
        );
    }
}
