package com.project.singk.domain.post.infrastructure.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class GenrePostId {

    @Column(name = "genre_id")
    private Long genreId;

    @Column(name = "post_id")
    private Long postId;

    @Builder
    public GenrePostId(Long genreId, Long postId) {
        this.genreId = genreId;
        this.postId = postId;
    }
}
