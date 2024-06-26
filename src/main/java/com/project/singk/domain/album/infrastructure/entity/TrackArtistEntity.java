package com.project.singk.domain.album.infrastructure.entity;

import com.project.singk.domain.album.domain.TrackArtist;
import com.project.singk.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRACK_ARTISTS")
@Getter
@NoArgsConstructor
public class TrackArtistEntity extends BaseTimeEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;

    @Builder
    public TrackArtistEntity(Long id, ArtistEntity artist) {
        this.id = id;
        this.artist = artist;
    }

    public static TrackArtistEntity from (TrackArtist trackArtist) {
        return TrackArtistEntity.builder()
                .id(trackArtist.getId())
                .artist(ArtistEntity.from(trackArtist.getArtist()))
                .build();
    }

    public TrackArtist toModel() {
        return TrackArtist.builder()
                .id(this.id)
                .artist(this.artist.toModel())
                .build();
    }
}
