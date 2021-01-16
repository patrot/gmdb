package com.galvanize.indus.gmdb.unittests.services;

import com.galvanize.indus.gmdb.models.Movie;
import com.galvanize.indus.gmdb.repositories.MoviesRepository;
import com.galvanize.indus.gmdb.services.MovieRatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MovieRatingServiceTests {

    private Movie expectedMovie;
    private MovieRatingService movieRatingService;

    private MoviesRepository mockMoviesRepository;

    @BeforeEach
    void setup(){
        mockMoviesRepository = mock(MoviesRepository.class);
        movieRatingService = new MovieRatingService(mockMoviesRepository);
        List<Integer> userRatings = new ArrayList<Integer>();
        userRatings.add(5);
        expectedMovie = Movie.builder()
                .title("The Avengers")
                .director("Joss Whedon")
                .actors("Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth")
                .release("2012")
                .description("Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.")
                .userRatings(userRatings)
                .build();
    }

    @Test
    public void submitMovieRatingTest(){
        when(mockMoviesRepository.findByTitle("The Avengers")).thenReturn(java.util.Optional.ofNullable(expectedMovie));

        when(mockMoviesRepository.save(expectedMovie)).thenReturn(expectedMovie);
        Movie movieResult = movieRatingService.submitMovieRating("The Avengers", "4");
        assertEquals(expectedMovie.getTitle(), movieResult.getTitle());
        assertEquals(2, movieResult.getUserRatings().size());
        assertEquals(4, movieResult.getUserRatings().get(movieResult.getUserRatings().size()-1));

        verify(mockMoviesRepository).findByTitle("The Avengers");
        verify(mockMoviesRepository).save(expectedMovie);
    }
}
