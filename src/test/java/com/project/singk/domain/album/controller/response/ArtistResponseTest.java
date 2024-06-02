package com.project.singk.domain.album.controller.response;

import com.project.singk.domain.album.domain.Artist;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ArtistResponseTest {

    @Test
    public void Artist로_응답을_생성할_수_있다() {
        // given
        Artist artist = Artist.builder()
                .id("6HvZYsbFfjnjFrWF950C9d")
                .name("NewJeans")
                .build();

        // when
        final ArtistResponse response = ArtistResponse.from(artist);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo("6HvZYsbFfjnjFrWF950C9d"),
                () -> assertThat(response.getName()).isEqualTo("NewJeans")
        );
    }
}
