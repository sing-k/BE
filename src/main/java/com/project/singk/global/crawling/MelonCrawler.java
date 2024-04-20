package com.project.singk.global.crawling;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.project.singk.domain.album.domain.AlbumType;
import com.project.singk.domain.album.dto.AlbumRequestDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MelonCrawler {
	private final String baseUrl = "https://www.melon.com/album/detail.htm?albumId=%d";
	private final Crawler crawler;

	public AlbumRequestDto getAlbum(Long albumId) {
		try {
			crawler.setUrl(String.format(baseUrl, albumId));

			// 앨범
			WebElement album = crawler.getElement(By.cssSelector("#conts > div.section_info > div > div.entry"));

			String type = album.findElement(By.className("gubun")).getText();
			if (AlbumType.of(type) == null) return null;

			// 수록곡
			List<String> tracks = new ArrayList<>();
			List<WebElement> elements = crawler.getElements(By.className("wrap_song_info"));

			for (WebElement e : elements) {
				tracks.add(e.findElement(By.className("ellipsis")).getText());
			}

			return AlbumRequestDto.builder()
				.melonId(albumId)
				.imageUrl(crawler.getElement(By.cssSelector("#d_album_org > img")).getAttribute("src"))
				.name(album.findElement(By.className("song_name")).getText())
				.type(type)
				.artist(album.findElement(By.className("artist")).getText())
				.genre(album.findElement(By.cssSelector("div.meta > dl > dd:nth-child(4)")).getText())
				.releasedAt(album.findElement(By.cssSelector("div.meta > dl > dd:nth-child(2)")).getText())
				.tracks(tracks)
				.build();

		} catch (UnhandledAlertException e) {
			e.getAlertText();
		}

		return null;
	}
}
