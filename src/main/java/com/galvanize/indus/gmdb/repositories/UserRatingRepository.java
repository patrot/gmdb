package com.galvanize.indus.gmdb.repositories;

import com.galvanize.indus.gmdb.models.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRatingRepository extends JpaRepository<UserRating, Long> {
}
