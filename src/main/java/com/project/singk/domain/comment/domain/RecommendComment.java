package com.project.singk.domain.comment.domain;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.domain.RecommendPost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecommendComment {

    private Long id;
    private String content;
    private int likeCount;
    private Member member;
    private RecommendComment parent;
    private RecommendPost post;

    @Builder
    public RecommendComment(Long id,String content, int likeCount, Member member,RecommendComment parent,RecommendPost post){
        this.id = id;
        this.content = content;
        this.likeCount = likeCount;
        this.member = member;
        this.parent = parent;
        this.post = post;
    }

    public static RecommendComment byRequest(RecommendCommentCreate req,Member member,RecommendPost post,RecommendComment parent){
        return RecommendComment.builder()
                .content(req.getContent())
                .member(member)
                .post(post)
                .parent(parent)
                .build();
    }
    public void update(RecommendCommentCreate req){
        String content = req.getContent();
        if(!content.isEmpty()){
            this.content = content;
        }
    }
    public void updateLikeCount(int cnt){
        this.likeCount = cnt;
    }
}
