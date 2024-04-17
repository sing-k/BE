package com.project.singk.global.crawling;


import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;

import com.project.singk.domain.album.dto.AlbumRequestDto;

class CrawlerTest {

	@Test
	public void 없는ID_예외처리하기() {
		// given
		Crawler crawler = new Crawler();
		List<String> ids = List.of("12345678", "11450069", "11450063");

		// when
		for (String id: ids) {
			try {
				crawler.setUrl(String.format("https://www.melon.com/album/detail.htm?albumId=%s", id));
			} catch (UnhandledAlertException e) {
				e.getAlertText();
			}
		}
		crawler.close();
	}

	@Test
	public void 크롤링성공() {
		// given
		Crawler crawler = new Crawler();

		// when
		crawler.setUrl("https://www.melon.com/album/detail.htm?albumId=11450069");

		WebElement img = crawler.getElement(By.cssSelector("#d_album_org > img"));
		WebElement type = crawler.getElement(By.cssSelector("#conts > div.section_info > div > div.entry > div.info > span"));

		// then
		System.out.println(img.getAttribute("src"));
		System.out.println(type.getText());

		crawler.close();
	}

	@Test
	public void 앨범수록곡크롤링성공() {
		// given
		Crawler crawler = new Crawler();
		crawler.setUrl("https://www.melon.com/album/detail.htm?albumId=11450069");

		// when
		List<WebElement> elements = crawler.getElements(By.className("wrap_song_info"));

		// then
		for (WebElement e : elements) {
			System.out.println(e.findElement(By.className("ellipsis")).getText());
		}

		crawler.close();
	}

	@Test
	public void 멜론크롤링성공() {
		// given
		MelonCrawler crawler = new MelonCrawler(new Crawler());
		Long melonId = 11450069L;
		// when
		AlbumRequestDto album = crawler.getAlbum(melonId);

		// then
		System.out.println(album);
		assertThat(album.getMelonId()).isEqualTo(melonId);
	}
}
