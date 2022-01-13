package com.unibuc.cinema_booking.controller;

import com.unibuc.cinema_booking.dto.LocationRequest;
import com.unibuc.cinema_booking.dto.SeatRequest;
import com.unibuc.cinema_booking.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/seats")
public class SeatController {

    private SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    public ResponseEntity<SeatRequest> createClientAccount(@RequestBody @Valid SeatRequest seatRequest) {
        SeatRequest savedSeatRequest = seatService.create(seatRequest);
        return new ResponseEntity<>(savedSeatRequest, null == savedSeatRequest ? HttpStatus.EXPECTATION_FAILED : HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SeatRequest> getOne(@PathVariable("id") Long id) {
        SeatRequest seatDRO = seatService.getOne(id);
        return new ResponseEntity<>(seatDRO, null == seatDRO ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String remove(@PathVariable("id") Long id) {
        boolean result = seatService.delete(id);
        return result ? String.format("The location with id : %s was removed", id)
                : String.format("The location with id : %s was not removed", id);
    }
}
