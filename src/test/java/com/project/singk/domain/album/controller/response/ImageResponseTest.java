package com.project.singk.domain.album.controller.response;

import com.project.singk.domain.album.domain.AlbumImage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ImageResponseTest {

    @Test
    public void AlbumImage로_응답을_생성할_수_있다() {
        // given
        AlbumImage image = AlbumImage.builder()
                .imageUrl("https://i.scdn.co/image/ab67616d0000b273b657fbb27b17e7bd4691c2b2")
                .width(640)
                .height(640)
                .build();

        // when
        final ImageResponse response = ImageResponse.from(image);

        // then
        assertAll(
                () -> assertThat(response.getImageUrl()).isEqualTo("https://i.scdn.co/image/ab67616d0000b273b657fbb27b17e7bd4691c2b2"),
                () -> assertThat(response.getWidth()).isEqualTo(640),
                () -> assertThat(response.getHeight()).isEqualTo(640)
        );
    }
}
