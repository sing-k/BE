package com.project.singk.domain.post.controller.domain;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumType;
import com.project.singk.domain.album.infrastructure.spotify.AlbumEntity;
import com.project.singk.domain.album.infrastructure.spotify.ArtistSimplifiedEntity;
import com.project.singk.domain.album.infrastructure.spotify.ImageEntity;
import com.project.singk.domain.album.infrastructure.spotify.TrackSimplifiedEntity;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.domain.post.domain.FreePostCreate;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class FreePostTest {

    @Test
    public void FreePostCreate로_FreePost를_생성할_수_있다() {

        // given
        Member writer = Member.builder()
                .id(1L)
                .build();

        FreePostCreate freePostCreate = FreePostCreate.builder()
                .title("제목")
                .content("내용")
                .build();


        // when
        FreePost freePost = FreePost.from(freePostCreate, writer);

        // then
        assertAll(
                () -> assertThat(freePost.getId()).isNull(),
                () -> assertThat(freePost.getTitle()).isEqualTo("제목"),
                () -> assertThat(freePost.getContent()).isEqualTo("내용"),
                () -> assertThat(freePost.getLikes()).isEqualTo(0),
                () -> assertThat(freePost.getComments()).isEqualTo(0),
                () -> assertThat(freePost.getMember().getId()).isEqualTo(1L)
        );
    }

    @Test
    public void FreePost는_FreePostCreate로_업데이트할_수_있다() {

        // given
        FreePostCreate freePostCreate = FreePostCreate.builder()
                .title("변경된 제목")
                .content("변경된 내용")
                .build();

        Member member = Member.builder()
                .id(1L)
                .build();

        FreePost freePost = FreePost.builder()
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
        freePost = freePost.update(freePostCreate);

        // then
        assertThat(freePost.getId()).isEqualTo(1L);
        assertThat(freePost.getTitle()).isEqualTo("변경된 제목");
        assertThat(freePost.getContent()).isEqualTo("변경된 내용");
        assertThat(freePost.getLikes()).isEqualTo(0);
        assertThat(freePost.getComments()).isEqualTo(0);
        assertThat(freePost.getMember().getId()).isEqualTo(1L);
    }

    @Test
    public void FreePost는_좋아요_수를_업데이트할_수_있다() {

        // given
        Member member = Member.builder()
                .id(1L)
                .build();

        FreePost freePost = FreePost.builder()
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
        freePost = freePost.updateLikes(freePost.getLikes() + 1);

        // then
        assertThat(freePost.getId()).isEqualTo(1L);
        assertThat(freePost.getTitle()).isEqualTo("제목");
        assertThat(freePost.getContent()).isEqualTo("내용");
        assertThat(freePost.getLikes()).isEqualTo(1);
        assertThat(freePost.getComments()).isEqualTo(0);
        assertThat(freePost.getMember().getId()).isEqualTo(1L);
    }

    @Test
    public void FreePost는_댓글_수를_업데이트할_수_있다() {

        // given
        Member member = Member.builder()
                .id(1L)
                .build();

        FreePost freePost = FreePost.builder()
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
        freePost = freePost.updateComments(freePost.getComments() + 1);

        // then
        assertThat(freePost.getId()).isEqualTo(1L);
        assertThat(freePost.getTitle()).isEqualTo("제목");
        assertThat(freePost.getContent()).isEqualTo("내용");
        assertThat(freePost.getLikes()).isEqualTo(0);
        assertThat(freePost.getComments()).isEqualTo(1);
        assertThat(freePost.getMember().getId()).isEqualTo(1L);
    }


}
