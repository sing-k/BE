package com.project.singk.global.crawling;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;


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
				System.out.println(id);
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
}
