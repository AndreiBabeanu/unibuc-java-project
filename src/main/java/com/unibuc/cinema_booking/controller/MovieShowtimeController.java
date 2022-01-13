package com.unibuc.cinema_booking.controller;

import com.unibuc.cinema_booking.dto.LocationRequest;
import com.unibuc.cinema_booking.dto.MovieShowtimeRequest;
import com.unibuc.cinema_booking.service.MoviesShowtimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/movieShowtimes")
public class MovieShowtimeController {

    private MoviesShowtimeService moviesShowtimeService;

    public MovieShowtimeController(MoviesShowtimeService moviesShowtimeService) {
        this.moviesShowtimeService = moviesShowtimeService;
    }

    @PostMapping
    public ResponseEntity<MovieShowtimeRequest> create(@RequestBody @Valid MovieShowtimeRequest movieShowtimeRequest) {
        MovieShowtimeRequest savedMovieShowtimeDTO = moviesShowtimeService.create(movieShowtimeRequest);
        return new ResponseEntity<>(savedMovieShowtimeDTO, null == savedMovieShowtimeDTO ? HttpStatus.EXPECTATION_FAILED : HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MovieShowtimeRequest> getAll() {
        return moviesShowtimeService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieShowtimeRequest> getOne(@PathVariable("id") Long id) {
        MovieShowtimeRequest movieShowtimeDTO = moviesShowtimeService.getOne(id);
        return new ResponseEntity<>(movieShowtimeDTO, null == movieShowtimeDTO ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String remove(@PathVariable("id") Long id) {
        boolean result = moviesShowtimeService.delete(id);
        return result ? String.format("The movie showtime with id : %s was removed", id)
                : String.format("The movie showtime with id : %s was not removed", id);
    }
}
