package com.project.singk.domain.album.controller.response;


import com.project.singk.domain.album.domain.Track;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TrackResponseTest {

    @Test
    public void Track으로_응답을_생성할_수_있다() {
        // given
        Track track = Track.builder()
                .id("19D8LNpWwIPpi6hs9BG7dq")
                .name("Bubble Gum")
                .trackNumber(2)
                .duration(200266L)
                .previewUrl("https://p.scdn.co/mp3-preview/6209f0f9f4f3432fd0fce127fec7e27bd22f6223?cid=abcfcd8269394fd7af92b59a576f5033")
                .isPlayable(true)
                .build();

        // when
        final TrackResponse response = TrackResponse.from(track);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo("19D8LNpWwIPpi6hs9BG7dq"),
                () -> assertThat(response.getName()).isEqualTo("Bubble Gum"),
                () -> assertThat(response.getTrackNumber()).isEqualTo(2),
                () -> assertThat(response.getDuration()).isEqualTo(200266L),
                () -> assertThat(response.getPreviewUrl()).isEqualTo("https://p.scdn.co/mp3-preview/6209f0f9f4f3432fd0fce127fec7e27bd22f6223?cid=abcfcd8269394fd7af92b59a576f5033"),
                () -> assertThat(response.isPlayable()).isEqualTo(true)
        );
    }
}
