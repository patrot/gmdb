package com.galvanize.indus.gmdb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="movie_id", nullable=false)
    @ToString.Exclude
    @JsonIgnore
    private Movie movie;

    private int rating;
    private String review;
}
