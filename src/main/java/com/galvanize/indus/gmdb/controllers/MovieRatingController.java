package com.galvanize.indus.gmdb.controllers;

import com.galvanize.indus.gmdb.models.Movie;
import com.galvanize.indus.gmdb.services.MovieRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieRatingController {
    @Autowired
    MovieRatingService movieRatingService;

    @PostMapping("/gmdb/movies/rating/{title}/{rating}")
    @ResponseStatus(HttpStatus.CREATED)
    public Movie submitMovieRating(@PathVariable String title, @PathVariable String rating){
        return movieRatingService.submitMovieRating(title, rating);
    }
}
