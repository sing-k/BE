package com.project.singk.domain.post.infrastructure.entity;

import com.project.singk.domain.post.infrastructure.entity.key.GenreAlbumRecommendPostId;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Genre_RecommendPosts_Mapping", indexes = {
        @Index(name = "idx_genre_post", columnList = "genre_id, post_id")
})
@Getter
@NoArgsConstructor
public class GenreRecommendPostMappingEntity {
    @EmbeddedId
    private GenreAlbumRecommendPostId id;

    @ManyToOne
    @MapsId("genreId")
    @JoinColumn(name = "genre_id", insertable = false, updatable = false)
    private AlbumGenreEntity genre;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private PostEntity post;

    @Builder
    public GenreRecommendPostMappingEntity(GenreAlbumRecommendPostId id, AlbumGenreEntity genre, PostEntity post) {
        this.id = id;
        this.genre = genre;
        this.post = post;
    }
}
