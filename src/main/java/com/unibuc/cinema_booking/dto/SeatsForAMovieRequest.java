package com.unibuc.cinema_booking.dto;

import javax.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatsForAMovieRequest {

    @NotNull
    private long idMovie;
    @NotNull
    private Boolean available;
}
