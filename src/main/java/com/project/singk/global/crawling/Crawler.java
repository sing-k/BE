package com.project.singk.global.crawling;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
public class Crawler {
	private static final String WEB_DRIVER_NAME = "webdriver.chrome.driver";
	private static final String WEB_DRIVER_PATH = "./chromedriver";
	private WebDriver driver;

	public Crawler () {
		// 크롬 드라이버 설정
		System.setProperty(WEB_DRIVER_NAME, WEB_DRIVER_PATH);

		// 크롬 드라이버 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--disable-popup-blocking");

		driver = new ChromeDriver(options);
	}

	public void setUrl(String url) {
		driver.get(url);
	}

	public List<WebElement> getElements(By by) {
			return driver.findElements(by);
	}

	public WebElement getElement(By by) {
			return driver.findElement(by);
	}

	public void close() {
		driver.close();
	}
}
