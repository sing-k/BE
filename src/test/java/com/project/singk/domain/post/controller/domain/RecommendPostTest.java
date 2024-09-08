package com.project.singk.domain.post.controller.domain;

import com.project.singk.domain.album.domain.GenreType;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.domain.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RecommendPostTest {

    @Test
    public void RecommendPostCreate로_Album타입의_RecommendPost를_생성할_수_있다() {

        // given
        Member writer = Member.builder()
                .id(1L)
                .build();

        RecommendPostCreate recommendPostCreate = RecommendPostCreate.builder()
                .title("제목")
                .content("내용")
                .type("ALBUM")
                .link("앨범 ID")
                .genre("POP")
                .build();

        String thumbnailImg = "썸네일 이미지 링크";
        // when
        RecommendPost recommendPost = RecommendPost.albumType(
                recommendPostCreate,
                thumbnailImg,
                writer
        );

        // then
        assertAll(
                () -> assertThat(recommendPost.getId()).isNull(),
                () -> assertThat(recommendPost.getTitle()).isEqualTo("제목"),
                () -> assertThat(recommendPost.getContent()).isEqualTo("내용"),
                () -> assertThat(recommendPost.getRecommend()).isEqualTo(RecommendType.ALBUM),
                () -> assertThat(recommendPost.getGenre()).isEqualTo(GenreType.POP),
                () -> assertThat(recommendPost.getLink()).isEqualTo("썸네일 이미지 링크"),
                () -> assertThat(recommendPost.getLikes()).isEqualTo(0),
                () -> assertThat(recommendPost.getComments()).isEqualTo(0),
                () -> assertThat(recommendPost.getMember().getId()).isEqualTo(1L)
        );
    }

    @Test
    public void RecommendPostCreate로_Image타입의_RecommendPost를_생성할_수_있다() {

        // given
        Member writer = Member.builder()
                .id(1L)
                .build();

        RecommendPostCreate recommendPostCreate = RecommendPostCreate.builder()
                .title("제목")
                .content("내용")
                .type("IMAGE")
                .genre("POP")
                .build();

        String thumbnailImg = "썸네일 이미지 링크";
        // when
        RecommendPost recommendPost = RecommendPost.imageType(
                recommendPostCreate,
                thumbnailImg,
                writer
        );

        // then
        assertAll(
                () -> assertThat(recommendPost.getId()).isNull(),
                () -> assertThat(recommendPost.getTitle()).isEqualTo("제목"),
                () -> assertThat(recommendPost.getContent()).isEqualTo("내용"),
                () -> assertThat(recommendPost.getRecommend()).isEqualTo(RecommendType.IMAGE),
                () -> assertThat(recommendPost.getGenre()).isEqualTo(GenreType.POP),
                () -> assertThat(recommendPost.getLink()).isEqualTo("썸네일 이미지 링크"),
                () -> assertThat(recommendPost.getLikes()).isEqualTo(0),
                () -> assertThat(recommendPost.getComments()).isEqualTo(0),
                () -> assertThat(recommendPost.getMember().getId()).isEqualTo(1L)
        );
    }

    @Test
    public void RecommendPostCreate로_Youtube타입의_RecommendPost를_생성할_수_있다() {

        // given
        Member writer = Member.builder()
                .id(1L)
                .build();

        RecommendPostCreate recommendPostCreate = RecommendPostCreate.builder()
                .title("제목")
                .content("내용")
                .type("YOUTUBE")
                .link("유튜브 링크")
                .genre("POP")
                .build();

        // when
        RecommendPost recommendPost = RecommendPost.youtubeType(
                recommendPostCreate,
                writer
        );

        // then
        assertAll(
                () -> assertThat(recommendPost.getId()).isNull(),
                () -> assertThat(recommendPost.getTitle()).isEqualTo("제목"),
                () -> assertThat(recommendPost.getContent()).isEqualTo("내용"),
                () -> assertThat(recommendPost.getRecommend()).isEqualTo(RecommendType.YOUTUBE),
                () -> assertThat(recommendPost.getGenre()).isEqualTo(GenreType.POP),
                () -> assertThat(recommendPost.getLink()).isEqualTo("유튜브 링크"),
                () -> assertThat(recommendPost.getLikes()).isEqualTo(0),
                () -> assertThat(recommendPost.getComments()).isEqualTo(0),
                () -> assertThat(recommendPost.getMember().getId()).isEqualTo(1L)
        );
    }

    @Test
    public void RecommendPost는_RecommendPostUpdate로_업데이트할_수_있다() {

        // given
        RecommendPostUpdate recommendPostUpdate = RecommendPostUpdate.builder()
                .title("변경된 제목")
                .content("변경된 내용")
                .build();

        Member member = Member.builder()
                .id(1L)
                .build();

        RecommendPost recommendPost = RecommendPost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .likes(0)
                .comments(0)
                .member(member)
                .createdAt(LocalDate.of(2024, 8, 12).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 8, 12).atStartOfDay())
                .build();

        // when
        recommendPost = recommendPost.update(recommendPostUpdate);

        // then
        assertThat(recommendPost.getId()).isEqualTo(1L);
        assertThat(recommendPost.getTitle()).isEqualTo("변경된 제목");
        assertThat(recommendPost.getContent()).isEqualTo("변경된 내용");
        assertThat(recommendPost.getLikes()).isEqualTo(0);
        assertThat(recommendPost.getComments()).isEqualTo(0);
        assertThat(recommendPost.getMember().getId()).isEqualTo(1L);
    }

    @Test
    public void RecommendPost는_좋아요_수를_업데이트할_수_있다() {

        // given
        Member member = Member.builder()
                .id(1L)
                .build();

        RecommendPost recommendPost = RecommendPost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .likes(0)
                .comments(0)
                .member(member)
                .createdAt(LocalDate.of(2024, 8, 12).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 8, 12).atStartOfDay())
                .build();

        // when
        recommendPost = recommendPost.updateLikes(recommendPost.getLikes() + 1);

        // then
        assertThat(recommendPost.getId()).isEqualTo(1L);
        assertThat(recommendPost.getTitle()).isEqualTo("제목");
        assertThat(recommendPost.getContent()).isEqualTo("내용");
        assertThat(recommendPost.getLikes()).isEqualTo(1);
        assertThat(recommendPost.getComments()).isEqualTo(0);
        assertThat(recommendPost.getMember().getId()).isEqualTo(1L);
    }

    @Test
    public void RecommendPost는_댓글_수를_업데이트할_수_있다() {

        // given
        Member member = Member.builder()
                .id(1L)
                .build();

        RecommendPost recommendPost = RecommendPost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .likes(0)
                .comments(0)
                .member(member)
                .createdAt(LocalDate.of(2024, 8, 12).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 8, 12).atStartOfDay())
                .build();

        // when
        recommendPost = recommendPost.updateComments(recommendPost.getComments() + 1);

        // then
        assertThat(recommendPost.getId()).isEqualTo(1L);
        assertThat(recommendPost.getTitle()).isEqualTo("제목");
        assertThat(recommendPost.getContent()).isEqualTo("내용");
        assertThat(recommendPost.getLikes()).isEqualTo(0);
        assertThat(recommendPost.getComments()).isEqualTo(1);
        assertThat(recommendPost.getMember().getId()).isEqualTo(1L);
    }


}
