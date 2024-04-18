package com.project.singk.domain.album.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumGenre;
import com.project.singk.domain.album.domain.Genre;
import com.project.singk.domain.album.dto.AlbumRequestDto;
import com.project.singk.domain.album.repository.AlbumGenreRepository;
import com.project.singk.domain.album.repository.AlbumRepository;
import com.project.singk.domain.album.repository.GenreRepository;
import com.project.singk.global.crawling.MelonCrawler;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {
	@InjectMocks
	private AlbumService target;
	@Mock
	private MelonCrawler melonCrawler;
	@Mock
	private AlbumRepository albumRepository;
	@Mock
	private GenreRepository genreRepository;
	@Mock
	private AlbumGenreRepository albumGenreRepository;

	@Test
	public void 앨범크롤링_성공() {
		// given
		doReturn(AlbumRequestDto.builder()
			.type("[EP]")
			.genre("장르1, 장르2")
			.releasedAt("2024.04.18")
			.build())
			.when(melonCrawler)
			.getAlbum(anyLong());

		doReturn(Album.builder().build())
			.when(albumRepository)
			.save(any(Album.class));

		doReturn(Optional.of(Genre.builder().build()))
			.when(genreRepository)
			.findByName(anyString());

		doReturn(AlbumGenre.builder().build())
			.when(albumGenreRepository)
			.save(any(AlbumGenre.class));

		// when
		target.crawlingMelonAlbums(12345678L, 12348765L);
	}
}
