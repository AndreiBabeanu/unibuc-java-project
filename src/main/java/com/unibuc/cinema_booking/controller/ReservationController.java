package com.unibuc.cinema_booking.controller;

import com.unibuc.cinema_booking.dto.*;
import com.unibuc.cinema_booking.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/reservations")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationRequest> create(@RequestBody @Valid ReservationRequest reservationRequest) {
        ReservationRequest savedReservationDTO = reservationService.create(reservationRequest);
        return new ResponseEntity<>(savedReservationDTO, null == savedReservationDTO ? HttpStatus.EXPECTATION_FAILED : HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationRequest> getAll() {
        return reservationService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationRequest> getOne(@PathVariable("id") Long id) {
        ReservationRequest reservationDRO = reservationService.getOne(id);
        return new ResponseEntity<>(reservationDRO, null == reservationDRO ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping(value = "/reservationsUser/{id}")
    public List<ReservationsForUserResponse> getReservationsForUser(@PathVariable("id") Long id) {
            return reservationService.getAllReservationForUser(id);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String remove(@PathVariable("id") Long id) {
        boolean result = reservationService.delete(id);
        return result ? String.format("The reservation with id : %s was removed", id)
                : String.format("The reservation with id : %s was not removed", id);
    }

    @PostMapping("/create")
    public ResponseEntity<ReservationRequest> createReservation(@RequestBody @Valid CreateReservationRequest createReservationRequest) {
        ReservationRequest savedReservationDTO = reservationService.createReservation(createReservationRequest);
        return new ResponseEntity<>(savedReservationDTO, null == savedReservationDTO ? HttpStatus.EXPECTATION_FAILED : HttpStatus.CREATED);
    }

    @PutMapping("/confirm")
    public String updateCategoryUser(@Valid @RequestBody ConfirmRequest confirmRequest) {
        int numberOfUpdatesRows =  reservationService.confirmReservation(confirmRequest);
        return numberOfUpdatesRows == 1 ? String.format("The reservation with id : %d was updated", confirmRequest.getIdReservation())
                : String.format("The reservation with id : %d was not updated", confirmRequest.getIdReservation());
    }
}
