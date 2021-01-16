package com.galvanize.indus.gmdb.controllers;

import com.galvanize.indus.gmdb.models.Movie;
import com.galvanize.indus.gmdb.services.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MoviesController {

    @Autowired
    private MoviesService moviesService;

    @GetMapping("/gmdb/movies")
    public List<Movie> getMovies() {
        return moviesService.findAll();
    }
}
