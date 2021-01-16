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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class MovieRatingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MoviesService mockMoviesService;

    @MockBean
    MovieRatingService movieRatingService;

    private Movie avengers;

    @BeforeEach
    void setUp() {
        List<Integer> userRatings = new ArrayList<Integer>();
        userRatings.add(4);
        userRatings.add(5);
        avengers = Movie.builder()
                .title("The Avengers")
                .director("Joss Whedon")
                .actors("Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth")
                .release("2012")
                .description("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.")
                .rating("4")
                .userRatings(userRatings)
                .build();
    }

    @Test
    public void submitMovieRatingTest() throws Exception {

        when(movieRatingService.submitMovieRating("The Avengers", "4")).thenReturn(avengers);

        mockMvc.perform(post("/gmdb/movies/rating/The Avengers/4"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.title").value("The Avengers"))
                .andExpect(jsonPath("$.director").value("Joss Whedon"))
                .andExpect(jsonPath("$.actors").value("Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth"))
                .andExpect(jsonPath("$.release").value("2012"))
                .andExpect(jsonPath("$.description").value("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity."))
                .andExpect(jsonPath("$.rating").value("4"))
                .andExpect(jsonPath("$.userRatings[0]").value(4))
                .andExpect(jsonPath("$.userRatings[1]").value(5));


        verify(movieRatingService).submitMovieRating("The Avengers", "4");
    }

}
