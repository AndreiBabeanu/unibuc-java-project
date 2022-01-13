package com.unibuc.cinema_booking.mapper;

import com.unibuc.cinema_booking.dto.CinemaHallRequest;
import com.unibuc.cinema_booking.model.Cinema;
import com.unibuc.cinema_booking.model.CinemaHall;
import org.springframework.stereotype.Component;

@Component
public class CinemaHallMapper {

    public CinemaHall mapToEntity(CinemaHallRequest cinemaHallRequest)
    {
        return new CinemaHall(cinemaHallRequest.getId(), cinemaHallRequest.getCinemaId(), cinemaHallRequest.getName(), cinemaHallRequest.getNumberSeats());
    }

    public CinemaHallRequest mapToDTO(CinemaHall cinemaHall)
    {
        return new CinemaHallRequest(cinemaHall.getId(), cinemaHall.getCinemaId(), cinemaHall.getName(), cinemaHall.getNumberSeats());
    }

}
