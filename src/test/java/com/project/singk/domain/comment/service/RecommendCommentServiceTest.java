package com.project.singk.domain.comment.service;

import com.project.singk.domain.album.domain.GenreType;
import com.project.singk.domain.comment.controller.response.CommentResponse;
import com.project.singk.domain.comment.domain.CommentCreate;
import com.project.singk.domain.comment.domain.FreeComment;
import com.project.singk.domain.comment.domain.RecommendComment;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.domain.RecommendType;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.mock.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecommendCommentServiceTest {

    private TestContainer tc;

    @BeforeEach
    public void init() {
        tc = TestContainer.builder().build();
    }

    @Test
    public void 앨범추천게시글_댓글을_생성할_수_있다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자A")
                .statistics(MemberStatistics.empty())
                .build();
        writer = tc.memberRepository.save(writer);

        RecommendPost recommendPost = RecommendPost.builder()
                .title("앨범추천게시글 제목")
                .content("앨범추천게시글 내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = tc.recommendPostRepository.save(recommendPost);

        CommentCreate commentCreate = CommentCreate.builder()
                .content("자유게시글 댓글")
                .build();

        Long writerId = writer.getId();
        Long postId =  recommendPost.getId();
        Long parentId = null;

        // when
        PkResponseDto result = tc.recommendCommentService.createRecommendComment(
                writerId,
                postId,
                parentId,
                commentCreate
        );

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    public void 앨범추천게시글_대댓글을_생성할_수_있다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자A")
                .statistics(MemberStatistics.empty())
                .build();
        writer = tc.memberRepository.save(writer);

        RecommendPost recommendPost = RecommendPost.builder()
                .title("앨범추천게시글 제목")
                .content("앨범추천게시글 내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = tc.recommendPostRepository.save(recommendPost);

        RecommendComment parentComment = RecommendComment.builder()
                .parentId(null)
                .content("첫 번째 댓글")
                .post(recommendPost)
                .member(writer)
                .build();

        parentComment = tc.recommendCommentRepository.save(parentComment);

        CommentCreate commentCreate = CommentCreate.builder()
                .content("첫 번째 댓글에 대한 대댓글")
                .build();

        Long writerId = writer.getId();
        Long postId = recommendPost.getId();
        Long parentId = parentComment.getId();

        // when
        PkResponseDto result = tc.recommendCommentService.createRecommendComment(
                writerId,
                postId,
                parentId,
                commentCreate
        );

        // then
        assertThat(result.getId()).isEqualTo(2L);
    }

    @Test
    public void 앨범추천게시글_댓글목록을_최신순으로_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = tc.memberRepository.save(writer);

        Long postId = 1L;
        RecommendPost recommendPost = RecommendPost.builder()
                .title("앨범추천게시글 제목")
                .content("앨범추천게시글 내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        System.out.println(recommendPost.getId());
        recommendPost = tc.recommendPostRepository.save(recommendPost);

        LocalDateTime dateTime = LocalDateTime.of(2024, 8, 17, 12, 0, 0, 0);
        for (int i = 1; i <= 3; i++) {
            RecommendComment parent = tc.recommendCommentRepository.save(RecommendComment.builder()
                    .content(i + " 번째 댓글")
                    .parentId(null)
                    .post(recommendPost)
                    .member(writer)
                    .createdAt(dateTime.plusDays(i))
                    .build());

            if (i == 3) continue;

            for (int j = 1; j <= 2; j++) {
                tc.recommendCommentRepository.save(RecommendComment.builder()
                        .content(i + " 번째 댓글에 대한 " + j + " 번째 대댓글")
                        .parentId(parent.getId())
                        .post(recommendPost)
                        .member(writer)
                        .createdAt(dateTime.plusDays(i).plusHours(j))
                        .build());
            }
        }

        Long viewerId = 1L;

        // when
        List<CommentResponse> response = tc.recommendCommentService.getRecommendComments(viewerId, postId);

        // then
        int size = response.size();
        assertAll(
                () -> assertThat(response.size()).isEqualTo(3),
                () -> assertThat(response.get(0).getContent()).isEqualTo("1 번째 댓글"),
                () -> assertThat(response.get(0).getChildren().get(0).getContent()).isEqualTo("1 번째 댓글에 대한 1 번째 대댓글"),
                () -> assertThat(response.get(0).getChildren().size()).isEqualTo(2),
                () -> assertThat(response.get(size - 1).getContent()).isEqualTo("3 번째 댓글"),
                () -> assertThat(response.get(size - 1).getChildren().size()).isEqualTo(0)
        );
    }
    @Test
    public void 내가_작성한_앨범추천게시글_댓글목록을_최신순으로_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = tc.memberRepository.save(writer);

        Long postId = 1L;
        RecommendPost recommendPost = RecommendPost.builder()
                .title("앨범추천게시글 제목")
                .content("앨범추천게시글 내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();

        recommendPost = tc.recommendPostRepository.save(recommendPost);

        LocalDateTime dateTime = LocalDateTime.of(2024, 8, 17, 12, 0, 0, 0);
        for (int i = 1; i <= 3; i++) {
            RecommendComment parent = tc.recommendCommentRepository.save(RecommendComment.builder()
                    .content(i + " 번째 댓글")
                    .parentId(null)
                    .post(recommendPost)
                    .member(writer)
                    .createdAt(dateTime.plusDays(i))
                    .build());

            if (i == 3) continue;

            for (int j = 1; j <= 2; j++) {
                tc.recommendCommentRepository.save(RecommendComment.builder()
                        .content(i + " 번째 댓글에 대한 " + j + " 번째 대댓글")
                        .parentId(parent.getId())
                        .post(recommendPost)
                        .member(writer)
                        .createdAt(dateTime.plusDays(i).plusHours(j))
                        .build());
            }
        }

        Long viewerId = 1L;

        // when
        List<CommentResponse> response = tc.recommendCommentService.getMyRecommendComments(viewerId, postId);

        // then
        int size = response.size();
        assertAll(
                () -> assertThat(response.size()).isEqualTo(3),
                () -> assertThat(response.get(0).getContent()).isEqualTo("1 번째 댓글"),
                () -> assertThat(response.get(0).getChildren().get(0).getContent()).isEqualTo("1 번째 댓글에 대한 1 번째 대댓글"),
                () -> assertThat(response.get(0).getChildren().size()).isEqualTo(2),
                () -> assertThat(response.get(size - 1).getContent()).isEqualTo("3 번째 댓글"),
                () -> assertThat(response.get(size - 1).getChildren().size()).isEqualTo(0)
        );
    }

    @Test
    public void 내가_작성하지_않은_앨범추천게시글_댓글을_수정할_수_없다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자A")
                .build();
        writer = tc.memberRepository.save(writer);

        RecommendPost recommendPost = RecommendPost.builder()
                .title("앨범추천게시글 제목")
                .content("앨범추천게시글 내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        tc.recommendPostRepository.save(recommendPost);

        RecommendComment recommendComment = RecommendComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(recommendPost)
                .member(writer)
                .build();
        recommendComment = tc.recommendCommentRepository.save(recommendComment);

        Long currentMemberId = 2L;
        Long postId = 1L;
        CommentCreate commentCreate = CommentCreate.builder()
                .content("수정할 내용")
                .build();
        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> tc.recommendCommentService.updateRecommendComment(
                        currentMemberId,
                        postId,
                        commentCreate
                )
        );
        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.FORBIDDEN_COMMENT);
    }

    @Test
    public void 내가_작성한_앨범추천게시글_댓글을_수정할_수_있다() {
        // given
        Member writer = Member.builder()
                .id(1L)
                .nickname("작성자A")
                .build();
        writer = tc.memberRepository.save(writer);

        RecommendPost recommendPost = RecommendPost.builder()
                .title("앨범추천게시글 제목")
                .content("앨범추천게시글 내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        tc.recommendPostRepository.save(recommendPost);

        RecommendComment recommendComment = RecommendComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(recommendPost)
                .member(writer)
                .build();
        recommendComment = tc.recommendCommentRepository.save(recommendComment);

        Long currentMemberId = 1L;
        Long postId = 1L;

        CommentCreate commentCreate = CommentCreate.builder()
                .content("수정할 내용")
                .build();
        // when
        PkResponseDto response = tc.recommendCommentService.updateRecommendComment(
                currentMemberId,
                postId,
                commentCreate
        );
        // then
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    public void 내가_작성하지_않은_앨범추천게시글_댓글을_삭제할_수_없다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자A")
                .statistics(MemberStatistics.empty())
                .build();
        writer = tc.memberRepository.save(writer);

        Member currentMember = Member.builder()
                .nickname("현재 로그인한 회원")
                .statistics(MemberStatistics.empty())
                .build();
        currentMember = tc.memberRepository.save(currentMember);

        RecommendPost recommendPost = RecommendPost.builder()
                .title("앨범추천게시글 제목")
                .content("앨범추천게시글 내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = tc.recommendPostRepository.save(recommendPost);

        RecommendComment recommendComment = RecommendComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(recommendPost)
                .member(writer)
                .build();
        recommendComment = tc.recommendCommentRepository.save(recommendComment);

        Long currentMemberId = currentMember.getId();
        Long postId = recommendPost.getId();
        Long commentId = recommendComment.getId();

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> tc.recommendCommentService.deleteRecommendComment(
                        currentMemberId,
                        postId,
                        commentId
                )
        );
        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.FORBIDDEN_COMMENT);
    }

    @Test
    public void 내가_작성한_자유게시글_댓글을_삭제할_수_있다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자A")
                .statistics(MemberStatistics.empty())
                .build();
        writer = tc.memberRepository.save(writer);

        RecommendPost recommendPost = RecommendPost.builder()
                .title("앨범추천게시글 제목")
                .content("앨범추천게시글 내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        recommendPost = tc.recommendPostRepository.save(recommendPost);

        RecommendComment recommendComment = RecommendComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(recommendPost)
                .member(writer)
                .build();
        recommendComment = tc.recommendCommentRepository.save(recommendComment);

        Long currentMemberId = writer.getId();
        Long postId = recommendPost.getId();
        Long commentId = recommendComment.getId();

        // when
        tc.recommendCommentService.deleteRecommendComment(currentMemberId, postId, commentId);
    }
}
