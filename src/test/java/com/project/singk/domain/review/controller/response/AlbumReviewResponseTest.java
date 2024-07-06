package com.project.singk.domain.review.controller.response;

import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.review.domain.AlbumReview;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AlbumReviewResponseTest {

    @Test
    public void AlbumReview로_응답을_생성할_수_있다() {
        // given
        Member member = Member.builder()
                .id(1L)
                .nickname("SingK")
                .gender(Gender.MALE)
                .build();

        String imageUrl = "imageUrl";

        AlbumReview albumReview = AlbumReview.builder()
                .id(1L)
                .content("이것은 제가 제일 좋아하는 앨범에 대한 감상평 입니다.")
                .score(5)
                .prosCount(0)
                .consCount(0)
                .createdAt(LocalDateTime.of(2024, 6, 6, 0, 0, 0))
                .reviewer(member)
                .build();

        // when
        AlbumReviewResponse albumReviewResponse = AlbumReviewResponse.from(albumReview, imageUrl);

        // then
        assertAll(
                () -> assertThat(albumReviewResponse.getId()).isEqualTo(1L),
                () -> assertThat(albumReviewResponse.getContent()).isEqualTo("이것은 제가 제일 좋아하는 앨범에 대한 감상평 입니다."),
                () -> assertThat(albumReviewResponse.getScore()).isEqualTo(5),
                () -> assertThat(albumReviewResponse.getPros()).isEqualTo(0),
                () -> assertThat(albumReviewResponse.getCons()).isEqualTo(0),
                () -> assertThat(albumReviewResponse.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 6, 6, 0, 0, 0)),
                () -> assertThat(albumReviewResponse.getReviewer().getNickname()).isEqualTo("SingK")
        );
    }
}
