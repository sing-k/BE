package com.project.singk.domain.album.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.UnhandledAlertException;
import org.springframework.stereotype.Service;

import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumGenre;
import com.project.singk.domain.album.domain.Genre;
import com.project.singk.domain.album.dto.AlbumListResponseDto;
import com.project.singk.domain.album.dto.AlbumRequestDto;
import com.project.singk.domain.album.repository.AlbumGenreRepository;
import com.project.singk.domain.album.repository.AlbumRepository;
import com.project.singk.domain.album.repository.GenreRepository;
import com.project.singk.global.crawling.Crawler;
import com.project.singk.global.crawling.MelonCrawler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {

	private final AlbumRepository albumRepository;
	private final GenreRepository genreRepository;
	private final AlbumGenreRepository albumGenreRepository;

	public List<AlbumListResponseDto> getRandomAlbums(Long limit) {
		List<Album> albums;

		if (albumRepository.count() < limit) {
			albums = albumRepository.findAll();
		} else {
			albums = albumRepository.findRandomAlbums(limit);
		}

		return albums.stream().map(album -> {
			List<String> genres = album.getAlbumGenres().stream().map(albumGenre ->
				albumGenre.getGenre().getName()).toList();
			return AlbumListResponseDto.of(album, genres);
		}).toList();
	}
	public void crawlingMelonAlbums (Long startId, Long endId) {
		MelonCrawler crawler = new MelonCrawler(new Crawler());
		List<AlbumRequestDto> albums = new ArrayList<>();

		for (Long id = startId; id <= endId; id++) {
			try{
				AlbumRequestDto album = crawler.getAlbum(id);
				if (album != null) albums.add(album);
			} catch (UnhandledAlertException e) {
				e.getAlertText();
			}
		}

		for (AlbumRequestDto a : albums) {
			Album album = albumRepository.findByName(a.getName()).orElse(null);
			if (album == null) {
				album = albumRepository.save(a.toAlbumEntity());
			}

			// TODO : save Track with cascade
			List<String> genres = Arrays.stream(a.getGenre().split(",")).map(String::trim).toList();
			for (String g : genres) {
				// 장르 조회 후 없으면 저장
				Genre genre = genreRepository.findByName(g).orElse(null);
				if (genre == null) {
					genre = genreRepository.save(Genre.builder().name(g).build());
				}

				albumGenreRepository.save(AlbumGenre.builder()
					.album(album)
					.genre(genre)
					.build());
			}
		}
	}
	
}
