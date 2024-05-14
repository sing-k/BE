package com.project.singk;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.neovisionaries.i18n.CountryCode;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;

@SpringBootTest
class SpotifyTest {

	@Autowired
	SpotifyApi spotifyApi;
	
	@Test
	public void searchAPI() {
		final SearchAlbumsRequest request = spotifyApi.searchAlbums("Get up")
			.market(CountryCode.KR)
			.build();

		try {
			final Paging<AlbumSimplified> albums = request.execute();
			for (AlbumSimplified album : albums.getItems()) {
				System.out.println(album);
			}
		} catch (IOException | ParseException | SpotifyWebApiException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void albumAPI() {
		final GetAlbumRequest request = spotifyApi.getAlbum("6Knnr9SwfB0kyFoMa4rNQ1")
			.market(CountryCode.KR)
			.build();

		try {
			final Album album = request.execute();
			System.out.println(album);
		} catch (IOException | ParseException | SpotifyWebApiException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void artistAPI() {
		final GetArtistRequest request = spotifyApi.getArtist("6HvZYsbFfjnjFrWF950C9d")
			.build();
		try {
			final Artist artist = request.execute();
			System.out.println(artist);
		} catch (IOException | ParseException | SpotifyWebApiException e) {
			throw new RuntimeException(e);
		}
	}
}
