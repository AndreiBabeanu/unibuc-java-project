package com.unibuc.cinema_booking.dto;
import com.unibuc.cinema_booking.model.EMovieType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    private long id;

    @NotBlank
    private String name;

    @NotNull
    private int durationMinutes;

    @NotNull
    private Boolean is3D;

    @NotNull
    private EMovieType type;

    @Max(5)
    private int rating;

    private String description;

    private int releaseYear;

}
