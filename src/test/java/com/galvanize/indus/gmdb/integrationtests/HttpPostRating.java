package com.galvanize.indus.gmdb.integrationtests;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HttpPostRating {


    @Autowired
    MockMvc mockMvc;

    @Test
    public void submitMovieRatingTest_firstRating() throws Exception {

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
                .andExpect(jsonPath("$.userRatings[0]").value(4));
                //.andExpect(jsonPath("$.userRatings[1]").value(5));
    }

    @Test
    public void submitMovieRatingTest_twoRatings() throws Exception {

        mockMvc.perform(post("/gmdb/movies/rating/The Avengers/3"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.title").value("The Avengers"))
                .andExpect(jsonPath("$.director").value("Joss Whedon"))
                .andExpect(jsonPath("$.actors").value("Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth"))
                .andExpect(jsonPath("$.release").value("2012"))
                .andExpect(jsonPath("$.description").value("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity."))
                .andExpect(jsonPath("$.rating").value("3"))
                .andExpect(jsonPath("$.userRatings[0]").value(3));

        mockMvc.perform(post("/gmdb/movies/rating/The Avengers/5"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.title").value("The Avengers"))
                .andExpect(jsonPath("$.director").value("Joss Whedon"))
                .andExpect(jsonPath("$.actors").value("Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth"))
                .andExpect(jsonPath("$.release").value("2012"))
                .andExpect(jsonPath("$.description").value("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity."))
                .andExpect(jsonPath("$.rating").value("4"))
                .andExpect(jsonPath("$.userRatings[0]").value(3))
                .andExpect(jsonPath("$.userRatings[1]").value(5));
    }
}
