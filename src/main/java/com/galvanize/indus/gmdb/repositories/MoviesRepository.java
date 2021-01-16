package com.galvanize.indus.gmdb.repositories;

import com.galvanize.indus.gmdb.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<Movie, String> {
}
