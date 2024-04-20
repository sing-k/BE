package com.project.singk.domain.album.domain;

import java.time.LocalDateTime;

import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ALBUM_GENRES")
public class AlbumGenre extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "album_id")
	private Album album;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "genre_id")
	private Genre genre;
}
