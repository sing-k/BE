package com.project.singk.domain.comment.service;

import com.project.singk.domain.comment.controller.port.FreeCommentService;
import com.project.singk.domain.comment.controller.response.CommentResponse;
import com.project.singk.domain.comment.domain.CommentCreate;
import com.project.singk.domain.comment.domain.FreeComment;
import com.project.singk.domain.comment.service.port.FreeCommentRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.controller.request.FilterSort;
import com.project.singk.domain.post.controller.request.PostSort;
import com.project.singk.domain.post.controller.response.FreePostResponse;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.domain.post.domain.FreePostCreate;
import com.project.singk.domain.post.service.port.FreePostRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.OffsetPageResponse;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.mock.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class FreeCommentServiceTest {

    @Autowired
    private FreeCommentService freeCommentService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FreePostRepository freePostRepository;
    @Autowired
    private FreeCommentRepository freeCommentRepository;

    @Test
    public void 자유게시글_댓글을_생성할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .nickname("작성자A")
                .statistics(MemberStatistics.empty())
                .build();
        writer = memberRepository.save(writer);

        Member currentMember = Member.builder()
                .nickname("현재 로그인한 회원")
                .statistics(MemberStatistics.empty())
                .build();
        writer = memberRepository.save(currentMember);

        Long postId = 1L;
        FreePost freePost = FreePost.builder()
                .title("자유게시글 제목")
                .content("자유게시글 내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePostRepository.save(freePost);

        Long parentId = null;

        CommentCreate commentCreate = CommentCreate.builder()
                .content("자유게시글 댓글")
                .build();

        // when
        PkResponseDto result = freeCommentService.createFreeComment(
                writerId,
                postId,
                parentId,
                commentCreate
        );

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    public void 자유게시글_대댓글을_생성할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .nickname("작성자A")
                .statistics(MemberStatistics.empty())
                .build();
        writer = memberRepository.save(writer);

        Member currentMember = Member.builder()
                .nickname("현재 로그인한 회원")
                .statistics(MemberStatistics.empty())
                .build();
        writer = memberRepository.save(currentMember);

        Long postId = 1L;
        FreePost freePost = FreePost.builder()
                .title("자유게시글 제목")
                .content("자유게시글 내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);

        Long parentId = 1L;
        FreeComment parentComment = FreeComment.builder()
                .parentId(null)
                .content("첫 번째 댓글")
                .post(freePost)
                .member(writer)
                .build();
        parentComment = freeCommentRepository.save(parentComment);

        CommentCreate commentCreate = CommentCreate.builder()
                .content("첫 번째 댓글에 대한 대댓글")
                .build();

        // when
        PkResponseDto result = freeCommentService.createFreeComment(
                writerId,
                postId,
                parentId,
                commentCreate
        );

        // then
        assertThat(result.getId()).isEqualTo(2L);
    }

    @Test
    public void 자유게시글_댓글목록을_최신순으로_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        Long postId = 1L;
        FreePost freePost = FreePost.builder()
                .title("자유게시글 제목")
                .content("자유게시글 내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);

        LocalDateTime dateTime = LocalDateTime.of(2024, 8, 17, 12, 0, 0, 0);
        for (int i = 1; i <= 3; i++) {
            FreeComment parent = freeCommentRepository.save(FreeComment.builder()
                    .content(i + " 번째 댓글")
                    .parentId(null)
                    .post(freePost)
                    .member(writer)
                    .createdAt(dateTime.plusDays(i))
                    .build());

            if (i == 3) continue;

            for (int j = 1; j <= 2; j++) {
                freeCommentRepository.save(FreeComment.builder()
                        .content(i + " 번째 댓글에 대한 " + j + " 번째 대댓글")
                        .parentId(parent.getId())
                        .post(freePost)
                        .member(writer)
                        .createdAt(dateTime.plusDays(i).plusHours(j))
                        .build());
            }
        }

        Long viewerId = 1L;

        // when
        List<CommentResponse> response = freeCommentService.getFreeComments(viewerId, postId);

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
    public void 내가_작성한_자유게시글_댓글목록을_최신순으로_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = memberRepository.save(writer);

        Long postId = 1L;
        FreePost freePost = FreePost.builder()
                .title("자유게시글 제목")
                .content("자유게시글 내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .build();
        freePost = freePostRepository.save(freePost);

        LocalDateTime dateTime = LocalDateTime.of(2024, 8, 17, 12, 0, 0, 0);
        for (int i = 1; i <= 3; i++) {
            FreeComment parent = freeCommentRepository.save(FreeComment.builder()
                    .content(i + " 번째 댓글")
                    .parentId(null)
                    .post(freePost)
                    .member(writer)
                    .createdAt(dateTime.plusDays(i))
                    .build());

            if (i == 3) continue;

            for (int j = 1; j <= 2; j++) {
                freeCommentRepository.save(FreeComment.builder()
                        .content(i + " 번째 댓글에 대한 " + j + " 번째 대댓글")
                        .parentId(parent.getId())
                        .post(freePost)
                        .member(writer)
                        .createdAt(dateTime.plusDays(i).plusHours(j))
                        .build());
            }
        }

        Long viewerId = 1L;

        // when
        List<CommentResponse> response = freeCommentService.getMyFreeComments(viewerId);

        // then
        int size = response.size();
        assertAll(
                () -> assertThat(response.size()).isEqualTo(7),
                () -> assertThat(response.get(0).getContent()).isEqualTo("1 번째 댓글"),
                () -> assertThat(response.get(size - 1).getContent()).isEqualTo("3 번째 댓글")
        );
    }

    @Test
    public void 내가_작성하지_않은_자유게시글_댓글을_수정할_수_없다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자A")
                .build();
        writer = memberRepository.save(writer);

        FreePost post = FreePost.builder()
                .title("제목")
                .content("내용")
                .member(writer)
                .build();
        post = freePostRepository.save(post);

        FreeComment freeComment = FreeComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(post)
                .member(writer)
                .build();
        freeComment = freeCommentRepository.save(freeComment);

        Long currentMemberId = 2L;
        Long postId = 1L;
        CommentCreate commentCreate = CommentCreate.builder()
                .content("수정할 내용")
                .build();
        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> freeCommentService.updateFreeComment(
                        currentMemberId,
                        postId,
                        commentCreate
                )
        );
        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.FORBIDDEN_COMMENT);
    }

    @Test
    public void 내가_작성한_자유게시글_댓글을_수정할_수_있다() {
        // given
        Member writer = Member.builder()
                .id(1L)
                .nickname("작성자A")
                .build();
        writer = memberRepository.save(writer);

        FreePost post = FreePost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .member(writer)
                .build();
        post = freePostRepository.save(post);

        FreeComment freeComment = FreeComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(post)
                .member(writer)
                .build();
        freeComment = freeCommentRepository.save(freeComment);

        Long currentMemberId = 1L;
        Long postId = 1L;

        CommentCreate commentCreate = CommentCreate.builder()
                .content("수정할 내용")
                .build();
        // when
        PkResponseDto response = freeCommentService.updateFreeComment(
                currentMemberId,
                postId,
                commentCreate
        );
        // then
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    public void 내가_작성하지_않은_자유게시글_댓글을_삭제할_수_없다() {
        // given
        Member writer = Member.builder()
                .nickname("작성자A")
                .statistics(MemberStatistics.empty())
                .build();
        writer = memberRepository.save(writer);

        Member currentMember = Member.builder()
                .nickname("현재 로그인한 회원")
                .statistics(MemberStatistics.empty())
                .build();
        currentMember = memberRepository.save(currentMember);

        FreePost post = FreePost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .member(writer)
                .build();
        post = freePostRepository.save(post);

        FreeComment freeComment = FreeComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(post)
                .member(writer)
                .build();
        freeComment = freeCommentRepository.save(freeComment);

        Long currentMemberId = currentMember.getId();
        Long postId = post.getId();
        Long commentId = freeComment.getId();

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> freeCommentService.deleteFreeComment(
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
        writer = memberRepository.save(writer);

        Member currentMember = Member.builder()
                .nickname("현재 로그인한 회원")
                .statistics(MemberStatistics.empty())
                .build();
        currentMember = memberRepository.save(currentMember);

        FreePost post = FreePost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .member(writer)
                .build();
        post = freePostRepository.save(post);

        FreeComment freeComment = FreeComment.builder()
                .parentId(null)
                .content("댓글 내용")
                .post(post)
                .member(writer)
                .build();
        freeComment = freeCommentRepository.save(freeComment);

        Long currentMemberId = writer.getId();
        Long postId = post.getId();
        Long commentId = freeComment.getId();

        // when
        freeCommentService.deleteFreeComment(currentMemberId, postId, commentId);
    }
}
