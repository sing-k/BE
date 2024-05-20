package com.project.singk.domain.album.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neovisionaries.i18n.CountryCode;
import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.domain.AlbumImage;
import com.project.singk.domain.album.domain.AlbumType;
import com.project.singk.domain.album.domain.Artist;
import com.project.singk.domain.album.domain.Track;
import com.project.singk.domain.album.dto.AlbumResponseDto;
import com.project.singk.domain.album.repository.AlbumRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.PageResponse;

import lombok.RequiredArgsConstructor;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {

	private final SpotifyApi spotifyApi;
	private final AlbumRepository albumRepository;

	public AlbumResponseDto.Detail getAlbums(String id) {
		Album album = albumRepository.findById(id).orElse(null);

		// DB에 데이터 있는 경우
		if (album != null) {
			return AlbumResponseDto.Detail.of(album);
		}

		// DB에 데이터 없는 경우
		final GetAlbumRequest getAlbumRequest = spotifyApi.getAlbum(id)
			.market(CountryCode.KR)
			.build();

		try {
			se.michaelthelin.spotify.model_objects.specification.Album spotifyAlbum = getAlbumRequest.execute();

			// TODO : 라인 수 줄이기 VS 파일 수 줄이기
			List<Artist> artists = Arrays.stream(spotifyAlbum.getArtists())
				.map(artist -> Artist.builder()
					.id(artist.getId())
					.name(artist.getName())
					.build())
				.toList();

			List<Track> tracks = Arrays.stream(spotifyAlbum.getTracks().getItems())
				.map(track -> Track.builder()
					.id(track.getId())
					.name(track.getName())
					.trackNumber(track.getTrackNumber())
					.duration(track.getDurationMs())
					.isPlayable(track.getIsPlayable())
					.previewUrl(track.getPreviewUrl())
					.build())
				.toList();

			List<AlbumImage> images = Arrays.stream(spotifyAlbum.getImages())
				.map(image -> AlbumImage.builder()
					.width(image.getWidth())
					.height(image.getHeight())
					.imageUrl(image.getUrl())
					.build())
				.toList();

			album = Album.builder()
				.id(spotifyAlbum.getId())
				.name(spotifyAlbum.getName())
				.type(AlbumType.of(
					spotifyAlbum.getAlbumType().type,
					tracks.size())
				)
				.releasedAt(LocalDate.parse(
					spotifyAlbum.getReleaseDate(),
					DateTimeFormatter.ofPattern("yyyy-MM-dd")
				).atStartOfDay())
				.trackCount(tracks.size())
				.build();

			// 양방향 연관관계 설정
			album.addTracks(tracks);
			album.addAlbumImages(images);
			album.addArtists(artists);


			return AlbumResponseDto.Detail.of(albumRepository.save(album));
		} catch (IOException | ParseException | SpotifyWebApiException e) {
			throw new ApiException(AppHttpStatus.FAILED_REQUEST_SPOTIFY);
		}
	}

	@Transactional(readOnly = true)
	public PageResponse<AlbumResponseDto.Simple> searchAlbums(String query, int offset, int limit) {
		// 앨범 목록 API 요청 준비
		final SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums(query)
			.limit(limit)
			.offset(offset)
			.market(CountryCode.KR)
			.build();

		try {
			// 요청
			final Paging<AlbumSimplified> spotifyAlbums = searchAlbumsRequest.execute();

			// 데이터 저장없이 바로 전달
			return PageResponse.of(
				spotifyAlbums.getOffset(),
				spotifyAlbums.getLimit(),
				spotifyAlbums.getTotal(),
				Arrays.stream(spotifyAlbums.getItems())
					.map(AlbumResponseDto.Simple::of)
					.toList()
			);
		} catch (IOException | ParseException | SpotifyWebApiException e) {
			throw new ApiException(AppHttpStatus.FAILED_REQUEST_SPOTIFY);
		}
	}
}
