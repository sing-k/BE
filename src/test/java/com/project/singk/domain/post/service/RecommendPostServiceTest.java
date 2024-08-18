package com.project.singk.domain.post.service;

import com.project.singk.domain.album.domain.AlbumImage;
import com.project.singk.domain.album.domain.GenreType;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.controller.request.FilterSort;
import com.project.singk.domain.post.controller.request.PostSort;
import com.project.singk.domain.post.controller.response.RecommendPostResponse;
import com.project.singk.domain.post.domain.*;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.OffsetPageResponse;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.mock.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecommendPostServiceTest {

    private TestContainer tc;

    @BeforeEach
    public void init() {
        tc = TestContainer.builder().build();
    }

    @Test
    public void 이미지타입의_추천게시글을_생성할_수_있다() {
        // given
        Long currentMemberId = 1L;
        Member writer = Member.builder()
                .id(currentMemberId)
                .nickname("작성자")
                .build();
        tc.memberRepository.save(writer);

        RecommendPostCreate recommendPostCreate = RecommendPostCreate.builder()
                .title("제목")
                .content("내용")
                .type(RecommendType.IMAGE.toString())
                .link(null)
                .genre(GenreType.POP.toString())
                .build();

        MockMultipartFile image = new MockMultipartFile(
                "image",
                "test.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes()
        );

        // when
        PkResponseDto result = tc.recommendPostService.createRecommendPost(
                currentMemberId,
                recommendPostCreate,
                image
        );

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    public void 앨범타입의_추천게시글을_생성할_수_있다() {
        // given
        Long currentMemberId = 1L;
        Member writer = Member.builder()
                .id(currentMemberId)
                .nickname("작성자")
                .build();
        tc.memberRepository.save(writer);

        AlbumImage albumImage = AlbumImage.builder()
                .id(1L)
                .width(360)
                .height(360)
                .imageUrl("albumImageUrl")
                .build();
        tc.albumImageRepository.save(albumImage);

        RecommendPostCreate recommendPostCreate = RecommendPostCreate.builder()
                .title("제목")
                .content("내용")
                .type(RecommendType.ALBUM.toString())
                .link("앨범ID")
                .genre(GenreType.POP.toString())
                .build();

        MockMultipartFile image = null;

        // when
        PkResponseDto result = tc.recommendPostService.createRecommendPost(
                currentMemberId,
                recommendPostCreate,
                image
        );

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    public void 유튜브타입의_추천게시글을_생성할_수_있다() {
        // given
        Long currentMemberId = 1L;
        Member writer = Member.builder()
                .id(currentMemberId)
                .nickname("작성자")
                .build();
        tc.memberRepository.save(writer);

        RecommendPostCreate recommendPostCreate = RecommendPostCreate.builder()
                .title("제목")
                .content("내용")
                .type(RecommendType.YOUTUBE.toString())
                .link("유튜브URL")
                .genre(GenreType.POP.toString())
                .build();

        MockMultipartFile image = null;

        // when
        PkResponseDto result = tc.recommendPostService.createRecommendPost(
                currentMemberId,
                recommendPostCreate,
                image
        );

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    public void 이미지타입의_추천게시글을_단건_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .id(writerId)
                .nickname("작성자")
                .build();
        writer = tc.memberRepository.save(writer);

        MockMultipartFile image = new MockMultipartFile(
                "image",
                "test.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes()
        );
        tc.s3Repository.putObject("image", image);

        RecommendPost post = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.IMAGE)
                .genre(GenreType.POP)
                .link("image")
                .likes(0)
                .comments(0)
                .member(writer)
                .createdAt(LocalDateTime.of(2024, 8, 16, 12, 0, 0))
                .modifiedAt(LocalDateTime.of(2024, 8, 16, 13, 0, 0))
                .build();

        post = tc.recommendPostRepository.save(post);

        Long postId = 1L;
        Long currentMemberId = 1L;
        // when
        RecommendPostResponse response = tc.recommendPostService.getRecommendPost(currentMemberId, postId);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(1L),
                () -> assertThat(response.getTitle()).isEqualTo("제목"),
                () -> assertThat(response.getContent()).isEqualTo("내용"),
                () -> assertThat(response.getRecommend()).isEqualTo("이미지"),
                () -> assertThat(response.getGenre()).isEqualTo("팝"),
                () -> assertThat(response.getLink()).isEqualTo("imageUrl"),
                () -> assertThat(response.getLike().isLike()).isEqualTo(false),
                () -> assertThat(response.getLike().getCount()).isEqualTo(0),
                () -> assertThat(response.getComments()).isEqualTo(0),
                () -> assertThat(response.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 8, 16, 12, 0, 0)),
                () -> assertThat(response.getModifiedAt()).isEqualTo(LocalDateTime.of(2024, 8, 16, 13, 0, 0))
        );
    }

    @Test
    public void 앨범타입의_추천게시글을_단건_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .id(writerId)
                .nickname("작성자")
                .build();
        writer = tc.memberRepository.save(writer);

        AlbumImage albumImage = AlbumImage.builder()
                .id(1L)
                .width(360)
                .height(360)
                .imageUrl("imageUrl")
                .build();
        tc.albumImageRepository.save(albumImage);

        RecommendPost post = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.ALBUM)
                .genre(GenreType.POP)
                .link("앨범ID")
                .likes(0)
                .comments(0)
                .member(writer)
                .createdAt(LocalDateTime.of(2024, 8, 16, 12, 0, 0))
                .modifiedAt(LocalDateTime.of(2024, 8, 16, 13, 0, 0))
                .build();

        post = tc.recommendPostRepository.save(post);

        Long postId = 1L;
        Long currentMemberId = 1L;
        // when
        RecommendPostResponse response = tc.recommendPostService.getRecommendPost(currentMemberId, postId);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(1L),
                () -> assertThat(response.getTitle()).isEqualTo("제목"),
                () -> assertThat(response.getContent()).isEqualTo("내용"),
                () -> assertThat(response.getRecommend()).isEqualTo("앨범"),
                () -> assertThat(response.getGenre()).isEqualTo("팝"),
                () -> assertThat(response.getLink()).isEqualTo("앨범ID"),
                () -> assertThat(response.getLike().isLike()).isEqualTo(false),
                () -> assertThat(response.getLike().getCount()).isEqualTo(0),
                () -> assertThat(response.getComments()).isEqualTo(0),
                () -> assertThat(response.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 8, 16, 12, 0, 0)),
                () -> assertThat(response.getModifiedAt()).isEqualTo(LocalDateTime.of(2024, 8, 16, 13, 0, 0))
        );
    }

    @Test
    public void 유튜브타입의_추천게시글을_단건_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .id(writerId)
                .nickname("작성자")
                .build();
        writer = tc.memberRepository.save(writer);

        RecommendPost post = RecommendPost.builder()
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .genre(GenreType.POP)
                .link("유튜브URL")
                .likes(0)
                .comments(0)
                .member(writer)
                .createdAt(LocalDateTime.of(2024, 8, 16, 12, 0, 0))
                .modifiedAt(LocalDateTime.of(2024, 8, 16, 13, 0, 0))
                .build();

        post = tc.recommendPostRepository.save(post);

        Long postId = 1L;
        Long currentMemberId = 1L;
        // when
        RecommendPostResponse response = tc.recommendPostService.getRecommendPost(currentMemberId, postId);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(1L),
                () -> assertThat(response.getTitle()).isEqualTo("제목"),
                () -> assertThat(response.getContent()).isEqualTo("내용"),
                () -> assertThat(response.getRecommend()).isEqualTo("유튜브"),
                () -> assertThat(response.getGenre()).isEqualTo("팝"),
                () -> assertThat(response.getLink()).isEqualTo("유튜브URL"),
                () -> assertThat(response.getLike().isLike()).isEqualTo(false),
                () -> assertThat(response.getLike().getCount()).isEqualTo(0),
                () -> assertThat(response.getComments()).isEqualTo(0),
                () -> assertThat(response.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 8, 16, 12, 0, 0)),
                () -> assertThat(response.getModifiedAt()).isEqualTo(LocalDateTime.of(2024, 8, 16, 13, 0, 0))
        );
    }

    @Test
    public void 추천게시글을_최신순으로_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .nickname("작성자")
                .build();
        writer = tc.memberRepository.save(writer);

        MockMultipartFile image = new MockMultipartFile(
                "image",
                "test.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes()
        );
        tc.s3Repository.putObject("image", image);

        AlbumImage albumImage = AlbumImage.builder()
                .id(1L)
                .width(360)
                .height(360)
                .imageUrl("albumImageUrl")
                .build();
        tc.albumImageRepository.save(albumImage);

        List<RecommendPost> posts = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.of(2024, 8, 16, 12, 0,0,0);

        // 이미지 타입
        posts.add(RecommendPost.builder()
                .title("이미지 타입 게시글")
                .content("이미지 타입 내용")
                .recommend(RecommendType.IMAGE)
                .genre(GenreType.POP)
                .link("image")
                .likes(0)
                .comments(0)
                .member(writer)
                .createdAt(createdAt)
                .build());
        // 앨범 타입
        posts.add(RecommendPost.builder()
                .title("앨범 타입 제목")
                .content("앨범 타입 내용")
                .recommend(RecommendType.ALBUM)
                .genre(GenreType.POP)
                .link("앨범ID")
                .likes(0)
                .comments(0)
                .member(writer)
                .createdAt(createdAt.plusDays(1))
                .build());
        // 유튜브 타입
        posts.add(RecommendPost.builder()
                .title("유튜브 타입 제목")
                .content("유튜브 타입 내용")
                .recommend(RecommendType.YOUTUBE)
                .genre(GenreType.POP)
                .link("유튜브URL")
                .likes(0)
                .comments(0)
                .member(writer)
                .createdAt(createdAt.plusDays(2))
                .build());
        posts = tc.recommendPostRepository.saveAll(posts);

        Long currentMemberId = 1L;
        int offset = 0;
        int limit = 5;
        String sort = PostSort.LATEST.toString();
        String filter = null;
        String keyword = null;
        // when
        OffsetPageResponse<RecommendPostResponse> response = tc.recommendPostService.getRecommendPosts(
                currentMemberId,
                offset,
                limit,
                sort,
                filter,
                keyword
        );
        // then
        assertAll(
                () -> assertThat(response.getItems().size()).isEqualTo(3),
                () -> assertThat(response.getItems().get(0).getRecommend()).isEqualTo("유튜브"),
                () -> assertThat(response.getItems().get(0).getLink()).isEqualTo("유튜브URL"),
                () -> assertThat(response.getItems().get(1).getRecommend()).isEqualTo("앨범"),
                () -> assertThat(response.getItems().get(1).getLink()).isEqualTo("앨범ID"),
                () -> assertThat(response.getItems().get(2).getRecommend()).isEqualTo("이미지"),
                () -> assertThat(response.getItems().get(2).getLink()).isEqualTo("imageUrl")
        );
    }

    @Test
    public void 추천게시글을_좋아요순으로_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .id(writerId)
                .nickname("작성자")
                .build();
        writer = tc.memberRepository.save(writer);

        MockMultipartFile image = new MockMultipartFile(
                "image",
                "test.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes()
        );
        tc.s3Repository.putObject("image", image);

        AlbumImage albumImage = AlbumImage.builder()
                .id(1L)
                .width(360)
                .height(360)
                .imageUrl("albumImageUrl")
                .build();
        tc.albumImageRepository.save(albumImage);

        List<RecommendPost> posts = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.of(2024, 8, 16, 12, 0,0,0);

        // 이미지 타입
        posts.add(RecommendPost.builder()
                .title("이미지 타입 게시글")
                .content("이미지 타입 내용")
                .recommend(RecommendType.IMAGE)
                .genre(GenreType.POP)
                .link("image")
                .likes(2)
                .comments(0)
                .member(writer)
                .createdAt(createdAt)
                .build());
        // 앨범 타입
        posts.add(RecommendPost.builder()
                .title("앨범 타입 제목")
                .content("앨범 타입 내용")
                .recommend(RecommendType.ALBUM)
                .genre(GenreType.POP)
                .link("앨범ID")
                .likes(1)
                .comments(0)
                .member(writer)
                .createdAt(createdAt.plusDays(1))
                .build());
        // 유튜브 타입
        posts.add(RecommendPost.builder()
                .title("유튜브 타입 제목")
                .content("유튜브 타입 내용")
                .recommend(RecommendType.YOUTUBE)
                .genre(GenreType.POP)
                .link("유튜브URL")
                .likes(3)
                .comments(0)
                .member(writer)
                .createdAt(createdAt.plusDays(2))
                .build());

        posts = tc.recommendPostRepository.saveAll(posts);

        Long currentMemberId = 1L;
        int offset = 0;
        int limit = 5;
        String sort = PostSort.LIKES.toString();
        String filter = null;
        String keyword = null;
        // when
        OffsetPageResponse<RecommendPostResponse> response = tc.recommendPostService.getRecommendPosts(
                currentMemberId,
                offset,
                limit,
                sort,
                filter,
                keyword
        );
        // then
        assertAll(
                () -> assertThat(response.getItems().size()).isEqualTo(3),
                () -> assertThat(response.getItems().get(0).getRecommend()).isEqualTo("유튜브"),
                () -> assertThat(response.getItems().get(0).getLink()).isEqualTo("유튜브URL"),
                () -> assertThat(response.getItems().get(1).getRecommend()).isEqualTo("이미지"),
                () -> assertThat(response.getItems().get(1).getLink()).isEqualTo("imageUrl"),
                () -> assertThat(response.getItems().get(2).getRecommend()).isEqualTo("앨범"),
                () -> assertThat(response.getItems().get(2).getLink()).isEqualTo("앨범ID")
        );
    }

    @Test
    public void 추천게시글을_제목으로_검색하여_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .id(writerId)
                .nickname("작성자")
                .build();
        writer = tc.memberRepository.save(writer);

        List<RecommendPost> posts = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.of(2024, 8, 16, 12, 0,0,0);
        for (int i = 1; i <= 10; i++) {
            posts.add(RecommendPost.builder()
                    .title(i <= 6 ? "검색용 " + i : "제목 " + i)
                    .content("내용 " + i)
                    .recommend(RecommendType.YOUTUBE)
                    .link("유튜브 링크")
                    .genre(GenreType.POP)
                    .likes(i)
                    .member(writer)
                    .createdAt(createdAt)
                    .build());
            createdAt = createdAt.minusDays(1);
        }
        posts = tc.recommendPostRepository.saveAll(posts);
        Long viewerId = 1L;
        int offset = 0;
        int limit = 5;
        String sort = PostSort.LATEST.toString();
        String filter = FilterSort.TITLE.toString();
        String keyword = "검색용";
        // when
        OffsetPageResponse<RecommendPostResponse> response = tc.recommendPostService.getRecommendPosts(
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
    public void 추천게시글을_내용으로_검색하여_조회할_수_있다() {
        // given
        Long writerId = 1L;
        Member writer = Member.builder()
                .id(writerId)
                .nickname("작성자")
                .build();
        writer = tc.memberRepository.save(writer);

        List<RecommendPost> posts = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.of(2024, 8, 16, 12, 0,0,0);
        for (int i = 1; i <= 10; i++) {
            posts.add(RecommendPost.builder()
                    .title("제목 " + i)
                    .content(i <= 6 ? "검색용 " + i : "내용 " + i)
                    .likes(i)
                    .recommend(RecommendType.YOUTUBE)
                    .link("유튜브URL")
                    .genre(GenreType.POP)
                    .member(writer)
                    .createdAt(createdAt)
                    .build());
            createdAt = createdAt.minusDays(1);
        }
        posts = tc.recommendPostRepository.saveAll(posts);
        Long viewerId = 1L;
        int offset = 0;
        int limit = 5;
        String sort = PostSort.LATEST.toString();
        String filter = FilterSort.CONTENT.toString();
        String keyword = "검색용";
        // when
        OffsetPageResponse<RecommendPostResponse> response = tc.recommendPostService.getRecommendPosts(
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
    public void 추천게시글을_작성자의_닉네임으로_검색하여_조회할_수_있다() {
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

        List<RecommendPost> posts = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.of(2024, 8, 16, 12, 0,0,0);
        for (int i = 1; i <= 10; i++) {
            posts.add(RecommendPost.builder()
                    .title("제목 " + i)
                    .content("내용 " + i)
                    .likes(i)
                    .recommend(RecommendType.YOUTUBE)
                    .link("유튜브URL")
                    .genre(GenreType.POP)
                    .member(i <= 6 ? writerA : writerB)
                    .createdAt(createdAt)
                    .build());
            createdAt = createdAt.minusDays(1);
        }
        posts = tc.recommendPostRepository.saveAll(posts);
        Long viewerId = 1L;
        int offset = 0;
        int limit = 5;
        String sort = PostSort.LATEST.toString();
        String filter = FilterSort.WRITER.toString();
        String keyword = "A";
        // when
        OffsetPageResponse<RecommendPostResponse> response = tc.recommendPostService.getRecommendPosts(
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
    public void 내가_작성한_추천게시글을_최신순으로_조회할_수_있다() {
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

        List<RecommendPost> posts = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.of(2024, 8, 16, 12, 0, 0, 0);
        for (int i = 1; i <= 10; i++) {
            posts.add(RecommendPost.builder()
                    .title("제목 " + i)
                    .content("내용 " + i)
                    .likes(i)
                    .recommend(RecommendType.YOUTUBE)
                    .link("유튜브URL")
                    .genre(GenreType.POP)
                    .member(i <= 8 ? writerA : writerB)
                    .createdAt(createdAt)
                    .build());
            createdAt = createdAt.minusDays(1);
        }
        posts = tc.recommendPostRepository.saveAll(posts);
        Long viewerId = 1L;
        int offset = 0;
        int limit = 5;
        // when
        OffsetPageResponse<RecommendPostResponse> response = tc.recommendPostService.getMyRecommendPosts(
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
    public void 내가_작성하지_않은_추천게시글을_수정할_수_없다() {
        // given
        Member writer = Member.builder()
                .id(1L)
                .nickname("작성자A")
                .build();
        writer = tc.memberRepository.save(writer);

        RecommendPost post = RecommendPost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .member(writer)
                .build();
        post = tc.recommendPostRepository.save(post);

        Long currentMemberId = 2L;
        Long postId = 1L;
        RecommendPostUpdate recommendPostUpdate = RecommendPostUpdate.builder()
                .title("수정할 제목")
                .content("수정할 내용")
                .build();
        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> tc.recommendPostService.updateRecommendPost(
                        currentMemberId,
                        postId,
                        recommendPostUpdate
                )
        );
        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.FORBIDDEN_POST);
    }

    @Test
    public void 내가_작성한_추천게시글을_수정할_수_있다() {
        // given
        Member writer = Member.builder()
                .id(1L)
                .nickname("작성자A")
                .build();
        writer = tc.memberRepository.save(writer);

        RecommendPost post = RecommendPost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .member(writer)
                .build();
        post = tc.recommendPostRepository.save(post);

        Long currentMemberId = 1L;
        Long postId = 1L;
        RecommendPostUpdate recommendPostUpdate = RecommendPostUpdate.builder()
                .title("수정할 제목")
                .content("수정할 내용")
                .build();
        // when
        PkResponseDto response = tc.recommendPostService.updateRecommendPost(
                currentMemberId,
                postId,
                recommendPostUpdate
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

        RecommendPost post = RecommendPost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .member(writer)
                .build();
        post = tc.recommendPostRepository.save(post);

        Long currentMemberId = 2L;
        Long postId = 1L;

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> tc.recommendPostService.deleteRecommendPost(
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

        RecommendPost post = RecommendPost.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .recommend(RecommendType.YOUTUBE)
                .link("유튜브URL")
                .genre(GenreType.POP)
                .member(writer)
                .build();
        post = tc.recommendPostRepository.save(post);

        Long currentMemberId = 1L;
        Long postId = 1L;
        // when
        tc.recommendPostService.deleteRecommendPost(currentMemberId, postId);
    }
}
