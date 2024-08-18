package com.project.singk.domain.comment.domain;


import com.project.singk.domain.album.domain.GenreType;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.domain.RecommendType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RecommendCommentTest {

	@Test
	public void CommentCreate로_RecommendComment를_생성할_수_있다() {
		// given
        Member writer = Member.builder()
                .id(1L)
                .nickname("작성자")
                .build();

        RecommendPost recommendPost = RecommendPost.builder()
                .id(1L)
                .title("앨범추천게시글 제목")
                .content("앨범추천게시글 내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();

		CommentCreate commentCreate = CommentCreate.builder()
			.content("댓글 내용")
			.build();

        Long parentId = null;
		// when
		RecommendComment recommendComment = RecommendComment.from(
                commentCreate,
                writer,
                recommendPost,
                parentId
        );

		// then
		assertAll(
			() -> assertThat(recommendComment.getId()).isNull(),
			() -> assertThat(recommendComment.getParentId()).isNull(),
			() -> assertThat(recommendComment.getContent()).isEqualTo("댓글 내용"),
			() -> assertThat(recommendComment.getLikes()).isEqualTo(0),
			() -> assertThat(recommendComment.getMember().getNickname()).isEqualTo("작성자"),
			() -> assertThat(recommendComment.getPost().getTitle()).isEqualTo("앨범추천게시글 제목")
		);
	}

	@Test
	public void CommentCreate로_RecommendComment를_수정할_수_있다() {
        // given
        RecommendComment recommendComment = RecommendComment.builder()
                .id(1L)
                .content("댓글 내용")
                .build();

        CommentCreate commentCreate = CommentCreate.builder()
                .content("수정된 댓글 내용")
                .build();

        // when
        recommendComment = recommendComment.update(commentCreate);

        // then
        assertThat(recommendComment.getContent()).isEqualTo("수정된 댓글 내용");
	}

	@Test
	void RecommendComment는_좋아요수를_수정할_수_있다() {
        // given
        RecommendComment recommendComment = RecommendComment.builder()
                .id(1L)
                .content("댓글 내용")
                .likes(0)
                .build();
        // when
        recommendComment = recommendComment.updateLikes(recommendComment.getLikes() + 1);

        // then
        assertThat(recommendComment.getLikes()).isEqualTo(1);
	}
}
