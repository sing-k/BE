package com.project.singk.domain.album.infrastructure;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Repository;

import com.neovisionaries.i18n.CountryCode;
import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.infrastructure.spotify.AlbumEntity;
import com.project.singk.domain.album.infrastructure.spotify.AlbumSimplifiedEntity;
import com.project.singk.domain.album.service.port.SpotifyRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.Page;

import lombok.RequiredArgsConstructor;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;

@Repository
@RequiredArgsConstructor
public class SpotifyRepositoryImpl implements SpotifyRepository {

	private final SpotifyApi spotifyApi;

	@Override
	public AlbumEntity getAlbumById(String id) {
		final GetAlbumRequest getAlbumRequest = spotifyApi.getAlbum(id)
			.market(CountryCode.KR)
			.build();

		try {
			return AlbumEntity.from(getAlbumRequest.execute());
		} catch (IOException | ParseException | SpotifyWebApiException e) {
			throw new ApiException(AppHttpStatus.FAILED_REQUEST_SPOTIFY);
		}
	}

	@Override
	public Page<AlbumSimplifiedEntity> searchAlbums(String query, int offset, int limit) {
		final SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums(query)
			.limit(limit)
			.offset(offset)
			.market(CountryCode.KR)
			.build();

		try {
			Paging<AlbumSimplified> albums = searchAlbumsRequest.execute();
			return Page.of(
				offset,
				limit,
				albums.getTotal(),
				Arrays.stream(albums.getItems())
					.map(AlbumSimplifiedEntity::from)
					.toList()
			);
		} catch (IOException | ParseException | SpotifyWebApiException e) {
			throw new ApiException(AppHttpStatus.FAILED_REQUEST_SPOTIFY);
		}
	}
}
