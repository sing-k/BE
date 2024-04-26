package com.project.singk.global;


import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.singk.domain.album.dto.AlbumRequestDto;
import com.project.singk.global.config.properties.MailProperties;
import com.project.singk.global.crawling.Crawler;
import com.project.singk.global.crawling.MelonCrawler;

@SpringBootTest
class PropertiesTest {

	@Autowired
	private MailProperties mailProperties;

	@Test
	public void 메일프로퍼티확인() {
		// given

		// when

		// then
		assertThat(mailProperties.getNaver().getUsername()).isEqualTo("singk-auth");
	}
}
