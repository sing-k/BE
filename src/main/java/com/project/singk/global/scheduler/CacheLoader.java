package com.project.singk.global.scheduler;

import com.project.singk.domain.album.controller.response.AlbumListResponse;
import com.project.singk.domain.album.domain.Album;
import com.project.singk.domain.album.service.port.AlbumRepository;
import com.project.singk.global.api.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CacheLoader {

    private final AlbumRepository albumRepository;

    // @Scheduled(initialDelay = 5000, fixedRate = 30000) // 5분
    @Transactional(readOnly = true)
    public void cachingAlbumsByModifiedAt() {
        // 필요한 매개변수로 캐시를 갱신
        updateCacheAlbumsByModifiedAt(null, null, 5);
    }

    @CachePut(value = "albums_modified_at", key = "'albums_modified_at_'+ #cursorId + '_' + #cursorDate + '_' + #limit")
    public PageResponse<AlbumListResponse> updateCacheAlbumsByModifiedAt(String cursorId, String cursorDate, int limit) {
        Page<Album> albums = albumRepository.findAllByModifiedAt(cursorId, cursorDate, limit);

        return PageResponse.of(
                (int) albums.getPageable().getOffset(),
                limit,
                (int) albums.getTotalElements(),
                albums.stream()
                        .map(AlbumListResponse::from)
                        .toList()
        );
    }
}
