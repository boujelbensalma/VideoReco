package com.sbo.videorecommendation;

import com.sbo.videorecommendation.controller.VideoController;
import com.sbo.videorecommendation.dto.MovieDTO;
import com.sbo.videorecommendation.dto.SerieDTO;
import com.sbo.videorecommendation.dto.VideoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VideoRecommendationApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private VideoController videoController;

	@BeforeEach
	void setUp() {
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setId("97e343ac-3141-45d1-aff6-68a7465d55ec");
		movieDTO.setTitle("Sample Movie");
		movieDTO.setLabels(List.of("Action", "Adventure"));
		videoController.addVideo(movieDTO);

		SerieDTO serieDTO = new SerieDTO();
		serieDTO.setId("86be99d4-ba36-11eb-8529-0242ac13000");
		serieDTO.setTitle("Game of Thrones");
		serieDTO.setLabels(List.of("Adventure", "Drama", "Fantasy"));
		serieDTO.setNumberOfEpisodes(140);
		videoController.addVideo(serieDTO);
	}

	@Test
	void testAddMovie() throws Exception {
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setId("97e343ac-3141-45d1-aff6-68a7465d55ec");
		movieDTO.setTitle("Knives Out");
		movieDTO.setLabels(List.of("Comedy", "Crime", "Drama", "Mystery", "Thriller"));
		movieDTO.setDirector("Rian Johnson");
		movieDTO.setReleaseDate(LocalDate.of(2019, 11, 27));

		String movieJson = objectMapper.writeValueAsString(movieDTO);

		mockMvc.perform(post("/videos/add")
						.content(movieJson)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value("97e343ac-3141-45d1-aff6-68a7465d55ec"));

		assertNotNull(videoController.getVideoById("97e343ac-3141-45d1-aff6-68a7465d55ec").getBody());
	}

	@Test
	void testAddSeries() throws Exception {
		SerieDTO serieDTO = new SerieDTO();
		serieDTO.setId("86be99d4-ba36-11eb-8529-0242ac13000");
		serieDTO.setTitle("Game of Thrones");
		serieDTO.setLabels(List.of("Adventure", "Drama", "Fantasy"));
		serieDTO.setNumberOfEpisodes(140);

		String serieJson = objectMapper.writeValueAsString(serieDTO);

		mockMvc.perform(post("/videos/add")
						.content(serieJson)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value("86be99d4-ba36-11eb-8529-0242ac13000"));

		assertNotNull(videoController.getVideoById("86be99d4-ba36-11eb-8529-0242ac13000").getBody());
	}

	@Test
	void testGetMovies() throws Exception {
		mockMvc.perform(get("/videos/movies")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}

	@Test
	void testGetSeries() throws Exception {
		mockMvc.perform(get("/videos/series")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}

	@Test
	void testGetDeletedVideoIds() throws Exception {
		mockMvc.perform(get("/videos/deleted-ids")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}

	@Test
	void testDeleteVideo() throws Exception {
		mockMvc.perform(delete("/videos/delete/{videoId}", "97e343ac-3141-45d1-aff6-68a7465d55ec")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testSearchVideosByTitle() throws Exception {
		mockMvc.perform(get("/videos/search/{keyword}", "ame")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}

	@Test
	void testGetVideoById() throws Exception {
		mockMvc.perform(get("/videos/{videoId}", "86be99d4-ba36-11eb-8529-0242ac13000")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("86be99d4-ba36-11eb-8529-0242ac13000"));
	}


}
