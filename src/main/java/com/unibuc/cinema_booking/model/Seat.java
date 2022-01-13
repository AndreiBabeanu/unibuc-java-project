package com.unibuc.cinema_booking.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    private long id;
    private long cinemaHallId;
    private int numberSeat;
    private int rowSeat;
    private int columnSeat;
    private Boolean isReserved;
}
