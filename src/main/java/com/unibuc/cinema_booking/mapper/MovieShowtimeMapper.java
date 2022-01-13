package com.unibuc.cinema_booking.mapper;

import com.unibuc.cinema_booking.dto.MovieShowtimeRequest;
import com.unibuc.cinema_booking.model.MovieShowtime;
import org.springframework.stereotype.Component;

@Component
public class MovieShowtimeMapper {

    public MovieShowtime mapToEntity(MovieShowtimeRequest movieShowtimeRequest)
    {
        return new MovieShowtime(movieShowtimeRequest.getId(),movieShowtimeRequest.getMovieId(),movieShowtimeRequest.getCinemaHallId(),movieShowtimeRequest.getDate());
    }

    public MovieShowtimeRequest mapToDTO(MovieShowtime movieShowtime)
    {
        return new MovieShowtimeRequest(movieShowtime.getId(),movieShowtime.getMovieId(),movieShowtime.getCinemaHallId(),movieShowtime.getDate());
    }
}
