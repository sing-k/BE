package com.project.singk.domain.post.controller.port;

import com.project.singk.domain.post.controller.response.FreePostResponse;
import com.project.singk.domain.post.domain.FreePostCreate;
import com.project.singk.global.api.OffsetPageResponse;
import com.project.singk.global.domain.PkResponseDto;

public interface FreePostService {
    PkResponseDto createFreePost(Long memberId, FreePostCreate freePostCreate);
    FreePostResponse getFreePost(Long memberId, Long postId);
    OffsetPageResponse<FreePostResponse> getFreePosts(Long memberId, int offset, int limit, String sort, String filter, String keyword);
    OffsetPageResponse<FreePostResponse> getMyFreePosts(Long memberId, int offset, int limit);
    PkResponseDto updateFreePost(Long memberId, Long postId, FreePostCreate freePostCreate);
    void deleteFreePost(Long memberId, Long postId);
}
