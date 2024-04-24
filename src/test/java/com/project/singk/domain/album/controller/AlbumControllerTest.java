package com.project.singk.domain.album.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.project.singk.domain.album.dto.AlbumListResponseDto;
import com.project.singk.domain.album.service.AlbumService;
import com.project.singk.global.api.BaseResponse;

@WebMvcTest(AlbumController.class)
@MockBean(JpaMetamodelMappingContext.class)
class AlbumControllerTest {
	@MockBean
	private AlbumService albumService;
	@Autowired
	private MockMvc mockMvc;

	@ParameterizedTest
	@MethodSource("invalidLimitParameter")
	public void 앨범랜덤조회실패_잘못된파라미터(String limit) throws Exception {
		// given
		final String url = "/api/albums/random";

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.multipart(HttpMethod.GET, url)
				.param("limit", limit)
		);

		// then
		resultActions.andDo(print())
			.andExpect(jsonPath("$.statusCode").value(400));
	}
	private static Stream<Arguments> invalidLimitParameter() {
		return Stream.of(
			Arguments.of("-1"),
			Arguments.of("0"),
			Arguments.of("9")
		);
	}

	@Test
	public void 앨범랜덤조회성공() throws Exception {
		// given
		final String url = "/api/albums/random";

		doReturn(List.of(
			AlbumListResponseDto.builder().build()
		))
			.when(albumService)
			.getRandomAlbums(anyLong());

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.request(HttpMethod.GET, url)
				.param("limit", "1")
		);

		// then
		resultActions.andDo(print())
			.andExpect(jsonPath("$.statusCode").value(200));
	}
}
