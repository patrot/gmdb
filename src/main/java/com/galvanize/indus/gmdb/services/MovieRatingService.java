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

    public MovieRatingService(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }


    public Movie submitMovieRating(String title, String rating, String review){
        Optional<Movie> optionalMovie = moviesRepository.findByTitle(title);
        Movie movie = optionalMovie.get();
        UserRating newUserRating = UserRating.builder().review(review).rating(Integer.valueOf(rating)).build();

        movie.getUserRatings().add(newUserRating);


         int avgRating = movie.getUserRatings().stream().mapToInt(
                 userRating -> userRating.getRating()).sum();
         avgRating = avgRating/movie.getUserRatings().size();
        movie.setRating(String.valueOf(avgRating));
        Movie savedMovie = moviesRepository.save(movie);
        return savedMovie;

    }
}
