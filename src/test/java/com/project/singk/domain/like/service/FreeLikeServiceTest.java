package com.project.singk.domain.like.service;


import com.project.singk.domain.comment.domain.FreeComment;
import com.project.singk.domain.comment.service.port.FreeCommentRepository;
import com.project.singk.domain.like.controller.port.FreeLikeService;
import com.project.singk.domain.like.domain.FreeCommentLike;
import com.project.singk.domain.like.domain.FreePostLike;
import com.project.singk.domain.like.service.port.FreeCommentLikeRepository;
import com.project.singk.domain.like.service.port.FreePostLikeRepository;
import com.project.singk.domain.member.domain.*;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.domain.post.service.port.FreePostRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.mock.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class FreeLikeServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FreePostRepository freePostRepository;
    @Autowired
    private FreeCommentRepository freeCommentRepository;
    @Autowired
    private FreePostLikeRepository freePostLikeRepository;
    @Autowired
    private FreeCommentLikeRepository freeCommentLikeRepository;
    @Autowired
    private FreeLikeService freeLikeService;

	@Test
	public void 현재_로그인한_사용자가_자유게시글에_좋아요를_했는지_확인할_수_있다() {
		// given
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        FreePost freePost = FreePost.builder()
                .title("제목")
                .content("내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);

        Member liker = Member.builder()
                .nickname("좋아요 누른 사람")
                .build();
        liker = memberRepository.save(liker);

        FreePostLike freePostLike = FreePostLike.builder()
                .post(freePost)
                .member(liker)
                .build();
        freePostLike = freePostLikeRepository.save(freePostLike);

		// when
		boolean resultA = freeLikeService.getPostLike(liker.getId(), freePost.getId());
        boolean resultB = freeLikeService.getPostLike(writer.getId(), freePost.getId());

		// then
		assertAll(
			() -> assertThat(resultA).isEqualTo(true),
			() -> assertThat(resultB).isEqualTo(false)
		);
	}

    @Test
    public void 자신이_작성한_자유게시글에_좋아요를_할_수_없다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);
        Long writerId = writer.getId();

        FreePost freePost = FreePost.builder()
                .title("제목")
                .content("내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);
        Long postId = freePost.getId();

        // when
        ApiException result = assertThrows(ApiException.class,
                () -> freeLikeService.createPostLike(writerId, postId)
        );

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.INVALID_POST_LIKES);
    }

    @Test
    public void 동일한_자유게시글에_좋아요를_여러번_할_수_없다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        FreePost freePost = FreePost.builder()
                .title("제목")
                .content("내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);
        Long postId = freePost.getId();

        Member liker = Member.builder()
                .nickname("좋아요 누른 사람")
                .build();
        liker = memberRepository.save(liker);
        Long likerId = liker.getId();

        FreePostLike freePostLike = FreePostLike.builder()
                .post(freePost)
                .member(liker)
                .build();
        freePostLike = freePostLikeRepository.save(freePostLike);

        // when
        ApiException result = assertThrows(ApiException.class,
                () -> freeLikeService.createPostLike(likerId, postId)
        );

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.DUPLICATE_POST_LIKES);
    }

    @Test
	public void 자유게시글에_좋아요를_할_수_있다() {
		// given
        Member writer = Member.builder()
                .nickname("작성자")
                .statistics(MemberStatistics.empty())
                .build();
        writer = memberRepository.save(writer);

        FreePost freePost = FreePost.builder()
                .title("제목")
                .content("내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);

        Member liker = Member.builder()
                .nickname("좋아요 누른 사람")
                .statistics(MemberStatistics.empty())
                .build();
        liker = memberRepository.save(liker);

		// when
		PkResponseDto response = freeLikeService.createPostLike(liker.getId(), freePost.getId());

		// then
		assertAll(
			() -> assertThat(response.getId()).isEqualTo(1L)
		);
	}

    @Test
    public void 자유게시글에_좋아요를_하지_않았다면_취소를_할_수_없다() {
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        FreePost freePost = FreePost.builder()
                .title("제목")
                .content("내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);
        Long postId = freePost.getId();

        Member liker = Member.builder()
                .nickname("좋아요를 취소한 사람")
                .build();
        liker = memberRepository.save(liker);
        Long likerId = liker.getId();

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> freeLikeService.deletePostLike(likerId, postId)
        );

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.NOT_FOUND_POST_LIKES);
    }

    @Test
    public void 자유게시글에_등록한_좋아요를_취소_할_수_있다() {
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        FreePost freePost = FreePost.builder()
                .title("제목")
                .content("내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);
        Long postId = freePost.getId();

        Member liker = Member.builder()
                .nickname("좋아요를 취소한 사람")
                .build();
        liker = memberRepository.save(liker);
        Long likerId = liker.getId();

        FreePostLike freePostLike = FreePostLike.builder()
                .post(freePost)
                .member(liker)
                .build();
        freePostLike = freePostLikeRepository.save(freePostLike);

        // when
        freeLikeService.deletePostLike(likerId, postId);
    }

    @Test
    public void 현재_사용자가_자유게시글_댓글에_좋아요를_했는지_확인할_수_있다() {
		// given
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        FreePost freePost = FreePost.builder()
                .title("게시글 제목")
                .content("게시글 내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);

        FreeComment freeComment = FreeComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(freePost)
                .member(writer)
                .build();
        freeComment = freeCommentRepository.save(freeComment);

        Member liker = Member.builder()
                .nickname("좋아요 누른 사람")
                .build();
        liker = memberRepository.save(liker);


        FreeCommentLike freeCommentLike = FreeCommentLike.builder()
                .comment(freeComment)
                .member(liker)
                .build();
        freeCommentLike = freeCommentLikeRepository.save(freeCommentLike);

		// when
		boolean resultA = freeLikeService.getCommentLike(liker.getId(), freeComment.getId());
        boolean resultB = freeLikeService.getCommentLike(writer.getId(), freeComment.getId());

		// then
		assertAll(
			() -> assertThat(resultA).isEqualTo(true),
			() -> assertThat(resultB).isEqualTo(false)
		);
	}

    @Test
    public void 자신이_작성한_자유게시글_댓글에_좋아요를_할_수_없다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);
        Long writerId = writer.getId();

        FreePost freePost = FreePost.builder()
                .title("게시글 제목")
                .content("게시글 내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);
        Long postId = freePost.getId();

        FreeComment freeComment = FreeComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(freePost)
                .member(writer)
                .build();
        freeComment = freeCommentRepository.save(freeComment);
        Long commentId = freeComment.getId();

        // when
        ApiException result = assertThrows(ApiException.class,
                () -> freeLikeService.createCommentLike(writerId, commentId)
        );

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.INVALID_COMMENT_LIKES);
    }

    @Test
    public void 동일한_자유게시글_댓글에_좋아요를_여러번_할_수_없다() {

        // given
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);
        Long writerId = writer.getId();

        FreePost freePost = FreePost.builder()
                .title("게시글 제목")
                .content("게시글 내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);

        FreeComment freeComment = FreeComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(freePost)
                .member(writer)
                .build();
        freeComment = freeCommentRepository.save(freeComment);
        Long commentId = freeComment.getId();

        Member liker = Member.builder()
                .nickname("좋아요 한 사람")
                .build();
        liker = memberRepository.save(liker);
        Long likerId = liker.getId();

        FreeCommentLike freeCommentLike = FreeCommentLike.builder()
                .comment(freeComment)
                .member(liker)
                .build();
        freeCommentLike = freeCommentLikeRepository.save(freeCommentLike);

        // when
        ApiException result = assertThrows(ApiException.class,
                () -> freeLikeService.createCommentLike(likerId, commentId)
        );

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.DUPLICATE_COMMENT_LIKES);
    }

    @Test
    public void 자유게시글_댓글에_좋아요를_할_수_있다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자")
                .statistics(MemberStatistics.empty())
                .build();
        writer = memberRepository.save(writer);

        FreePost freePost = FreePost.builder()
                .title("게시글 제목")
                .content("게시글 내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);

        FreeComment freeComment = FreeComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(freePost)
                .member(writer)
                .build();
        freeComment = freeCommentRepository.save(freeComment);

        Member liker = Member.builder()
                .nickname("좋아요 누른 사람")
                .statistics(MemberStatistics.empty())
                .build();
        liker = memberRepository.save(liker);

        // when
        PkResponseDto response = freeLikeService.createCommentLike(liker.getId(), freeComment.getId());

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(1L)
        );
    }

    @Test
    public void 자유게시글_댓글에_좋아요를_하지_않았다면_취소를_할_수_없다() {
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        FreePost freePost = FreePost.builder()
                .title("제목")
                .content("내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);

        FreeComment freeComment = FreeComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(freePost)
                .member(writer)
                .build();
        freeComment = freeCommentRepository.save(freeComment);
        Long commentId = freeComment.getId();

        Member liker = Member.builder()
                .nickname("좋아요를 취소한 사람")
                .build();
        liker = memberRepository.save(liker);
        Long likerId = liker.getId();

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> freeLikeService.deleteCommentLike(likerId, commentId)
        );

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.NOT_FOUND_COMMENT_LIKES);
    }

    @Test
    public void 자유게시글_댓글에_등록한_좋아요를_취소_할_수_있다() {
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        FreePost freePost = FreePost.builder()
                .title("제목")
                .content("내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);

        FreeComment freeComment = FreeComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(freePost)
                .member(writer)
                .build();
        freeComment = freeCommentRepository.save(freeComment);
        Long commentId = freeComment.getId();

        Member liker = Member.builder()
                .nickname("좋아요를 취소한 사람")
                .build();
        liker = memberRepository.save(liker);
        Long likerId = liker.getId();

        FreeCommentLike freeCommentLike = FreeCommentLike.builder()
                .comment(freeComment)
                .member(liker)
                .build();
        freeCommentLike = freeCommentLikeRepository.save(freeCommentLike);

        // when
        freeLikeService.deleteCommentLike(likerId, commentId);
    }
}
