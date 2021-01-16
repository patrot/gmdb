package com.galvanize.indus.gmdb.unittests.controllers;

import com.galvanize.indus.gmdb.models.Movie;
import com.galvanize.indus.gmdb.services.MovieRatingService;
import com.galvanize.indus.gmdb.services.MoviesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
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

    @MockBean
    MovieRatingService movieRatingService;

    private List<Movie> movies;
    private Movie avengers;
    private Movie supermanReturns;

    @BeforeEach
    void setUp() {
        movies = new ArrayList<>();

        avengers = Movie.builder()
                .title("The Avengers")
                .director("Joss Whedon")
                .actors("Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth")
                .release("2012")
                .description("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.")
                .build();

        supermanReturns = Movie.builder().title("Superman Returns").build();
    }

    @Test
    public void getMoviesTest_whenNoMoviesInDatabase() throws Exception {
        when(mockMoviesService.findAll()).thenReturn(movies);

        mockMvc.perform(get("/gmdb/movies"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.length()").value(0));

        verify(mockMoviesService).findAll();
    }

    @Test
    public void getMoviesTest_whenMultipleMovies() throws Exception {

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

        verify(mockMoviesService).findAll();
    }

    @Test
    public void getMoviesTest_whenSingleMovie() throws Exception {

        movies.add(avengers);
        when(mockMoviesService.findAll()).thenReturn(movies);

        mockMvc.perform(get("/gmdb/movies"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].title").value("The Avengers"))
                .andExpect(jsonPath("$.[0].director").value("Joss Whedon"))
                .andExpect(jsonPath("$.[0].actors").value("Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth"))
                .andExpect(jsonPath("$.[0].release").value("2012"))
                .andExpect(jsonPath("$.[0].description").value("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity."))
                .andExpect(jsonPath("$.[0].rating").isEmpty());

        verify(mockMoviesService).findAll();
    }

    @Test
    public void getMovieByTitleTest_whenMovieExists() throws Exception {

        Optional<Movie> optionalMovie = Optional.of(avengers);
        when(mockMoviesService.findByTitle("The Avengers")).thenReturn(optionalMovie);
        mockMvc.perform(get("/gmdb/movies/The Avengers"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.title").value("The Avengers"));

        verify(mockMoviesService).findByTitle("The Avengers");
    }

    @Test
    public void getMovieByTitleTest_whenMovieDoesNotExist() throws Exception {

        Optional<Movie> optionalMovie = Optional.ofNullable(null);
        when(mockMoviesService.findByTitle("Fantastic Four")).thenReturn(optionalMovie);

        mockMvc.perform(get("/gmdb/movies/Fantastic Four"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.message").value("Movie does not exist"));

        verify(mockMoviesService).findByTitle("Fantastic Four");
    }
}
