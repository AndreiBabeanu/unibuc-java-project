package com.unibuc.cinema_booking.util;

import com.unibuc.cinema_booking.model.Cinema;

public class CinemaUtil {

    public static Cinema anCinema(Long id) {
        return Cinema.builder()
                .id(id)
                .locationId(2)
                .name("Cinema Trivale")
                .phoneNumber("0724-449-333")
                .build();
    }
}
