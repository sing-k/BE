package com.project.singk.domain.album.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.project.singk.domain.album.domain.Album;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlbumRepositoryTest {

	@Autowired
	private AlbumRepository albumRepository;

	@Test
	public void 앨범목록랜덤조회_성공() {
		// given
		List<Album> albums = new ArrayList<>();
		for (long i = 1L; i <= 10L; i++) {
			albums.add(Album.builder().melonId(i).build());
		}
		albumRepository.saveAll(albums);

		// when
		List<Album> result = albumRepository.findRandomAlbums(5L);

		// then
		assertThat(result.size()).isEqualTo(5);
		System.out.println(result);
	}
}
