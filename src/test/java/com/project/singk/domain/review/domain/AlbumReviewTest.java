package com.project.singk.domain.review.domain;


import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumType;
import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.Role;
import com.project.singk.domain.vote.domain.VoteType;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AlbumReviewTest {
    @Test
    public void AlbumReviewCreate로_AlbumReview를_만들_수_있다() {
        // given
        AlbumReviewCreate albumReviewCreate = AlbumReviewCreate.builder()
                .content("감상평입니다")
                .score(4)
                .build();
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
        Album album = Album.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .name("How Sweet")
                .type(AlbumType.EP)
                .releasedAt(LocalDateTime.of(2024, 5, 24, 0, 0, 0))
                .build();

        // when
        AlbumReview albumReview = AlbumReview.from(albumReviewCreate, member, album);

        // then
        assertAll(
                () -> assertThat(albumReview.getContent()).isEqualTo("감상평입니다"),
                () -> assertThat(albumReview.getScore()).isEqualTo(4),
                () -> assertThat(albumReview.getProsCount()).isEqualTo(0),
                () -> assertThat(albumReview.getConsCount()).isEqualTo(0),
                () -> assertThat(albumReview.getWriter().getId()).isEqualTo(1L),
                () -> assertThat(albumReview.getAlbum().getId()).isEqualTo("0EhZEM4RRz0yioTgucDhJq")
        );
    }

    @Test
    public void AlbumReview는_작성자가_자신의_감상평에_투표를_하는지_검증할_수_있다() {
        // given
        Member writer = Member.builder()
                .id(1L)
                .build();

        AlbumReview albumReview = AlbumReview.builder()
                .writer(writer)
                .build();

        Member voter = Member.builder()
                .id(1L)
                .build();

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> albumReview.validateVoter(voter));

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.INVALID_ALBUM_REVIEW_VOTE);
    }

    @Test
    public void AlbumReview는_투표를_할_수_있다() {
        // given
        AlbumReview albumReview = AlbumReview.builder()
                .id(1L)
                .prosCount(0)
                .consCount(0)
                .build();

        // when
        albumReview = albumReview.vote(VoteType.PROS);

        // then
        assertThat(albumReview.getProsCount()).isEqualTo(1);
        assertThat(albumReview.getConsCount()).isEqualTo(0);
    }

    @Test
    public void AlbumReview는_투표를_취소_할_수_있다() {
        // given
        AlbumReview albumReview = AlbumReview.builder()
                .id(1L)
                .prosCount(1)
                .consCount(0)
                .build();

        // when
        albumReview = albumReview.withdraw(VoteType.PROS);

        // then
        assertThat(albumReview.getProsCount()).isEqualTo(0);
        assertThat(albumReview.getConsCount()).isEqualTo(0);
    }
}
