package com.galvanize.indus.gmdb.services;

import com.galvanize.indus.gmdb.models.Movie;
import com.galvanize.indus.gmdb.models.UserRating;
import com.galvanize.indus.gmdb.repositories.MoviesRepository;
import com.galvanize.indus.gmdb.repositories.UserRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieRatingService {

    MoviesRepository moviesRepository;
    UserRatingRepository userRatingRepository;

    public MovieRatingService(MoviesRepository moviesRepository, UserRatingRepository userRatingRepository) {
        this.moviesRepository = moviesRepository;
        this.userRatingRepository = userRatingRepository;
    }


    public Movie submitMovieRating(String title, String rating, String review){
        Optional<Movie> optionalMovie = moviesRepository.findByTitle(title);
        Movie movie = optionalMovie.get();
        UserRating newUserRating = UserRating.builder().review(review).rating(Integer.valueOf(rating)).movie(movie).build();

        UserRating savedUserRating = userRatingRepository.save(newUserRating);

        Optional<Movie> optionalUpdatedMovie = moviesRepository.findByTitle(title);
        Movie updatedMovie = optionalUpdatedMovie.get();



         int avgRating = updatedMovie.getUserRatings().stream().mapToInt(
                 userRating -> userRating.getRating()).sum();
         avgRating = avgRating/updatedMovie.getUserRatings().size();
        updatedMovie.setRating(String.valueOf(avgRating));
        Movie savedMovie = moviesRepository.save(updatedMovie);
        return savedMovie;

    }
}
