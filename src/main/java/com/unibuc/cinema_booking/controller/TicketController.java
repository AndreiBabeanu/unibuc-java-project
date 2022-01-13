package com.unibuc.cinema_booking.controller;

import com.unibuc.cinema_booking.dto.TicketRequest;
import com.unibuc.cinema_booking.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.plugins.tiff.TIFFDirectory;
import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/tickets")
public class TicketController {

    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketRequest> create(@RequestBody @Valid TicketRequest ticketRequest) {
        TicketRequest savedTicketDTO = ticketService.create(ticketRequest);
        return new ResponseEntity<>(savedTicketDTO, null == savedTicketDTO ? HttpStatus.EXPECTATION_FAILED : HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TicketRequest> getAll() {
        return ticketService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TicketRequest> getOne(@PathVariable("id") Long id) {
        TicketRequest ticketRequestDTO = ticketService.getOne(id);
        return new ResponseEntity<>(ticketRequestDTO, null == ticketRequestDTO ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String remove(@PathVariable("id") Long id) {
        boolean result = ticketService.delete(id);
        return result ? String.format("The ticket with id : %s was removed", id)
                : String.format("The ticket with id : %s was not removed", id);
    }
}
