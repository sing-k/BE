package com.project.singk.domain.album.infrastructure.entity;


import com.project.singk.domain.album.domain.AlbumArtist;
import com.project.singk.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ALBUM_ARTISTS")
@Getter
@NoArgsConstructor
public class AlbumArtistEntity extends BaseTimeEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;

    @Builder
    public AlbumArtistEntity(Long id, ArtistEntity artist) {
        this.id = id;
        this.artist = artist;
    }

    public static AlbumArtistEntity from (AlbumArtist albumArtist) {
        return AlbumArtistEntity.builder()
                .id(albumArtist.getId())
                .artist(ArtistEntity.from(albumArtist.getArtist()))
                .build();
    }

    public AlbumArtist toModel() {
        return AlbumArtist.builder()
                .id(this.id)
                .artist(this.artist.toModel())
                .build();
    }
}
