package com.project.singk.domain.album.controller.response;

import com.project.singk.domain.album.domain.Album;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class AlbumSimpleResponse {

    private String id;
    private String name;
    private List<ArtistResponse> artists;
    private List<ImageResponse> images;

    public static AlbumSimpleResponse from (Album album) {
        return AlbumSimpleResponse.builder()
                .id(album.getId())
                .name(album.getName())
                .artists(album.getArtists().stream()
                        .map(ArtistResponse::from)
                        .toList())
                .images(album.getImages().stream()
                        .map(ImageResponse::from)
                        .toList())
                .build();
    }
}
