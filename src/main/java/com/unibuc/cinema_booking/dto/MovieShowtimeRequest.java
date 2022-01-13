package com.unibuc.cinema_booking.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieShowtimeRequest {

    private long id;
    @NotNull
    private long movieId;
    @NotNull
    private long cinemaHallId;
    @NotBlank
    private String date;
}

