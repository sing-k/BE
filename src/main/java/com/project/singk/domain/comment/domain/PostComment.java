package com.project.singk.domain.comment.domain;

import com.project.singk.domain.member.domain.Member;
import com.project.singk.domain.post.domain.Post;
import com.project.singk.domain.post.domain.RecommendPost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostComment {
    private Long id;
    private String content;
    private int likeCount;
    private Member member;
    private PostComment parent;
    private Post post;

    @Builder
    public PostComment(Long id,String content, int likeCount, Member member,PostComment parent,Post post){
        this.id = id;
        this.content = content;
        this.likeCount = likeCount;
        this.member = member;
        this.parent = parent;
        this.post = post;
    }

    public static PostComment byRequest(PostCommentCreate req,Member member,Post post,PostComment parent){
        return PostComment.builder()
                .content(req.getContent())
                .member(member)
                .post(post)
                .parent(parent)
                .build();
    }
    public void update(PostCommentCreate req){
        String content = req.getContent();
        if(!content.isEmpty()){
            this.content = content;
        }
    }
}
