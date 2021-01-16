package com.galvanize.indus.gmdb.unittests.controllers;

import com.galvanize.indus.gmdb.models.Movie;
import com.galvanize.indus.gmdb.services.MoviesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class MoviesControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MoviesService mockMoviesService;

    @Test
    public void GetMoviesTest_whenNoMoviesInDatabase() throws Exception {
        List<Movie> movies = new ArrayList<>();
        when(mockMoviesService.findAll()).thenReturn(movies);

        mockMvc.perform(get("/gmdb/movies"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void GetMoviesTest_whenMultipleMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        Movie avengers = Movie.builder().title("The Avengers").build();
        Movie supermanReturns = Movie.builder().title("Superman Returns").build();

        movies.add(avengers);
        movies.add(supermanReturns);
        when(mockMoviesService.findAll()).thenReturn(movies);

        mockMvc.perform(get("/gmdb/movies"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].title").value("The Avengers"))
                .andExpect(jsonPath("$.[1].title").value("Superman Returns"));
    }
}
