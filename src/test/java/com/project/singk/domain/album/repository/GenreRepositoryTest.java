package com.project.singk.domain.album.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.project.singk.domain.album.domain.Genre;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GenreRepositoryTest {
	@Autowired
	private GenreRepository genreRepository;

	@Test
	public void 장르이름으로_조회성공() {
		// given
		List<Genre> genres = genreRepository.saveAll(List.of(
			Genre.builder().name("장르1").build(),
			Genre.builder().name("장르2").build(),
			Genre.builder().name("장르3").build()
		));
		String findName = "장르1";
		// when
		Genre result = genreRepository.findByName(findName).orElse(null);
		// then
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo(findName);
	}

	@Test
	public void 장르이름으로_조회실패() {
		// given
		List<Genre> genres = genreRepository.saveAll(List.of(
			Genre.builder().name("장르1").build(),
			Genre.builder().name("장르2").build(),
			Genre.builder().name("장르3").build()
		));
		String findName = "장르4";
		// when
		Genre result = genreRepository.findByName(findName).orElse(null);
		// then
		assertThat(result).isNull();
	}
}
