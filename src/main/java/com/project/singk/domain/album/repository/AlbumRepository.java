package com.project.singk.domain.album.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.singk.domain.album.domain.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
	Optional<Album> findByName(String name);

	// MySQL Native Query
	@Query(value = "SELECT * FROM ALBUMS order by RAND() limit ?1",nativeQuery = true)
	List<Album> findRandomAlbums(@Param("limit") Long limit);
}
