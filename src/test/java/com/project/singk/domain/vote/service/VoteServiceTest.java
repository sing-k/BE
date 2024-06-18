package com.project.singk.domain.vote.service;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.domain.review.domain.AlbumReview;
import com.project.singk.domain.vote.domain.AlbumReviewVote;
import com.project.singk.domain.vote.domain.VoteCreate;
import com.project.singk.domain.vote.domain.VoteType;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.domain.PkResponseDto;
import com.project.singk.mock.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VoteServiceTest {

    private TestContainer testContainer;

    @BeforeEach
    public void init() {
        testContainer = TestContainer.builder().build();
        List<Member> members = testContainer.memberRepository.saveAll(List.of(
                Member.builder()
                        .id(1L)
                        .statistics(MemberStatistics.empty())
                        .build(),
                Member.builder()
                        .id(2L)
                        .statistics(MemberStatistics.empty())
                        .build()
        ));

        Album album = testContainer.albumRepository.save(Album.builder()
                .id("0EhZEM4RRz0yioTgucDhJq")
                .build());

        testContainer.albumReviewRepository.save(AlbumReview.builder()
                .id(1L)
                .album(album)
                .reviewer(members.get(0))
                .build());
    }

    @Test
    public void 자신의_감상평에는_공감과비공감을_할_수_없다() {
        // given
        VoteCreate voteCreate = VoteCreate.builder()
                .type("PROS")
                .build();

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> testContainer.voteService.createAlbumReviewVote(
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

        testContainer.albumReviewVoteRepository.save(AlbumReviewVote.builder()
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
                () -> testContainer.voteService.createAlbumReviewVote(2L, 1L, voteCreate));

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
        PkResponseDto result = testContainer.voteService.createAlbumReviewVote(
                2L,
                1L,
                voteCreate);

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    public void 자신의_감상평에는_공감과비공감을_철회할_수_없다() {
        // given
        VoteCreate voteCreate = VoteCreate.builder()
                .type("PROS")
                .build();

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> testContainer.voteService.deleteAlbumReviewVote(
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
                () -> testContainer.voteService.deleteAlbumReviewVote(
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

        testContainer.albumReviewVoteRepository.save(AlbumReviewVote.builder()
                .type(VoteType.PROS)
                .member(Member.builder().id(2L).build())
                .albumReview(AlbumReview.builder().id(1L).build())
                .build()
        );

        // when
        final ApiException result = assertThrows(ApiException.class,
                () -> testContainer.voteService.deleteAlbumReviewVote(
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

        testContainer.albumReviewVoteRepository.save(AlbumReviewVote.builder()
                .type(VoteType.PROS)
                .member(Member.builder().id(2L).build())
                .albumReview(AlbumReview.builder().id(1L).build())
                .build()
        );

        // when
        testContainer.voteService.deleteAlbumReviewVote(
                2L,
                1L,
                voteCreate
        );
    }
}
