package com.project.singk.domain.post.service;

import com.project.singk.domain.common.service.port.S3Repository;
import com.project.singk.domain.like.service.port.FreePostLikeRepository;
import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.member.service.port.MemberRepository;
import com.project.singk.domain.post.controller.port.FreePostService;
import com.project.singk.domain.post.controller.response.FreePostListResponse;
import com.project.singk.domain.post.controller.response.FreePostResponse;
import com.project.singk.domain.post.controller.response.RecommendPostListResponse;
import com.project.singk.domain.post.domain.FreePostCreate;
import com.project.singk.domain.post.domain.FreePost;
import com.project.singk.domain.post.domain.RecommendPost;
import com.project.singk.domain.post.service.port.FreePostRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;
import com.project.singk.global.api.PageResponse;
import com.project.singk.global.domain.PkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreePostServiceImpl implements FreePostService {
    private final FreePostRepository freePostRepository;
    private final FreePostLikeRepository freePostLikeRepository;
    private final MemberRepository memberRepository;
    private final S3Repository s3Repository;

    @Override
    public PkResponseDto createFreePost(Long memberId, FreePostCreate freePostCreate) {
        Member member = memberRepository.getById(memberId);

        FreePost freePost = FreePost.from(freePostCreate, member);
        freePost = freePostRepository.save(freePost);

        return PkResponseDto.of(freePost.getId());
    }

    @Override
    public FreePostResponse getFreePost(Long memberId, Long postId){
        FreePost freePost = freePostRepository.getById(postId);
        return FreePostResponse.from(
                freePost,
                freePostLikeRepository.existsByMemberIdAndPostId(memberId, postId),
                s3Repository.getPreSignedGetUrl(freePost.getMember().getImageUrl())
        );
    }

    @Override
    public PageResponse<FreePostListResponse> getFreePosts(Long memberId, int offset, int limit, String sort, String filter, String keyword) {
        Page<FreePost> posts = freePostRepository.findAll(offset, limit, sort, filter, keyword);
        return PageResponse.of(
                offset,
                limit,
                (int) posts.getTotalElements(),
                posts.stream()
                        .map(post -> FreePostListResponse.from(
                                post,
                                freePostLikeRepository.existsByMemberIdAndPostId(memberId, post.getId()),
                                s3Repository.getPreSignedGetUrl(post.getMember().getImageUrl())
                        ))
                        .toList()
        );
    }

    @Override
    public PageResponse<FreePostListResponse> getMyFreePosts(Long memberId, int offset, int limit, String sort, String filter, String keyword) {
        Page<FreePost> posts = freePostRepository.findAllByMemberId(memberId, offset, limit, sort, filter, keyword);
        return PageResponse.of(
                offset,
                limit,
                (int) posts.getTotalElements(),
                posts.stream()
                        .map(post -> FreePostListResponse.from(
                                post,
                                freePostLikeRepository.existsByMemberIdAndPostId(memberId, post.getId()),
                                s3Repository.getPreSignedGetUrl(post.getMember().getImageUrl())
                        ))
                        .toList()
        );
    }


    @Override
    public PkResponseDto updateFreePost(Long memberId, Long postId, FreePostCreate freePostCreate){
        FreePost freePost = freePostRepository.getById(postId);

        if (!freePost.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.FORBIDDEN_POST);
        }

        freePost = freePost.update(freePostCreate);
        freePost = freePostRepository.save(freePost);
        return PkResponseDto.of(freePost.getId());
    }

    @Override
    public void deleteFreePost(Long memberId, Long postId){
        FreePost freePost = freePostRepository.getById(postId);

        if (!freePost.getMember().getId().equals(memberId)) {
            throw new ApiException(AppHttpStatus.FORBIDDEN_POST);
        }

        freePostRepository.deleteById(postId);
    }
}
