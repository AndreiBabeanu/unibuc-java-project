package com.unibuc.cinema_booking.util;

import com.unibuc.cinema_booking.dto.CinemaRequest;

public class CinemaDtoUtil {

    public static CinemaRequest aCinemaDto(Long id) {
        return CinemaRequest
                .builder()
                .id(id)
                .locationId(2)
                .name("Cinema Trivale")
                .phoneNumber("0724-449-333")
                .build();
    }

}
