package com.unibuc.cinema_booking.model;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private long id;
    private String name;
    private int durationMinutes;
    private Boolean is3D;
    private EMovieType type;
    private int rating;
    private String description;
    private int releaseYear;
}
