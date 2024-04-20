package com.project.singk.domain.album.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.project.singk.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "ALBUMS")
public class Album extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "melon_id")
	private Long melonId;

	private String name;

	@Enumerated(EnumType.STRING)
	private AlbumType type;

	private String artist;

	private String imageUrl;

	private LocalDateTime releasedAt;

	@OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
	List<AlbumGenre> albumGenres;
}
