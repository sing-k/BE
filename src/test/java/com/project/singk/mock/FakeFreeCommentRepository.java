package com.project.singk.mock;

import com.project.singk.domain.comment.domain.CommentSimplified;
import com.project.singk.domain.comment.domain.FreeComment;
import com.project.singk.domain.comment.service.port.FreeCommentRepository;
import com.project.singk.global.api.ApiException;
import com.project.singk.global.api.AppHttpStatus;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FakeFreeCommentRepository implements FreeCommentRepository {
	private final AtomicLong autoGeneratedId = new AtomicLong(0);
	private final List<FreeComment> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public FreeComment save(FreeComment freeComment) {
        if (freeComment.getId() == null || freeComment.getId() == 0) {
            FreeComment newFreeComment = FreeComment.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .parentId(freeComment.getParentId())
                    .content(freeComment.getContent())
                    .likes(freeComment.getLikes())
                    .member(freeComment.getMember())
                    .post(freeComment.getPost())
                    .createdAt(freeComment.getCreatedAt())
                    .modifiedAt(freeComment.getModifiedAt())
                    .build();
            data.add(newFreeComment);
            return newFreeComment;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), freeComment.getId()));
            data.add(freeComment);
            return freeComment;
        }
    }

    @Override
    public FreeComment getById(Long id) {
        return findById(id).orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_COMMENT));
    }

    @Override
    public Optional<FreeComment> findById(Long id) {
        return data.stream().filter(item -> item.getId().equals(id)).findAny();
    }


    @Override
    public void deleteById(Long postId) {
        data.removeIf(item -> item.getId().equals(postId));
    }

    @Override
    public List<CommentSimplified> findAllByPostId(Long postId) {
        return data.stream()
                .filter(item -> item.getPost().getId().equals(postId))
                .map(this::simplified)
                .toList();
    }

    @Override
    public List<CommentSimplified> findAllByMemberIdAndPostId(Long memberId, Long postId) {
        return data.stream()
                .filter(item -> item.getPost().getId().equals(postId) &&
                        item.getMember().getId().equals(memberId))
                .map(this::simplified)
                .toList();
    }

    private CommentSimplified simplified(FreeComment comment) {
        return CommentSimplified.builder()
                .id(comment.getId())
                .parentId(comment.getParentId())
                .content(comment.getContent())
                .likes(comment.getLikes())
                .member(comment.getMember())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
