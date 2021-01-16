package com.galvanize.indus.gmdb.controllers;

import com.galvanize.indus.gmdb.exceptions.GmdbMissingRatingException;
import com.galvanize.indus.gmdb.models.Movie;
import com.galvanize.indus.gmdb.models.UserRating;
import com.galvanize.indus.gmdb.services.MovieRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieRatingController {
    @Autowired
    MovieRatingService movieRatingService;

    @PostMapping("/gmdb/movies/rating/{title}")
    @ResponseStatus(HttpStatus.CREATED)
    public Movie submitMovieRating(@PathVariable String title, @RequestBody UserRating body) throws GmdbMissingRatingException {

        if (body.getRating() == 0) {
            throw new GmdbMissingRatingException();
        }

        return movieRatingService.submitMovieRating(title, Integer.toString(body.getRating()), body.getReview());
    }
}
