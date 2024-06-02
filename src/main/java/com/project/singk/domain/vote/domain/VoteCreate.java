package com.project.singk.domain.vote.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.project.singk.global.validate.ValidEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VoteCreate {

    @ValidEnum(enumClass = VoteType.class)
	private String type;

	@JsonCreator
    @Builder
	public VoteCreate(@JsonProperty("type") String type) {
		this.type = type;
	}
}
