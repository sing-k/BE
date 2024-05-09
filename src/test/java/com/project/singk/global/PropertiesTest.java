package com.project.singk.global;


import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.singk.global.properties.MailProperties;

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
