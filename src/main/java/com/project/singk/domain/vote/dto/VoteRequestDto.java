package com.project.singk.domain.vote.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.singk.domain.vote.domain.VoteType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteRequestDto {

	private String type;

	@JsonCreator
	public VoteRequestDto (@JsonProperty("type") String type) {
		this.type = type;
	}

	public VoteType toEnum() {
		return VoteType.valueOf(type);
	}
}
