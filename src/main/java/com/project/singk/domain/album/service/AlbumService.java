package com.project.singk.domain.album.service;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neovisionaries.i18n.CountryCode;
import com.project.singk.domain.album.dto.AlbumResponseDto;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.PageResponse;

import lombok.RequiredArgsConstructor;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlbumService {

	private final SpotifyApi spotifyApi;

	public PageResponse.Spotify<AlbumResponseDto.Simple> searchAlbums(String query, int offset, int limit) {
		// 앨범 목록 API 요청 준비
		final SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums(query)
			.limit(limit)
			.offset(offset)
			.market(CountryCode.KR)
			.build();

		try {
			// 요청
			final Paging<AlbumSimplified> spotifyAlbums = searchAlbumsRequest.execute();

			// 데이터 전달없이 바로 전달
			return PageResponse.Spotify.of(
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
