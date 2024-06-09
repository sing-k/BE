package com.project.singk.domain.review.service;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumType;
import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.Role;
import com.project.singk.domain.review.controller.response.AlbumReviewResponse;
import com.project.singk.domain.review.controller.response.AlbumReviewStatisticsResponse;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.domain.AlbumReviewCreate;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.mock.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReviewServiceTest {

    private TestContainer testContainer;

    @BeforeEach
    public void init() {
        testContainer = TestContainer.builder().build();
        testContainer.memberRepository.save(Member.builder()
                .id(1L)
                .email("singk@gmail.com")
                .password("encodedPassword")
                .name("김철수")
                .nickname("SingK")
                .birthday(LocalDate.of(1999, 12, 30).atStartOfDay())
                .gender(Gender.MALE)
                .role(Role.ROLE_USER)
                .build());
        testContainer.albumRepository.save(Album.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .name("How Sweet")
                .type(AlbumType.EP)
                .releasedAt(LocalDateTime.of(2024, 5, 24, 0, 0, 0))
                .build());
    }

    @Test
    public void 동일한_Album에_작성한_AlbumReview가_있다면_생성할_수_없다() {
        // given
        testContainer.albumReviewRepository.save(AlbumReview.builder()
                .album(Album.builder().id("0EhZEM4RRz0yioTgucDhJq").build())
                .reviewer(Member.builder().id(1L).build())
                .build()
        );

        AlbumReviewCreate albumReviewCreate = AlbumReviewCreate.builder()
                .content("감상평입니다")
                .score(4)
                .build();

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> testContainer.reviewService.createAlbumReview(
                        1L,
                        "0EhZEM4RRz0yioTgucDhJq",
                        albumReviewCreate));

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.DUPLICATE_ALBUM_REVIEW);
    }

    @Test
    public void AlbumReviewCreate로_AlbumReview를_생성할_수_있다() {
        // given
        AlbumReviewCreate albumReviewCreate = AlbumReviewCreate.builder()
                .content("감상평입니다")
                .score(4)
                .build();

        // when
        PkResponseDto result = testContainer.reviewService.createAlbumReview(
                1L,
                "0EhZEM4RRz0yioTgucDhJq",
                albumReviewCreate
        );

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    public void 본인의_AlbumReview가_아니라면_삭제할_수_없다() {
        // given
        testContainer.albumReviewRepository.save(AlbumReview.builder()
                .id(1L)
                .reviewer(Member.builder().id(1L).build())
                .album(Album.builder().id("0EhZEM4RRz0yioTgucDhJq").build())
                .build());

        testContainer.memberRepository.save(Member.builder()
                .id(2L)
                .build());

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> testContainer.reviewService.deleteAlbumReview(2L, "0EhZEM4RRz0yioTgucDhJq", 1L));

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.FORBIDDEN_ALBUM_REVIEW);
    }

    @Test
    public void 본인의_AlbumReview를_삭제할_수_있다() {
        // given
        testContainer.albumReviewRepository.save(AlbumReview.builder()
                .id(1L)
                .reviewer(Member.builder().id(1L).build())
                .album(Album.builder().id("0EhZEM4RRz0yioTgucDhJq").build())
                .build());
        // when
        testContainer.reviewService.deleteAlbumReview(1L, "0EhZEM4RRz0yioTgucDhJq", 1L);
    }

    @Test
    public void AlbumReview_목록을_최신순으로_조회할_수_있다() {
        // given
        TestContainer tc = TestContainer.builder().build();
        List<Member> members = new ArrayList<>();
        for (long i = 1L; i <= 5L; i++) {
            members.add(Member.builder()
                    .id(i)
                    .nickname("닉네임" + i)
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .build());
        }
        tc.memberRepository.saveAll(members);
        Album album = tc.albumRepository.save(Album.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .build());

        List<AlbumReview> albumReviews = new ArrayList<>();
        for (int i = 5; i >= 1; i--) {
            albumReviews.add(AlbumReview.builder()
                    .id((long) i)
                    .content("감상평 내용입니다." + i)
                    .score(i)
                    .prosCount(i)
                    .consCount(0)
                    .createdAt(LocalDateTime.of(2024, 6, 6, 0, 0, i))
                    .reviewer(members.get(5 - i))
                    .album(album)
                    .build());
        }
        albumReviews = tc.albumReviewRepository.saveAll(albumReviews);

        // when
        List<AlbumReviewResponse> result = tc.reviewService.getAlbumReviews("0EhZEM4RRz0yioTgucDhJq", "NEW");

        // then
        assertAll(
                () -> assertThat(result.get(0).getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 6, 6, 0, 0, 5)),
                () -> assertThat(result.get(0).getReviewer().getId()).isEqualTo(1L)
        );
    }

    @Test
    public void AlbumReview_목록을_공감순으로_조회할_수_있다() {
        // given
        TestContainer tc = TestContainer.builder().build();
        List<Member> members = new ArrayList<>();
        for (long i = 1L; i <= 5L; i++) {
            members.add(Member.builder()
                    .id(i)
                    .nickname("닉네임" + i)
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .build());
        }
        tc.memberRepository.saveAll(members);
        Album album = tc.albumRepository.save(Album.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .build());

        List<AlbumReview> albumReviews = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            albumReviews.add(AlbumReview.builder()
                    .id((long) i)
                    .content("감상평 내용입니다." + i)
                    .score(i)
                    .prosCount(i)
                    .consCount(0)
                    .createdAt(LocalDateTime.of(2024, 6, 6, 0, 0, i))
                    .reviewer(members.get(i - 1))
                    .album(album)
                    .build());
        }
        albumReviews = tc.albumReviewRepository.saveAll(albumReviews);

        // when
        List<AlbumReviewResponse> result = tc.reviewService.getAlbumReviews("0EhZEM4RRz0yioTgucDhJq", "LIKES");

        // then
        assertAll(
                () -> assertThat(result.get(0).getPros()).isEqualTo(5),
                () -> assertThat(result.get(0).getReviewer().getId()).isEqualTo(5L)
        );
    }

    @Test
    public void AlbumReview_대한_통계를_조회할_수_있다() {
        // given
        TestContainer tc = TestContainer.builder().build();
        List<Member> members = new ArrayList<>();
        for (long i = 1L; i <= 5L; i++) {
            members.add(Member.builder()
                    .id(i)
                    .nickname("닉네임" + i)
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .build());
        }
        tc.memberRepository.saveAll(members);
        Album album = tc.albumRepository.save(Album.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .build());

        List<AlbumReview> albumReviews = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            albumReviews.add(AlbumReview.builder()
                    .id((long) i)
                    .content("감상평 내용입니다." + i)
                    .score(i)
                    .prosCount(i)
                    .consCount(0)
                    .createdAt(LocalDateTime.of(2024, 6, 6, 0, 0, i))
                    .reviewer(members.get(i - 1))
                    .album(album)
                    .build());
        }
        albumReviews = tc.albumReviewRepository.saveAll(albumReviews);

        // when
        AlbumReviewStatisticsResponse result = tc.reviewService.getAlbumReviewStatistics("0EhZEM4RRz0yioTgucDhJq");

        // then
        assertAll(
                () -> assertThat(result.getCount()).isEqualTo(5),
                () -> assertThat(result.getTotalScore()).isEqualTo(15),
                () -> assertThat(result.getAverageScore()).isEqualTo(3.0),
                () -> assertThat(result.getScoreStatistics().get(0).getRatio()).isEqualTo(20.0),
                () -> assertThat(result.getGenderStatistics().get(0).getAverageScore()).isEqualTo(3.0),
                () -> assertThat(result.getGenderStatistics().get(0).getRatio()).isEqualTo(40.0)
        );
    }
}
