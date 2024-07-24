package com.project.singk.domain.post.infrastructure.entity;

import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RECOMMEND_POSTS")
@Getter
@NoArgsConstructor
public class RecommendPostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @Builder
    public RecommendPostEntity(Long id, MemberEntity member) {
        this.id = id;
        this.member = member;
    }

}
