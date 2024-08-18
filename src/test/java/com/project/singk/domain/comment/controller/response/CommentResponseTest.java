package com.project.singk.domain.comment.controller.response;

import com.project.singk.domain.comment.domain.CommentSimplified;
import com.project.singk.domain.member.controller.response.MemberResponse;
import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.member.domain.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CommentResponseTest {

    @Test
    public void CommentSimplified로_앨범추천게시글타입의_CommentResponse를_생성할_수_있다() {
        // given
        Member writer = Member.builder()
                .id(1L)
                .nickname("작성자")
                .build();

        CommentSimplified comment = CommentSimplified.builder()
                .id(1L)
                .parentId(null)
                .content("내용")
                .likes(0)
                .member(writer)
                .createdAt(LocalDateTime.of(2024, 8, 17, 12, 0, 0, 0))
                .modifiedAt(LocalDateTime.of(2024, 8, 17, 13, 0, 0, 0))
                .build();

        boolean isLike = false;
        String writerProfileImg = "imageUrl";
        // when
        CommentResponse response = CommentResponse.freeType(
                comment,
                isLike,
                writerProfileImg
        );

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(1L),
                () -> assertThat(response.getParentId()).isNull(),
                () -> assertThat(response.getType()).isEqualTo("자유 게시글"),
                () -> assertThat(response.getContent()).isEqualTo("내용"),
                () -> assertThat(response.getLike().isLike()).isEqualTo(false),
                () -> assertThat(response.getLike().getCount()).isEqualTo(0),
                () -> assertThat(response.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 8, 17, 12, 0, 0, 0)),
                () -> assertThat(response.getModifiedAt()).isEqualTo(LocalDateTime.of(2024, 8, 17, 13, 0, 0, 0))
        );
    }

    @Test
    public void CommentSimplified로_자유게시글_타입의_CommentResponse를_생성할_수_있다() {
        // given
        Member writer = Member.builder()
                .id(1L)
                .nickname("작성자")
                .build();

        CommentSimplified comment = CommentSimplified.builder()
                .id(1L)
                .parentId(null)
                .content("내용")
                .likes(0)
                .member(writer)
                .createdAt(LocalDateTime.of(2024, 8, 17, 12, 0, 0, 0))
                .modifiedAt(LocalDateTime.of(2024, 8, 17, 13, 0, 0, 0))
                .build();

        boolean isLike = false;
        String writerProfileImg = "imageUrl";
        // when
        CommentResponse response = CommentResponse.recommendType(
                comment,
                isLike,
                writerProfileImg
        );

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(1L),
                () -> assertThat(response.getParentId()).isNull(),
                () -> assertThat(response.getType()).isEqualTo("앨범 추천 게시글"),
                () -> assertThat(response.getContent()).isEqualTo("내용"),
                () -> assertThat(response.getLike().isLike()).isEqualTo(false),
                () -> assertThat(response.getLike().getCount()).isEqualTo(0),
                () -> assertThat(response.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 8, 17, 12, 0, 0, 0)),
                () -> assertThat(response.getModifiedAt()).isEqualTo(LocalDateTime.of(2024, 8, 17, 13, 0, 0, 0))
        );
    }

}
