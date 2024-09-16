package com.project.singk.domain.like.service;


import com.project.singk.domain.album.domain.GenreType;
import com.project.singk.domain.comment.domain.RecommendComment;
import com.project.singk.domain.comment.service.port.FreeCommentRepository;
import com.project.singk.domain.comment.service.port.RecommendCommentRepository;
import com.project.singk.domain.like.controller.port.FreeLikeService;
import com.project.singk.domain.like.controller.port.RecommendLikeService;
import com.project.singk.domain.like.domain.RecommendCommentLike;
import com.project.singk.domain.like.domain.RecommendPostLike;
import com.project.singk.domain.like.service.port.FreeCommentLikeRepository;
import com.project.singk.domain.like.service.port.FreePostLikeRepository;
import com.project.singk.domain.like.service.port.RecommendCommentLikeRepository;
import com.project.singk.domain.like.service.port.RecommendPostLikeRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.domain.RecommendType;
import com.project.singk.domain.post.service.port.FreePostRepository;
import com.project.singk.domain.post.service.port.RecommendPostRepository;
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
class RecommendLikeServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RecommendPostRepository recommendPostRepository;
    @Autowired
    private RecommendCommentRepository recommendCommentRepository;
    @Autowired
    private RecommendPostLikeRepository recommendPostLikeRepository;
    @Autowired
    private RecommendCommentLikeRepository recommendCommentLikeRepository;
    @Autowired
    private RecommendLikeService recommendLikeService;

	@Test
	public void 현재_로그인한_사용자가_추천게시글에_좋아요를_했는지_확인할_수_있다() {
		// given
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        RecommendPost recommendPost = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = recommendPostRepository.save(recommendPost);

        Member liker = Member.builder()
                .nickname("좋아요 누른 사람")
                .build();
        liker = memberRepository.save(liker);

        RecommendPostLike recommendPostLike = RecommendPostLike.builder()
                .post(recommendPost)
                .member(liker)
                .build();
        recommendPostLike = recommendPostLikeRepository.save(recommendPostLike);

		// when
		boolean resultA = recommendLikeService.getPostLike(liker.getId(), recommendPostLike.getId());
        boolean resultB = recommendLikeService.getPostLike(writer.getId(), recommendPostLike.getId());

		// then
		assertAll(
			() -> assertThat(resultA).isEqualTo(true),
			() -> assertThat(resultB).isEqualTo(false)
		);
	}

    @Test
    public void 자신이_작성한_추천게시글에_좋아요를_할_수_없다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);
        Long writerId = writer.getId();

        RecommendPost recommendPost = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = recommendPostRepository.save(recommendPost);
        Long postId = recommendPost.getId();

        // when
        ApiException result = assertThrows(ApiException.class,
                () -> recommendLikeService.createPostLike(writerId, postId)
        );

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.INVALID_POST_LIKES);
    }

    @Test
    public void 동일한_추천게시글에_좋아요를_여러번_할_수_없다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        RecommendPost recommendPost = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = recommendPostRepository.save(recommendPost);
        Long postId = recommendPost.getId();

        Member liker = Member.builder()
                .nickname("좋아요 누른 사람")
                .build();
        liker = memberRepository.save(liker);
        Long likerId = liker.getId();

        RecommendPostLike recommendPostLike = RecommendPostLike.builder()
                .post(recommendPost)
                .member(liker)
                .build();
        recommendPostLike = recommendPostLikeRepository.save(recommendPostLike);

        // when
        ApiException result = assertThrows(ApiException.class,
                () -> recommendLikeService.createPostLike(likerId, postId)
        );

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.DUPLICATE_POST_LIKES);
    }

    @Test
	public void 추천게시글에_좋아요를_할_수_있다() {
		// given
        Member writer = Member.builder()
                .nickname("작성자A")
                .statistics(MemberStatistics.empty())
                .build();
        writer = memberRepository.save(writer);

        Member liker = Member.builder()
                .nickname("좋아요 누른 사람")
                .statistics(MemberStatistics.empty())
                .build();
        liker = memberRepository.save(liker);

        RecommendPost recommendPost = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = recommendPostRepository.save(recommendPost);


        Long likerId = liker.getId();
        Long postId = recommendPost.getId();

		// when
		PkResponseDto response = recommendLikeService.createPostLike(likerId, postId);

		// then
		assertAll(
			() -> assertThat(response.getId()).isEqualTo(1L)
		);
	}

    @Test
    public void 추천게시글에_좋아요를_하지_않았다면_취소를_할_수_없다() {
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        RecommendPost recommendPost = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = recommendPostRepository.save(recommendPost);
        Long postId = recommendPost.getId();

        Member liker = Member.builder()
                .nickname("좋아요를 취소한 사람")
                .build();
        liker = memberRepository.save(liker);
        Long likerId = liker.getId();

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> recommendLikeService.deletePostLike(likerId, postId)
        );

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.NOT_FOUND_POST_LIKES);
    }

    @Test
    public void 추천게시글에_등록한_좋아요를_취소_할_수_있다() {
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        RecommendPost recommendPost = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = recommendPostRepository.save(recommendPost);
        Long postId = recommendPost.getId();

        Member liker = Member.builder()
                .nickname("좋아요를 취소한 사람")
                .build();
        liker = memberRepository.save(liker);
        Long likerId = liker.getId();

        RecommendPostLike recommendPostLike = RecommendPostLike.builder()
                .post(recommendPost)
                .member(liker)
                .build();
        recommendPostLike = recommendPostLikeRepository.save(recommendPostLike);

        // when
        recommendLikeService.deletePostLike(likerId, postId);
    }

    @Test
    public void 현재_사용자가_추천게시글_댓글에_좋아요를_했는지_확인할_수_있다() {
		// given
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        RecommendPost recommendPost = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = recommendPostRepository.save(recommendPost);

        RecommendComment recommendComment = RecommendComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(recommendPost)
                .member(writer)
                .build();
        recommendComment = recommendCommentRepository.save(recommendComment);

        Member liker = Member.builder()
                .nickname("좋아요 누른 사람")
                .build();
        liker = memberRepository.save(liker);


        RecommendCommentLike recommendCommentLike = RecommendCommentLike.builder()
                .comment(recommendComment)
                .member(liker)
                .build();
        recommendCommentLike = recommendCommentLikeRepository.save(recommendCommentLike);

		// when
		boolean resultA = recommendLikeService.getCommentLike(liker.getId(), recommendCommentLike.getId());
        boolean resultB = recommendLikeService.getCommentLike(writer.getId(), recommendCommentLike.getId());

		// then
		assertAll(
			() -> assertThat(resultA).isEqualTo(true),
			() -> assertThat(resultB).isEqualTo(false)
		);
	}

    @Test
    public void 자신이_작성한_추천게시글_댓글에_좋아요를_할_수_없다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);
        Long writerId = writer.getId();

        RecommendPost recommendPost = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = recommendPostRepository.save(recommendPost);

        RecommendComment recommendComment = RecommendComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(recommendPost)
                .member(writer)
                .build();
        recommendComment = recommendCommentRepository.save(recommendComment);
        Long commentId = recommendComment.getId();

        // when
        ApiException result = assertThrows(ApiException.class,
                () -> recommendLikeService.createCommentLike(writerId, commentId)
        );

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.INVALID_COMMENT_LIKES);
    }

    @Test
    public void 동일한_추천게시글_댓글에_좋아요를_여러번_할_수_없다() {

        // given
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);
        Long writerId = writer.getId();

        RecommendPost recommendPost = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = recommendPostRepository.save(recommendPost);

        RecommendComment recommendComment = RecommendComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(recommendPost)
                .member(writer)
                .build();
        recommendComment = recommendCommentRepository.save(recommendComment);
        Long commentId = recommendComment.getId();

        Member liker = Member.builder()
                .nickname("좋아요 한 사람")
                .build();
        liker = memberRepository.save(liker);
        Long likerId = liker.getId();

        RecommendCommentLike recommendCommentLike = RecommendCommentLike.builder()
                .comment(recommendComment)
                .member(liker)
                .build();
        recommendCommentLike = recommendCommentLikeRepository.save(recommendCommentLike);

        // when
        ApiException result = assertThrows(ApiException.class,
                () -> recommendLikeService.createCommentLike(likerId, commentId)
        );

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.DUPLICATE_COMMENT_LIKES);
    }

    @Test
    public void 추천게시글_댓글에_좋아요를_할_수_있다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자A")
                .statistics(MemberStatistics.empty())
                .build();
        writer = memberRepository.save(writer);

        Member liker = Member.builder()
                .nickname("좋아요 누른 사람")
                .statistics(MemberStatistics.empty())
                .build();
        liker = memberRepository.save(liker);

        RecommendPost recommendPost = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = recommendPostRepository.save(recommendPost);

        RecommendComment recommendComment = RecommendComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(recommendPost)
                .member(writer)
                .build();
        recommendComment = recommendCommentRepository.save(recommendComment);

        Long likerId = liker.getId();
        Long commentId = recommendComment.getId();

        // when
        PkResponseDto response = recommendLikeService.createCommentLike(likerId, commentId);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(1L)
        );
    }

    @Test
    public void 추천게시글_댓글에_좋아요를_하지_않았다면_취소를_할_수_없다() {
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        RecommendPost recommendPost = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = recommendPostRepository.save(recommendPost);

        RecommendComment recommendComment = RecommendComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(recommendPost)
                .member(writer)
                .build();
        recommendComment = recommendCommentRepository.save(recommendComment);
        Long commentId = recommendComment.getId();

        Member liker = Member.builder()
                .nickname("좋아요를 취소한 사람")
                .build();
        liker = memberRepository.save(liker);
        Long likerId = liker.getId();

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> recommendLikeService.deleteCommentLike(likerId, commentId)
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

        RecommendPost recommendPost = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = recommendPostRepository.save(recommendPost);

        RecommendComment recommendComment = RecommendComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(recommendPost)
                .member(writer)
                .build();
        recommendComment = recommendCommentRepository.save(recommendComment);
        Long commentId = recommendComment.getId();

        Member liker = Member.builder()
                .nickname("좋아요를 취소한 사람")
                .build();
        liker = memberRepository.save(liker);
        Long likerId = liker.getId();

        RecommendCommentLike recommendCommentLike = RecommendCommentLike.builder()
                .comment(recommendComment)
                .member(liker)
                .build();
        recommendCommentLike = recommendCommentLikeRepository.save(recommendCommentLike);

        // when
        recommendLikeService.deleteCommentLike(likerId, commentId);
    }
}
