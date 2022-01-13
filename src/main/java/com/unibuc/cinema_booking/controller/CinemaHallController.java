package com.unibuc.cinema_booking.controller;

import com.unibuc.cinema_booking.dto.CinemaHallRequest;
import com.unibuc.cinema_booking.dto.LocationRequest;
import com.unibuc.cinema_booking.model.CinemaHall;
import com.unibuc.cinema_booking.service.CinemaHallService;
import com.unibuc.cinema_booking.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/halls")
public class CinemaHallController {

    private CinemaHallService cinemaHallService;

    public CinemaHallController(CinemaHallService cinemaHallService) {
        this.cinemaHallService = cinemaHallService;
    }

    @PostMapping
    public ResponseEntity<CinemaHallRequest> createClientAccount(@RequestBody @Valid CinemaHallRequest cinemaHallRequest) {
        CinemaHallRequest savedHallDTO = cinemaHallService.create(cinemaHallRequest);
        return new ResponseEntity<>(savedHallDTO, null == savedHallDTO ? HttpStatus.EXPECTATION_FAILED : HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public List<CinemaHallRequest> getAll() {
        return cinemaHallService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CinemaHallRequest> getOne(@PathVariable("id") Long id) {
        CinemaHallRequest hallDTO = cinemaHallService.getOne(id);
        return new ResponseEntity<>(hallDTO, null == hallDTO ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String remove(@PathVariable("id") Long id) {
        boolean result = cinemaHallService.delete(id);
        return result ? String.format("The cinema hall with id : %s was removed", id)
                : String.format("The cinema hall with id : %s was not removed", id);
    }
}
