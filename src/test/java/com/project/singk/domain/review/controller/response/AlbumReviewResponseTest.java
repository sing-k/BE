package com.project.singk.domain.review.controller.response;

import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.vote.domain.AlbumReviewVote;
import com.project.singk.domain.vote.domain.VoteType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AlbumReviewResponseTest {

    @Test
    public void AlbumReview로_AlbumReviewResponse을_생성할_수_있다() {
        // given
        Member member = Member.builder()
                .id(1L)
                .nickname("SingK")
                .gender(Gender.MALE)
                .statistics(MemberStatistics.empty())
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

        AlbumReviewVote albumReviewVote = AlbumReviewVote.builder()
                .type(VoteType.NONE)
                .build();

        // when
        AlbumReviewResponse albumReviewResponse = AlbumReviewResponse.from(albumReview, albumReviewVote, imageUrl);

        // then
        assertAll(
                () -> assertThat(albumReviewResponse.getId()).isEqualTo(1L),
                () -> assertThat(albumReviewResponse.getContent()).isEqualTo("이것은 제가 제일 좋아하는 앨범에 대한 감상평 입니다."),
                () -> assertThat(albumReviewResponse.getScore()).isEqualTo(5),
                () -> assertThat(albumReviewResponse.getVote().getProsCount()).isEqualTo(0),
                () -> assertThat(albumReviewResponse.getVote().getConsCount()).isEqualTo(0),
                () -> assertThat(albumReviewResponse.getVote().isPros()).isEqualTo(false),
                () -> assertThat(albumReviewResponse.getVote().isCons()).isEqualTo(false),
                () -> assertThat(albumReviewResponse.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 6, 6, 0, 0, 0)),
                () -> assertThat(albumReviewResponse.getReviewer().getNickname()).isEqualTo("SingK")
        );
    }
}
