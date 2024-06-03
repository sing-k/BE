package com.project.singk.domain.review.service;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumType;
import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.Role;
import com.project.singk.domain.review.domain.AlbumReviewCreate;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.mock.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

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
}
