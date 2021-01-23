package com.galvanize.indus.gmdb.integrationtests;

import com.galvanize.indus.gmdb.models.Movie;
import com.galvanize.indus.gmdb.services.MoviesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HttpGetMovies {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getMovies() throws Exception {

        mockMvc.perform(get("/gmdb/movies"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].title").value("The Avengers"))
                .andExpect(jsonPath("$.[1].title").value("Superman Returns"));
    }

    @Test
    public void getMovieByTitle() throws Exception {
        mockMvc.perform(get("/gmdb/movies/The Avengers"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.title").value("The Avengers"));
    }

    @Test
    public void getMovieByTitleTest_whenMovieDoesNotExist() throws Exception {

        mockMvc.perform(get("/gmdb/movies/Fantastic Four"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.message").value("Movie does not exist"));
    }
}
