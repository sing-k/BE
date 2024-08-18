package com.project.singk.domain.post.controller.response;

import com.project.singk.domain.album.domain.GenreType;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.domain.RecommendType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class FreePostResponseTest {

    @Test
    public void FreePost로_FreePostResponse를_생성할_수_있다() {
        // given
        Member member = Member.builder()
                .id(1L)
                .build();

        FreePost post = FreePost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .likes(0)
                .comments(0)
                .member(member)
                .createdAt(LocalDate.of(2024, 8, 10).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 8, 10).atStartOfDay())
                .build();

        String writerProfileImg = "imageLink";
        boolean viewerIsLike = false;

        // when
        FreePostResponse response = FreePostResponse.from(
                post,
                viewerIsLike,
                writerProfileImg
        );

        // then
        assertAll(
                () -> assertThat(response.getTitle()).isEqualTo("제목"),
                () -> assertThat(response.getContent()).isEqualTo("내용"),
                () -> assertThat(response.getComments()).isEqualTo( 0),
                () -> assertThat(response.getLike().getCount()).isEqualTo( 0),
                () -> assertThat(response.getLike().isLike()).isEqualTo( false),
                () -> assertThat(response.getWriter().getId()).isEqualTo( 1L),
                () -> assertThat(response.getCreatedAt()).isEqualTo( LocalDateTime.of(2024, 8, 10, 0, 0, 0)),
                () -> assertThat(response.getModifiedAt()).isEqualTo( LocalDateTime.of(2024, 8, 10, 0, 0, 0))
        );
    }
}
