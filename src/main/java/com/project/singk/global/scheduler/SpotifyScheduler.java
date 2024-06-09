package com.project.singk.global.scheduler;

import com.project.singk.global.config.SpotifyConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;

@Component
@RequiredArgsConstructor
public class SpotifyScheduler {
    private final SpotifyApi spotifyApi;
    private final SpotifyConfig spotifyConfig;

    @Scheduled(fixedRate = 3600000)
    public void getAccessToken() {
        spotifyConfig.getAccessToken(spotifyApi);
    }
}
