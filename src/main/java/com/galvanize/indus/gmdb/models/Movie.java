package com.galvanize.indus.gmdb.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name="Movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="movies")
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String director;
    private String actors;
    private String releaseYear;

    @Lob
    @Column
    private String description;

//    @OneToMany(orphanRemoval = true,
//            cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<UserRating> userRatings;

    private String rating;
}
