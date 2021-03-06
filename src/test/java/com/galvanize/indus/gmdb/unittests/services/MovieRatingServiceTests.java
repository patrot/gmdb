package com.galvanize.indus.gmdb.unittests.services;

import com.galvanize.indus.gmdb.models.Movie;
import com.galvanize.indus.gmdb.models.UserRating;
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
        List<UserRating> userRatings = new ArrayList<>();
        userRatings.add(UserRating.builder().rating(5).build());
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
        Movie movieResult = movieRatingService.submitMovieRating("The Avengers", "4", "Poor Movie");
        assertEquals(expectedMovie.getTitle(), movieResult.getTitle());
        assertEquals(2, movieResult.getUserRatings().size());
        assertEquals(4, movieResult.getUserRatings().get(movieResult.getUserRatings().size()-1).getRating());
        assertEquals("Poor Movie", movieResult.getUserRatings().get(movieResult.getUserRatings().size()-1).getReview());

        verify(mockMoviesRepository).findByTitle("The Avengers");
        verify(mockMoviesRepository).save(expectedMovie);
    }

    @Test
    public void averageMovieRatingTest(){
        when(mockMoviesRepository.findByTitle("The Avengers")).thenReturn(java.util.Optional.ofNullable(expectedMovie));

        when(mockMoviesRepository.save(expectedMovie)).thenReturn(expectedMovie);
        Movie movieResult = movieRatingService.submitMovieRating("The Avengers", "3", "Good movie");
        assertEquals(expectedMovie.getTitle(), movieResult.getTitle());
        assertEquals(2, movieResult.getUserRatings().size());
        assertEquals(3, movieResult.getUserRatings().get(movieResult.getUserRatings().size()-1).getRating());
        assertEquals("Good movie", movieResult.getUserRatings().get(movieResult.getUserRatings().size()-1).getReview());
        assertEquals("4", movieResult.getRating());

        verify(mockMoviesRepository).findByTitle("The Avengers");
        verify(mockMoviesRepository).save(expectedMovie);

    }
}
