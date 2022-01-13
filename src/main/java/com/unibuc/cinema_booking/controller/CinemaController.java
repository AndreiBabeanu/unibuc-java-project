package com.unibuc.cinema_booking.controller;

import com.unibuc.cinema_booking.dto.CinemaRequest;
import com.unibuc.cinema_booking.service.CinemaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/cinemas")
public class CinemaController {

    private CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @PostMapping
    public ResponseEntity<CinemaRequest> createCinema(@Valid @RequestBody  CinemaRequest cinemaRequest) {
        CinemaRequest savedCinemaDTO = cinemaService.create(cinemaRequest);
        return new ResponseEntity<>(savedCinemaDTO, null == savedCinemaDTO ? HttpStatus.EXPECTATION_FAILED : HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CinemaRequest> getAll() {
        return cinemaService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CinemaRequest> getOne(@PathVariable("id") Long id) {
        CinemaRequest cinemaDTO = cinemaService.getOne(id);
        return new ResponseEntity<>(cinemaDTO, null == cinemaDTO ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String remove(@PathVariable("id") Long id) {
        boolean result = cinemaService.delete(id);
        return result ? String.format("The cinema with id : %s was removed", id)
                : String.format("The cinema with id : %s was not removed", id);
    }
}
