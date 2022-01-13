package com.unibuc.cinema_booking.mapper;

import com.unibuc.cinema_booking.dto.CinemaRequest;
import com.unibuc.cinema_booking.model.Cinema;
import org.springframework.stereotype.Component;

@Component
public class CinemaMapper {

    public Cinema mapToEntity(CinemaRequest cinemaRequest) {
        return new Cinema(cinemaRequest.getId(), cinemaRequest.getLocationId(), cinemaRequest.getName(), cinemaRequest.getPhoneNumber());
    }

    public CinemaRequest mapToDTO(Cinema cinema){
        return new CinemaRequest(cinema.getId(), cinema.getLocationId(), cinema.getName(), cinema.getPhoneNumber());
    }

}
