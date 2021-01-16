package com.galvanize.indus.gmdb.controllers;

import com.galvanize.indus.gmdb.exceptions.GmdbException;
import com.galvanize.indus.gmdb.models.Movie;
import com.galvanize.indus.gmdb.services.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class MoviesController {

    @Autowired
    private MoviesService moviesService;

    @GetMapping("/gmdb/movies")
    public List<Movie> getMovies() {
        return moviesService.findAll();
    }

    @GetMapping("/gmdb/movies/{title}")
    public Movie getMovie(@PathVariable String title) throws GmdbException {
        Optional<Movie> movie = moviesService.findByTitle(title);
        if (movie.isPresent()) {
            return movie.get();
        } else {
            throw new GmdbException();
        }
    }
}
