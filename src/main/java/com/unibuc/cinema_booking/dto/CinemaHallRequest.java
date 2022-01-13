package com.unibuc.cinema_booking.dto;

import com.unibuc.cinema_booking.validators.OnlyNumbers;
import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CinemaHallRequest {

    private long id;

    @NotNull
    private long cinemaId;
    @NotBlank
    private String Name;

    @NotNull
    private long numberSeats;
}
