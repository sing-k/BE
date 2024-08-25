package com.project.singk.domain.vote.controller.response;

import com.project.singk.domain.vote.domain.VoteType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class VoteResponse {
    private int prosCount;
    private int consCount;
    private boolean isPros;
    private boolean isCons;

    public static VoteResponse from (int pros, int cons, VoteType type) {
        return VoteResponse.builder()
                .prosCount(pros)
                .consCount(cons)
                .isPros(VoteType.PROS.equals(type))
                .isCons(VoteType.CONS.equals(type))
                .build();
    }
}
