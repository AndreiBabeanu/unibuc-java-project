package com.unibuc.cinema_booking.service;

import com.unibuc.cinema_booking.dto.MovieShowtimeRequest;
import com.unibuc.cinema_booking.exception.EntityNotFoundException;
import com.unibuc.cinema_booking.mapper.MovieShowtimeMapper;
import com.unibuc.cinema_booking.model.MovieShowtime;
import com.unibuc.cinema_booking.repository.MovieShowtimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MoviesShowtimeService {

    private MovieShowtimeRepository movieShowtimeRepository;
    private MovieShowtimeMapper movieShowtimeMapper;

    public MoviesShowtimeService(MovieShowtimeRepository movieShowtimeRepository, MovieShowtimeMapper movieShowtimeMapper) {
        this.movieShowtimeRepository = movieShowtimeRepository;
        this.movieShowtimeMapper = movieShowtimeMapper;
    }

    public MovieShowtimeRequest create(MovieShowtimeRequest locationRequest)
    {
        //map the request to entity
        MovieShowtime movie = movieShowtimeMapper.mapToEntity(locationRequest);

        //save the entity to database
        MovieShowtime savedMovie = movieShowtimeRepository.create(movie);

        //map the entity back to request
        MovieShowtimeRequest movieShowtimeSaved = movieShowtimeMapper.mapToDTO(savedMovie);

        return movieShowtimeSaved;

    }

    public List<MovieShowtimeRequest> getAll() {
        return movieShowtimeRepository.getAll()
                .stream()
                .map(location -> movieShowtimeMapper.mapToDTO(location))
                .collect(Collectors.toList());
    }

    public boolean delete(Long id) {
        Optional<MovieShowtime> movieShowtimeOptional = movieShowtimeRepository.getAll()
                .stream()
                .filter(movieShowtime -> movieShowtime.getId() == id)
                .findAny();
        if (movieShowtimeOptional.isPresent()) {
            movieShowtimeRepository.delete(id);
            return true;
        }
        return false;
    }

    public MovieShowtimeRequest getOne(Long id) {

        Optional<MovieShowtime> movieShowtime = movieShowtimeRepository.getOne(id);
        if(movieShowtime.isPresent()) {
            return this.movieShowtimeMapper.mapToDTO(movieShowtime.get());
        } else {
            throw new EntityNotFoundException("Movie Showtime",id);
        }

    }
}
