package com.unibuc.cinema_booking.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationRequest {

    @NotNull
    private long userId;
    @NotNull
    private long idSeat;
    @NotNull
    private long movieShowtimeId;

}
