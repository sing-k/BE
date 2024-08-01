package com.project.singk.domain.member.controller.response;

import com.project.singk.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MemberSimpleResponse {
    private Long id;
    private String imageUrl;
    private String nickname;

    public static MemberSimpleResponse from(Member member, String imageUrl) {
        return MemberSimpleResponse.builder()
                .id(member.getId())
                .imageUrl(member.getImageUrl())
                .nickname(member.getNickname())
                .build();
    }
}
