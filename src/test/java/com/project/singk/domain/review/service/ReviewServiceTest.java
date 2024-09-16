package com.project.singk.domain.review.service;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumType;
import com.project.singk.domain.album.service.port.AlbumRepository;
import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.member.domain.Role;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.review.controller.port.ReviewService;
import com.project.singk.domain.review.controller.response.AlbumReviewResponse;
import com.project.singk.domain.review.controller.response.AlbumReviewStatisticsResponse;
import com.project.singk.domain.review.controller.response.MyAlbumReviewResponse;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.domain.AlbumReviewCreate;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.domain.review.service.port.AlbumReviewRepository;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AlbumReviewRepository albumReviewRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ReviewService reviewService;

    @BeforeEach
    public void init() {
        memberRepository.save(Member.builder()
                .id(1L)
                .email("singk@gmail.com")
                .password("encodedPassword")
                .name("김철수")
                .nickname("SingK")
                .birthday(LocalDate.of(1999, 12, 30).atStartOfDay())
                .gender(Gender.MALE)
                .role(Role.ROLE_USER)
                .statistics(MemberStatistics.empty())
                .build());
        albumRepository.save(Album.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .name("How Sweet")
                .type(AlbumType.EP)
                .releasedAt(LocalDateTime.of(2024, 5, 24, 0, 0, 0))
                .statistics(AlbumReviewStatistics.empty())
                .build());
    }

    /**
     * createAlbumReview
     */

    @Test
    public void 동일한_Album에_작성한_AlbumReview가_있다면_생성할_수_없다() {
        // given
        albumReviewRepository.save(AlbumReview.builder()
                .album(Album.builder().id("0EhZEM4RRz0yioTgucDhJq").build())
                .reviewer(Member.builder().id(1L).build())
                .build()
        );

        AlbumReviewCreate albumReviewCreate = AlbumReviewCreate.builder()
                .content("감상평입니다")
                .score(4)
                .build();

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> reviewService.createAlbumReview(
                        1L,
                        "0EhZEM4RRz0yioTgucDhJq",
                        albumReviewCreate));

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.DUPLICATE_ALBUM_REVIEW);
    }

    @Test
    public void AlbumReviewCreate로_AlbumReview를_생성할_수_있다() {
        // given
        AlbumReviewCreate albumReviewCreate = AlbumReviewCreate.builder()
                .content("감상평입니다")
                .score(4)
                .build();

        // when
        PkResponseDto result = reviewService.createAlbumReview(
                1L,
                "0EhZEM4RRz0yioTgucDhJq",
                albumReviewCreate
        );

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    /**
     * deleteAlbumReview
     */
    @Test
    public void 본인의_AlbumReview가_아니라면_삭제할_수_없다() {
        // given
        albumReviewRepository.save(AlbumReview.builder()
                .id(1L)
                .reviewer(Member.builder().id(1L).build())
                .album(Album.builder().id("0EhZEM4RRz0yioTgucDhJq").build())
                .build());

        memberRepository.save(Member.builder()
                .id(2L)
                .build());

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> reviewService.deleteAlbumReview(2L, "0EhZEM4RRz0yioTgucDhJq", 1L));

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.FORBIDDEN_ALBUM_REVIEW);
    }

    @Test
    public void 본인의_AlbumReview를_삭제할_수_있다() {
        // given
        albumReviewRepository.save(AlbumReview.builder()
                .id(1L)
                .reviewer(Member.builder().id(1L).build())
                .album(Album.builder().id("0EhZEM4RRz0yioTgucDhJq").build())
                .build());
        // when
        reviewService.deleteAlbumReview(1L, "0EhZEM4RRz0yioTgucDhJq", 1L);
    }

    /**
     * getAlbumReviews
     */

    @Test
    public void AlbumId로_AlbumReview_목록을_최신순으로_조회할_수_있다() {
        // given
        List<Member> members = new ArrayList<>();
        for (long i = 1L; i <= 10L; i++) {
            members.add(Member.builder()
                    .nickname("닉네임" + i)
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .statistics(MemberStatistics.empty())
                    .build());
        }
        memberRepository.saveAll(members);
        Album album = albumRepository.save(Album.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .build());

        List<AlbumReview> albumReviews = new ArrayList<>();
        for (int i = 10; i >= 1; i--) {
            albumReviews.add(AlbumReview.builder()
                    .id((long) i)
                    .content("감상평 내용입니다." + i)
                    .score(i)
                    .prosCount(i)
                    .consCount(0)
                    .createdAt(LocalDateTime.of(2024, 6, i, 0, 0, 0))
                    .reviewer(members.get(10 - i))
                    .album(album)
                    .build());
        }
        albumReviews = albumReviewRepository.saveAll(albumReviews);

        // when
        OffsetPageResponse<AlbumReviewResponse> result = reviewService.getAlbumReviews(1L,"0EhZEM4RRz0yioTgucDhJq", 0, 5,"NEW");

        // then
        assertAll(
                () -> assertThat(result.getOffset()).isEqualTo(0),
                () -> assertThat(result.getLimit()).isEqualTo(5),
                () -> assertThat(result.getTotal()).isEqualTo(10),
                () -> assertThat(result.getItems().get(0).getCreatedAt()).isEqualTo(LocalDateTime.of(2024,6,10,0,0, 0))
        );
    }

    @Test
    public void AlbumId로_AlbumReview_목록을_공감순으로_조회할_수_있다() {
        // given
        List<Member> members = new ArrayList<>();
        for (long i = 1L; i <= 10L; i++) {
            members.add(Member.builder()
                    .id(i)
                    .nickname("닉네임" + i)
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .statistics(MemberStatistics.empty())
                    .build());
        }
        memberRepository.saveAll(members);
        Album album = albumRepository.save(Album.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .build());

        List<AlbumReview> albumReviews = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            albumReviews.add(AlbumReview.builder()
                    .id((long) i)
                    .content("감상평 내용입니다." + i)
                    .score(i)
                    .prosCount(i)
                    .consCount(0)
                    .createdAt(LocalDateTime.of(2024, 6, 6, 0, 0, i))
                    .reviewer(members.get(i - 1))
                    .album(album)
                    .build());
        }
        albumReviews = albumReviewRepository.saveAll(albumReviews);

        // when
        OffsetPageResponse<AlbumReviewResponse> result = reviewService.getAlbumReviews(1L,"0EhZEM4RRz0yioTgucDhJq",0, 5, "LIKES");

        // then
        assertAll(
                () -> assertThat(result.getOffset()).isEqualTo(0),
                () -> assertThat(result.getLimit()).isEqualTo(5),
                () -> assertThat(result.getTotal()).isEqualTo(10),
                () -> assertThat(result.getItems().get(0).getVote().getProsCount()).isEqualTo(10)
        );
    }

    /**
     * getAlbumReviewStatistics
     */
    @Test
    public void AlbumReview_대한_통계를_조회할_수_있다() {
        // given
        List<Member> members = new ArrayList<>();
        for (long i = 1L; i <= 5L; i++) {
            members.add(Member.builder()
                    .id(i)
                    .nickname("닉네임" + i)
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .build());
        }
        memberRepository.saveAll(members);
        Album album = albumRepository.save(Album.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .statistics(AlbumReviewStatistics.empty())
                .build());
        album = albumRepository.save(album);

        // when
        AlbumReviewStatisticsResponse result = reviewService.getAlbumReviewStatistics("0EhZEM4RRz0yioTgucDhJq");

        // then
        assertAll(
                () -> assertThat(result.getCount()).isEqualTo(0),
                () -> assertThat(result.getTotalScore()).isEqualTo(0),
                () -> assertThat(result.getAverageScore()).isEqualTo(0.0),
                () -> assertThat(result.getScoreStatistics().get(0).getRatio()).isEqualTo(0.0),
                () -> assertThat(result.getGenderStatistics().get(0).getAverageScore()).isEqualTo(0.0),
                () -> assertThat(result.getGenderStatistics().get(0).getRatio()).isEqualTo(0.0)
        );
    }

    /**
     * getMyAlbumReview
     */
    @Test
    public void MemberId로_AlbumReview_목록을_최신순으로_조회할_수_있다() {
        // given
        Member member1 = memberRepository.save(Member.builder()
                .id(1L)
                .nickname("SingK1")
                .gender(Gender.MALE)
                .statistics(MemberStatistics.empty())
                .build());
        Member member2 = memberRepository.save(Member.builder()
                .id(2L)
                .nickname("SingK2")
                .gender(Gender.FEMALE)
                .statistics(MemberStatistics.empty())
                .build());

        List<Album> albums = new ArrayList<>();
        for (long i = 1; i <= 20; i++) {
            albums.add(Album.builder()
                    .id("0EhZEM4RRz0yioTgucDhJq" + i)
                    .tracks(new ArrayList<>())
                    .artists(new ArrayList<>())
                    .images(new ArrayList<>())
                    .build());
        }
        albumRepository.saveAll(albums);


        List<AlbumReview> albumReviews = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            albumReviews.add(AlbumReview.builder()
                    .id((long) i)
                    .content("감상평 내용입니다." + i)
                    .score(i)
                    .prosCount(i)
                    .consCount(0)
                    .createdAt(LocalDateTime.of(2024, 6, i, 0, 0, 0))
                    .reviewer(i % 2 == 0 ? member1 : member2)
                    .album(albums.get(i - 1))
                    .build());
        }
        albumReviews = albumReviewRepository.saveAll(albumReviews);

        // when
        OffsetPageResponse<MyAlbumReviewResponse> result = reviewService.getMyAlbumReview(1L, 0, 5,"NEW");

        // then
        assertAll(
                () -> assertThat(result.getOffset()).isEqualTo(0),
                () -> assertThat(result.getLimit()).isEqualTo(5),
                () -> assertThat(result.getTotal()).isEqualTo(10),
                () -> assertThat(result.getItems().get(0).getCreatedAt()).isEqualTo(LocalDateTime.of(2024,6,20,0,0, 0))
        );
    }

    @Test
    public void MemberId로_AlbumReview_목록을_공감순으로_조회할_수_있다() {
        // given
        Member member1 = memberRepository.save(Member.builder()
                .id(1L)
                .nickname("SingK1")
                .gender(Gender.MALE)
                .statistics(MemberStatistics.empty())
                .build());
        Member member2 = memberRepository.save(Member.builder()
                .id(2L)
                .nickname("SingK2")
                .gender(Gender.FEMALE)
                .statistics(MemberStatistics.empty())
                .build());

        List<Album> albums = new ArrayList<>();
        for (long i = 1; i <= 20; i++) {
            albums.add(Album.builder()
                    .id("0EhZEM4RRz0yioTgucDhJq" + i)
                    .tracks(new ArrayList<>())
                    .artists(new ArrayList<>())
                    .images(new ArrayList<>())
                    .build());
        }
        albumRepository.saveAll(albums);


        List<AlbumReview> albumReviews = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            albumReviews.add(AlbumReview.builder()
                    .id((long) i)
                    .content("감상평 내용입니다." + i)
                    .score(i)
                    .prosCount(i)
                    .consCount(0)
                    .createdAt(LocalDateTime.of(2024, 6, i, 0, 0, 0))
                    .reviewer(i % 2 == 0 ? member1 : member2)
                    .album(albums.get(i - 1))
                    .build());
        }
        albumReviews = albumReviewRepository.saveAll(albumReviews);

        // when
        OffsetPageResponse<MyAlbumReviewResponse> result = reviewService.getMyAlbumReview(1L, 0, 5,"LIKES");

        // then
        assertAll(
                () -> assertThat(result.getOffset()).isEqualTo(0),
                () -> assertThat(result.getLimit()).isEqualTo(5),
                () -> assertThat(result.getTotal()).isEqualTo(10),
                () -> assertThat(result.getItems().get(0).getVote().getProsCount()).isEqualTo(20)
        );
    }
}
