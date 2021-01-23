package com.galvanize.indus.gmdb.unittests.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.galvanize.indus.gmdb.models.Movie;
import com.galvanize.indus.gmdb.models.UserRating;
import com.galvanize.indus.gmdb.services.MovieRatingService;
import com.galvanize.indus.gmdb.services.MoviesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<UserRating> userRatings = new ArrayList<>();
        userRatings.add(UserRating.builder().rating(4).review("Awful movie").build());
        userRatings.add(UserRating.builder().rating(5).review("Worthwhile watching, run do not walk to see it").build());
        avengers = Movie.builder()
                .title("The Avengers")
                .director("Joss Whedon")
                .actors("Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth")
                .releaseYear("2012")
                .description("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.")
                .rating("4")
                .userRatings(userRatings)
                .build();
    }

    @Test
    public void submitMovieRatingTest() throws Exception {

        when(movieRatingService.submitMovieRating("The Avengers", "4", "Worthwhile watching, run do not walk to see it")).thenReturn(avengers);

        ObjectMapper mapper = new ObjectMapper();
        UserRating userRating = UserRating.builder().rating(4).review("Worthwhile watching, run do not walk to see it").build();
        String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userRating);

        mockMvc.perform(post("/gmdb/movies/rating/The Avengers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content(content))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.title").value("The Avengers"))
                .andExpect(jsonPath("$.director").value("Joss Whedon"))
                .andExpect(jsonPath("$.actors").value("Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth"))
                .andExpect(jsonPath("$.releaseYear").value("2012"))
                .andExpect(jsonPath("$.description").value("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity."))
                .andExpect(jsonPath("$.rating").value("4"))
                .andExpect(jsonPath("$.userRatings[0].rating").value(4))
                .andExpect(jsonPath("$.userRatings[0].review").value("Awful movie"))
                .andExpect(jsonPath("$.userRatings[1].rating").value(5))
                .andExpect(jsonPath("$.userRatings[1].review").value("Worthwhile watching, run do not walk to see it"));


        verify(movieRatingService).submitMovieRating("The Avengers", "4", "Worthwhile watching, run do not walk to see it");
    }

    @Test
    public void submitMovieRatingTest_reviewWithoutRating() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode contentObject = mapper.createObjectNode();
        contentObject.put("review", "Worthwhile watching, run do not walk to see it");


        // UserRating userRating = UserRating.builder().rating(4).review("Worthwhile watching, run do not walk to see it").build();
        String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(contentObject);

        mockMvc.perform(post("/gmdb/movies/rating/The Avengers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content(content))
                    .andExpect(status().isNotAcceptable())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.message").value("Star rating is required"));

    }

}
