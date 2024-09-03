package com.project.singk.global.config;

import java.io.IOException;
import java.net.URI;

import org.apache.hc.core5.http.ParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.properties.SpotifyProperties;

import lombok.RequiredArgsConstructor;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

@Configuration
@RequiredArgsConstructor
public class SpotifyConfig {

	private final SpotifyProperties spotifyProperties;

	@Bean
	public SpotifyApi spotifyApi() {
		final SpotifyApi spotify = SpotifyApi.builder()
			.setClientId(spotifyProperties.getClientId())
			.setClientSecret(spotifyProperties.getClientSecret())
			.setRedirectUri(URI.create(spotifyProperties.getRedirectUri()))
			.build();

		getAccessToken(spotify);

		return spotify;
	}

    public void getAccessToken(SpotifyApi spotify) {
        ClientCredentialsRequest clientCredentialsRequest = spotify.clientCredentials().build();
        try {
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotify.setAccessToken(clientCredentials.getAccessToken());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new ApiException(AppHttpStatus.FAILED_AUTHENTICATION_SPOTIFY);
        }
    }
}
