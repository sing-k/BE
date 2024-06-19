package com.project.singk.domain.post.infrastructure.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class GenreAlbumRecommendPostId {
    @Column(name = "genre_id")
    private Long genreId;

    @Column(name = "post_id")
    private Long postId;

    @Builder
    public GenreAlbumRecommendPostId(Long genreId, Long postId) {
        this.genreId = genreId;
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreAlbumRecommendPostId that = (GenreAlbumRecommendPostId) o;
        return Objects.equals(postId, that.postId) &&
                Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, genreId);
    }
}
