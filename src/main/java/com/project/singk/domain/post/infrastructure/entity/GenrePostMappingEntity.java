package com.project.singk.domain.post.infrastructure.entity;

import com.project.singk.domain.post.infrastructure.entity.key.GenrePostId;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Genre_Posts_Mapping")
@Getter
@NoArgsConstructor
public class GenrePostMappingEntity {
    @EmbeddedId
    private GenrePostId genrePostId;

    @Builder
    public GenrePostMappingEntity(GenrePostId genrePostId) {
        this.genrePostId = genrePostId;
    }
}
