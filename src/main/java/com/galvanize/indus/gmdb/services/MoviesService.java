package com.galvanize.indus.gmdb.services;

import com.galvanize.indus.gmdb.models.Movie;
import com.galvanize.indus.gmdb.repositories.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MoviesService {

    @Autowired
    private MoviesRepository moviesRepository;

    public List<Movie> findAll() {
        return moviesRepository.findAll();
    }

    public Optional<Movie> findByTitle(String title) {
        return moviesRepository.findByTitle(title);
    }
}
