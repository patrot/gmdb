package com.galvanize.indus.gmdb.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="UserRatings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="user_ratings")
public class UserRating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne(fetch=FetchType.LAZY, optional=false)
//    @JoinColumn(name="movie_id", nullable=false)
//    private Movie movie;

    private int rating;
    private String review;
}
