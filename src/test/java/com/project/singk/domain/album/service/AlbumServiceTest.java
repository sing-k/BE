package com.project.singk.domain.album.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumGenre;
import com.project.singk.domain.album.domain.Genre;
import com.project.singk.domain.album.dto.AlbumListResponseDto;
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
	public void 앨범랜덤조회_성공() {
		// given
		final Long limit = 2L;

		List<Genre> genres = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			genres.add(Genre.builder().name(String.format("장르 %d", i)).albumGenres(new ArrayList<>()).build());
		}

		List<Album> albums = new ArrayList<>();
		for (int i = 1; i <= 2; i++) {
			albums.add(Album.builder().name(String.format("앨범 %d", i)).albumGenres(new ArrayList<>()).build());
		}

		List<AlbumGenre> albumGenres = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			albumGenres.add(AlbumGenre.builder().build());
		}

		albumGenres.get(0).setUp(albums.get(0), genres.get(0));
		albumGenres.get(1).setUp(albums.get(1), genres.get(1));
		albumGenres.get(2).setUp(albums.get(1), genres.get(2));

		doReturn(albums)
			.when(albumRepository)
			.findRandomAlbums(anyLong());

		doReturn(2L)
			.when(albumRepository)
			.count();

		// when
		final List<AlbumListResponseDto> result = target.getRandomAlbums(limit);

		// then
		assertThat(result.size()).isEqualTo(2);
	}

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
