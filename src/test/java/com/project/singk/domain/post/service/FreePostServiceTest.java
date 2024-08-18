package com.project.singk.domain.post.service;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.controller.request.FilterSort;
import com.project.singk.domain.post.controller.request.PostSort;
import com.project.singk.domain.post.controller.response.FreePostResponse;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.domain.post.domain.FreePostCreate;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.OffsetPageResponse;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.mock.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FreePostServiceTest {

    private TestContainer tc;

    @BeforeEach
    public void init() {
        tc = TestContainer.builder().build();
    }

    @Test
    public void 자유게시글을_생성할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .id(writerId)
                .nickname("작성자")
                .build();
        tc.memberRepository.save(writer);

        FreePostCreate freePostCreate = FreePostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        // when
        PkResponseDto result = tc.freePostService.createFreePost(
                writerId,
                freePostCreate
        );

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    public void 자유게시글을_단건_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .id(writerId)
                .nickname("작성자")
                .build();
        writer = tc.memberRepository.save(writer);

        FreePost post = FreePost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .likes(0)
                .comments(0)
                .member(writer)
                .createdAt(LocalDateTime.of(2024, 8, 16, 12, 0, 0))
                .modifiedAt(LocalDateTime.of(2024, 8, 16, 13, 0, 0))
                .build();

        post = tc.freePostRepository.save(post);

        Long postId = 1L;
        Long viewerId = 1L;
        // when
        FreePostResponse response = tc.freePostService.getFreePost(viewerId, postId);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(1L),
                () -> assertThat(response.getTitle()).isEqualTo("제목"),
                () -> assertThat(response.getContent()).isEqualTo("내용"),
                () -> assertThat(response.getLike().isLike()).isEqualTo(false),
                () -> assertThat(response.getLike().getCount()).isEqualTo(0),
                () -> assertThat(response.getComments()).isEqualTo(0),
                () -> assertThat(response.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 8, 16, 12, 0, 0)),
                () -> assertThat(response.getModifiedAt()).isEqualTo(LocalDateTime.of(2024, 8, 16, 13, 0, 0))
        );
    }

    @Test
    public void 자유게시글을_최신순으로_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .id(writerId)
                .nickname("작성자")
                .build();
        writer = tc.memberRepository.save(writer);

        List<FreePost> posts = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.of(2024, 8, 16, 12, 0,0,0);
        for (int i = 1; i <= 10; i++) {
            posts.add(FreePost.builder()
                    .title("제목 " + i)
                    .content("내용 " + i)
                    .likes(i)
                    .member(writer)
                    .createdAt(createdAt)
                    .build());
            createdAt = createdAt.minusDays(1);
        }
        posts = tc.freePostRepository.saveAll(posts);
        Long viewerId = 1L;
        int offset = 0;
        int limit = 5;
        String sort = PostSort.LATEST.toString();
        String filter = null;
        String keyword = null;
        // when
        OffsetPageResponse<FreePostResponse> response = tc.freePostService.getFreePosts(
                viewerId,
                offset,
                limit,
                sort,
                filter,
                keyword
        );
        // then
        assertAll(
                () -> assertThat(response.getItems().size()).isEqualTo(5),
                () -> assertThat(response.getItems().get(0).getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 8, 16, 12, 0,0,0)),
                () -> assertThat(response.getItems().get(0).getTitle()).isEqualTo("제목 1"),
                () -> assertThat(response.getItems().get(offset + limit - 1).getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 8, 12, 12, 0,0,0)),
                () -> assertThat(response.getItems().get(offset + limit - 1).getTitle()).isEqualTo("제목 5")
        );
    }

    @Test
    public void 자유게시글을_좋아요순으로_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .id(writerId)
                .nickname("작성자")
                .build();
        writer = tc.memberRepository.save(writer);

        List<FreePost> posts = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.of(2024, 8, 16, 12, 0,0,0);
        for (int i = 1; i <= 10; i++) {
            posts.add(FreePost.builder()
                    .title("제목 " + i)
                    .content("내용 " + i)
                    .likes(i)
                    .member(writer)
                    .createdAt(createdAt)
                    .build());
            createdAt = createdAt.minusDays(1);
        }
        posts = tc.freePostRepository.saveAll(posts);
        Long viewerId = 1L;
        int offset = 0;
        int limit = 5;
        String sort = PostSort.LIKES.toString();
        String filter = null;
        String keyword = null;
        // when
        OffsetPageResponse<FreePostResponse> response = tc.freePostService.getFreePosts(
                viewerId,
                offset,
                limit,
                sort,
                filter,
                keyword
        );
        // then
        assertAll(
                () -> assertThat(response.getItems().size()).isEqualTo(5),
                () -> assertThat(response.getItems().get(0).getLike().getCount()).isEqualTo(10),
                () -> assertThat(response.getItems().get(0).getTitle()).isEqualTo("제목 10"),
                () -> assertThat(response.getItems().get(offset + limit - 1).getLike().getCount()).isEqualTo(6),
                () -> assertThat(response.getItems().get(offset + limit - 1).getTitle()).isEqualTo("제목 6")
        );
    }

    @Test
    public void 자유게시글을_제목으로_검색하여_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .id(writerId)
                .nickname("작성자")
                .build();
        writer = tc.memberRepository.save(writer);

        List<FreePost> posts = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.of(2024, 8, 16, 12, 0,0,0);
        for (int i = 1; i <= 10; i++) {
            posts.add(FreePost.builder()
                    .title(i <= 6 ? "검색용 " + i : "제목 " + i)
                    .content("내용 " + i)
                    .likes(i)
                    .member(writer)
                    .createdAt(createdAt)
                    .build());
            createdAt = createdAt.minusDays(1);
        }
        posts = tc.freePostRepository.saveAll(posts);
        Long viewerId = 1L;
        int offset = 0;
        int limit = 5;
        String sort = PostSort.LATEST.toString();
        String filter = FilterSort.TITLE.toString();
        String keyword = "검색용";
        // when
        OffsetPageResponse<FreePostResponse> response = tc.freePostService.getFreePosts(
                viewerId,
                offset,
                limit,
                sort,
                filter,
                keyword
        );
        // then
        assertAll(
                () -> assertThat(response.getItems().size()).isEqualTo(5),
                () -> assertThat(response.getItems().get(0).getTitle()).isEqualTo("검색용 1"),
                () -> assertThat(response.getTotal()).isEqualTo(6)
        );
    }

    @Test
    public void 자유게시글을_내용으로_검색하여_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .id(writerId)
                .nickname("작성자")
                .build();
        writer = tc.memberRepository.save(writer);

        List<FreePost> posts = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.of(2024, 8, 16, 12, 0,0,0);
        for (int i = 1; i <= 10; i++) {
            posts.add(FreePost.builder()
                    .title("제목 " + i)
                    .content(i <= 6 ? "검색용 " + i : "내용 " + i)
                    .likes(i)
                    .member(writer)
                    .createdAt(createdAt)
                    .build());
            createdAt = createdAt.minusDays(1);
        }
        posts = tc.freePostRepository.saveAll(posts);
        Long viewerId = 1L;
        int offset = 0;
        int limit = 5;
        String sort = PostSort.LATEST.toString();
        String filter = FilterSort.CONTENT.toString();
        String keyword = "검색용";
        // when
        OffsetPageResponse<FreePostResponse> response = tc.freePostService.getFreePosts(
                viewerId,
                offset,
                limit,
                sort,
                filter,
                keyword
        );
        // then
        assertAll(
                () -> assertThat(response.getItems().size()).isEqualTo(5),
                () -> assertThat(response.getItems().get(0).getContent()).isEqualTo("검색용 1"),
                () -> assertThat(response.getTotal()).isEqualTo(6)
        );
    }

    @Test
    public void 자유게시글을_작성자의_닉네임으로_검색하여_조회할_수_있다() {
        // given
        Member writerA = Member.builder()
                .id(1L)
                .nickname("작성자A")
                .build();
        writerA = tc.memberRepository.save(writerA);
        Member writerB = Member.builder()
                .id(2L)
                .nickname("작성자B")
                .build();
        writerB = tc.memberRepository.save(writerB);

        List<FreePost> posts = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.of(2024, 8, 16, 12, 0,0,0);
        for (int i = 1; i <= 10; i++) {
            posts.add(FreePost.builder()
                    .title("제목 " + i)
                    .content("내용 " + i)
                    .likes(i)
                    .member(i <= 6 ? writerA : writerB)
                    .createdAt(createdAt)
                    .build());
            createdAt = createdAt.minusDays(1);
        }
        posts = tc.freePostRepository.saveAll(posts);
        Long viewerId = 1L;
        int offset = 0;
        int limit = 5;
        String sort = PostSort.LATEST.toString();
        String filter = FilterSort.WRITER.toString();
        String keyword = "A";
        // when
        OffsetPageResponse<FreePostResponse> response = tc.freePostService.getFreePosts(
                viewerId,
                offset,
                limit,
                sort,
                filter,
                keyword
        );
        // then
        assertAll(
                () -> assertThat(response.getItems().size()).isEqualTo(5),
                () -> assertThat(response.getItems().get(0).getWriter().getNickname()).isEqualTo("작성자A"),
                () -> assertThat(response.getTotal()).isEqualTo(6)
        );
    }

    @Test
    public void 내가_작성한_자유게시글을_최신순으로_조회할_수_있다() {
        // given
        Member writerA = Member.builder()
                .id(1L)
                .nickname("작성자A")
                .build();
        writerA = tc.memberRepository.save(writerA);
        Member writerB = Member.builder()
                .id(2L)
                .nickname("작성자B")
                .build();
        writerB = tc.memberRepository.save(writerB);

        List<FreePost> posts = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.of(2024, 8, 16, 12, 0, 0, 0);
        for (int i = 1; i <= 10; i++) {
            posts.add(FreePost.builder()
                    .title("제목 " + i)
                    .content("내용 " + i)
                    .likes(i)
                    .member(i <= 8 ? writerA : writerB)
                    .createdAt(createdAt)
                    .build());
            createdAt = createdAt.minusDays(1);
        }
        posts = tc.freePostRepository.saveAll(posts);
        Long viewerId = 1L;
        int offset = 0;
        int limit = 5;
        // when
        OffsetPageResponse<FreePostResponse> response = tc.freePostService.getMyFreePosts(
                viewerId,
                offset,
                limit
        );
        // then
        assertAll(
                () -> assertThat(response.getItems().size()).isEqualTo(5),
                () -> assertThat(response.getItems().get(0).getWriter().getNickname()).isEqualTo("작성자A"),
                () -> assertThat(response.getItems().get(0).getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 8, 16, 12, 0, 0, 0)),
                () -> assertThat(response.getTotal()).isEqualTo(8)
        );
    }

    @Test
    public void 내가_작성하지_않은_자유게시글을_수정할_수_없다() {
        // given
        Member writer = Member.builder()
                .id(1L)
                .nickname("작성자A")
                .build();
        writer = tc.memberRepository.save(writer);

        FreePost post = FreePost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .member(writer)
                .build();
        post = tc.freePostRepository.save(post);

        Long currentMemberId = 2L;
        Long postId = 1L;
        FreePostCreate freePostCreate = FreePostCreate.builder()
                .title("수정할 제목")
                .content("수정할 내용")
                .build();
        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> tc.freePostService.updateFreePost(
                        currentMemberId,
                        postId,
                        freePostCreate
                )
        );
        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.FORBIDDEN_POST);
    }

    @Test
    public void 내가_작성한_자유게시글을_수정할_수_있다() {
        // given
        Member writer = Member.builder()
                .id(1L)
                .nickname("작성자A")
                .build();
        writer = tc.memberRepository.save(writer);

        FreePost post = FreePost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .member(writer)
                .build();
        post = tc.freePostRepository.save(post);

        Long currentMemberId = 1L;
        Long postId = 1L;
        FreePostCreate freePostCreate = FreePostCreate.builder()
                .title("수정할 제목")
                .content("수정할 내용")
                .build();
        // when
        PkResponseDto response = tc.freePostService.updateFreePost(
                currentMemberId,
                postId,
                freePostCreate
        );
        // then
        assertThat(response.getId()).isEqualTo(1L);
    }


    @Test
    public void 내가_작성하지_않은_자유게시글을_삭제할_수_없다() {
        // given
        Member writer = Member.builder()
                .id(1L)
                .nickname("작성자A")
                .build();
        writer = tc.memberRepository.save(writer);

        FreePost post = FreePost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .member(writer)
                .build();
        post = tc.freePostRepository.save(post);

        Long currentMemberId = 2L;
        Long postId = 1L;

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> tc.freePostService.deleteFreePost(
                        currentMemberId,
                        postId
                )
        );
        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.FORBIDDEN_POST);
    }

    @Test
    public void 내가_작성한_자유게시글을_삭제할_수_있다() {
        // given
        Member writer = Member.builder()
                .id(1L)
                .nickname("작성자A")
                .build();
        writer = tc.memberRepository.save(writer);

        FreePost post = FreePost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .member(writer)
                .build();
        post = tc.freePostRepository.save(post);

        Long currentMemberId = 1L;
        Long postId = 1L;
        // when
        tc.freePostService.deleteFreePost(currentMemberId, postId);
    }
}
