package com.unibuc.cinema_booking.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequest {

    private long id;
    @NotNull
    private long cinemaHallId;
    @NotNull
    private int numberSeat;
    @NotNull
    private int rowSeat;
    @NotNull
    private int columnSeat;
    @NotNull
    private Boolean isReserved;

}
