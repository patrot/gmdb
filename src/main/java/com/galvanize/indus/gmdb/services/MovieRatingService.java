package com.galvanize.indus.gmdb.services;

import com.galvanize.indus.gmdb.models.Movie;
import com.galvanize.indus.gmdb.repositories.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieRatingService {

    MoviesRepository moviesRepository;

    public MovieRatingService(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }


    public Movie submitMovieRating(String title, String rating){
        Optional<Movie> movie = moviesRepository.findByTitle(title);
        movie.get().getUserRatings().add(Integer.valueOf(rating));
        Movie savedMovie = moviesRepository.save(movie.get());
        return savedMovie;

    }
}
