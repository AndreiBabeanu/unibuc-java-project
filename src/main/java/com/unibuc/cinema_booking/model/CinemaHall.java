package com.unibuc.cinema_booking.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CinemaHall {

    private long id;
    private long cinemaId;
    private String Name;
    private long numberSeats;
}
