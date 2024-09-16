package com.project.singk.domain.vote.service;

import com.project.singk.domain.album.domain.*;
import com.project.singk.domain.album.service.port.AlbumRepository;
import com.project.singk.domain.member.domain.Gender;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.domain.review.service.port.AlbumReviewRepository;
import com.project.singk.domain.vote.controller.port.VoteService;
import com.project.singk.domain.vote.domain.AlbumReviewVote;
import com.project.singk.domain.vote.domain.VoteCreate;
import com.project.singk.domain.vote.domain.VoteType;
import com.project.singk.domain.vote.service.port.AlbumReviewVoteRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.mock.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class VoteServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AlbumReviewVoteRepository albumReviewVoteRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AlbumReviewRepository albumReviewRepository;
    @Autowired
    private VoteService voteService;
    @BeforeEach
    public void init() {
        List<Member> members = memberRepository.saveAll(List.of(
                Member.builder()
                        .gender(Gender.MALE)
                        .statistics(MemberStatistics.empty())
                        .build(),
                Member.builder()
                        .gender(Gender.FEMALE)
                        .statistics(MemberStatistics.empty())
                        .build()
        ));

        List<Artist> artists = List.of(
                Artist.builder()
                        .name("NewJeans")
                        .build()
        );

        List<Track> tracks = List.of(
                Track.builder()
                        .id("1")
                        .name("Bubble Gum")
                        .artists(artists.stream()
                                .map(TrackArtist::from)
                                .toList())
                        .build(),
                Track.builder()
                        .id("2")
                        .name("How Sweet")
                        .artists(artists.stream()
                                .map(TrackArtist::from)
                                .toList())
                        .build(),
                Track.builder()
                        .id("3")
                        .name("How Sweet (Instrumental)")
                        .artists(artists.stream()
                                .map(TrackArtist::from)
                                .toList())
                        .build(),
                Track.builder()
                        .id("4")
                        .name("Bubble Gum (Instrumental)")
                        .artists(artists.stream()
                                .map(TrackArtist::from)
                                .toList())
                        .build()
        );

        List<AlbumImage> images = List.of(
                AlbumImage.builder()
                        .imageUrl("https://i.scdn.co/image/ab67616d0000b273b657fbb27b17e7bd4691c2b2")
                        .build()
        );
        Album album = Album.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .name("How Sweet")
                .type(AlbumType.EP)
                .releasedAt(LocalDateTime.of(2024, 5, 24, 0, 0, 0))
                .tracks(tracks)
                .artists(artists.stream()
                        .map(AlbumArtist::from)
                        .toList())
                .images(images)
                .statistics(AlbumReviewStatistics.empty())
                .build();

        album = albumRepository.save(album);

        albumReviewRepository.save(AlbumReview.builder()
                .id(1L)
                .content("content")
                .score(5)
                .album(album)
                .reviewer(members.get(0))
                .build());
    }

    /**
     * createAlbumReviewVote
     */
    @Test
    public void 자신의_감상평에는_공감과비공감을_할_수_없다() {
        // given
        VoteCreate voteCreate = VoteCreate.builder()
                .type("PROS")
                .build();

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> voteService.createAlbumReviewVote(
                        1L,
                        1L,
                        voteCreate));

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.INVALID_ALBUM_REVIEW_VOTER);
    }

    @Test
    public void 감상평에_중복해서_공감과비공감을_할_수_없다() {
        // given
        VoteCreate voteCreate = VoteCreate.builder()
                .type("PROS")
                .build();

        albumReviewVoteRepository.save(AlbumReviewVote.builder()
                .member(Member.builder()
                        .id(2L)
                        .build())
                .albumReview(AlbumReview.builder()
                        .id(1L)
                        .build())
                .build()
        );
        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> voteService.createAlbumReviewVote(2L, 1L, voteCreate));

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.DUPLICATE_ALBUM_REVIEW_VOTE);
    }

    @Test
    public void VoteCreate로_AlbumReviewVote를_생성할_수_있다() {
        // given
        VoteCreate voteCreate = VoteCreate.builder()
                .type("PROS")
                .build();

        // when
        PkResponseDto result = voteService.createAlbumReviewVote(
                2L,
                1L,
                voteCreate);

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    /**
     * deleteAlbumReviewVote
     */
    @Test
    public void 자신의_감상평에는_공감과비공감을_철회할_수_없다() {
        // given
        VoteCreate voteCreate = VoteCreate.builder()
                .type("PROS")
                .build();

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> voteService.deleteAlbumReviewVote(
                        1L,
                        1L,
                        voteCreate));

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.INVALID_ALBUM_REVIEW_VOTER);
    }

    @Test
    public void 공감이나_비공감을_하지_않았다면_철회할_수_없다() {
        // given
        VoteCreate voteCreate = VoteCreate.builder()
                .type("PROS")
                .build();

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> voteService.deleteAlbumReviewVote(
                        2L,
                        1L,
                        voteCreate));

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.NOT_FOUND_ALBUM_REVIEW_VOTE);
    }

    @Test
    public void 삭제하려는_공감유형이_저장되어있는_공감유형과_다르면_철회할_수_없다() {
        // given
        VoteCreate voteCreate = VoteCreate.builder()
                .type("CONS")
                .build();

        albumReviewVoteRepository.save(AlbumReviewVote.builder()
                .type(VoteType.PROS)
                .member(Member.builder().id(2L).build())
                .albumReview(AlbumReview.builder().id(1L).build())
                .build()
        );

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> voteService.deleteAlbumReviewVote(
                        2L,
                        1L,
                        voteCreate));

        // then
        assertThat(result.getStatus()).isEqualTo(AppHttpStatus.NOT_MATCH_ALBUM_REVIEW_VOTE_TYPE);
    }

    @Test
    public void 투표를_철회할_수_있다() {
        // given
        VoteCreate voteCreate = VoteCreate.builder()
                .type("PROS")
                .build();

        albumReviewVoteRepository.save(AlbumReviewVote.builder()
                .type(VoteType.PROS)
                .member(Member.builder().id(2L).build())
                .albumReview(AlbumReview.builder().id(1L).build())
                .build()
        );

        // when
        voteService.deleteAlbumReviewVote(
                2L,
                1L,
                voteCreate
        );
    }
}
