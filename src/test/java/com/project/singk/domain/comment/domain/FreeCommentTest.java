package com.project.singk.domain.comment.domain;


import com.project.singk.domain.member.domain.*;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.mock.FakePasswordEncoderHolder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class FreeCommentTest {

	@Test
	public void CommentCreate로_FreeComment를_생성할_수_있다() {
		// given
        Member writer = Member.builder()
                .id(1L)
                .nickname("작성자")
                .build();

        FreePost freePost = FreePost.builder()
                .id(1L)
                .title("자유게시글 제목")
                .content("자유게시글 내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();

		CommentCreate commentCreate = CommentCreate.builder()
			.content("댓글 내용")
			.build();

        Long parentId = null;
		// when
		FreeComment freeComment = FreeComment.from(
                commentCreate,
                writer,
                freePost,
                parentId
        );

		// then
		assertAll(
			() -> assertThat(freeComment.getId()).isNull(),
			() -> assertThat(freeComment.getParentId()).isNull(),
			() -> assertThat(freeComment.getContent()).isEqualTo("댓글 내용"),
			() -> assertThat(freeComment.getLikes()).isEqualTo(0),
			() -> assertThat(freeComment.getMember().getNickname()).isEqualTo("작성자"),
			() -> assertThat(freeComment.getPost().getTitle()).isEqualTo("자유게시글 제목")
		);
	}

	@Test
	public void CommentCreate로_FreeComment를_수정할_수_있다() {
        // given
        FreeComment freeComment = FreeComment.builder()
                .id(1L)
                .content("댓글 내용")
                .build();

        CommentCreate commentCreate = CommentCreate.builder()
                .content("수정된 댓글 내용")
                .build();

        // when
        freeComment = freeComment.update(commentCreate);

        // then
        assertThat(freeComment.getContent()).isEqualTo("수정된 댓글 내용");
	}

	@Test
	void FreeComment는_좋아요수를_수정할_수_있다() {
        // given
        FreeComment freeComment = FreeComment.builder()
                .id(1L)
                .content("댓글 내용")
                .likes(0)
                .build();
        // when
        freeComment = freeComment.updateLikes(freeComment.getLikes() + 1);

        // then
        assertThat(freeComment.getLikes()).isEqualTo(1);
	}
}
