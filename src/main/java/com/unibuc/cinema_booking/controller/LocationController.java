package com.unibuc.cinema_booking.controller;

import com.unibuc.cinema_booking.dto.LocationRequest;
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
@RequestMapping("/locations")
public class LocationController {

    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<LocationRequest> create(@RequestBody @Valid  LocationRequest locationRequest) {
        LocationRequest savedLocationDTO = locationService.create(locationRequest);
        return new ResponseEntity<>(savedLocationDTO, null == savedLocationDTO ? HttpStatus.EXPECTATION_FAILED : HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LocationRequest> getAll() {
        return locationService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LocationRequest> getOne(@PathVariable("id") Long id) {
        LocationRequest locationDTO = locationService.getOne(id);
        return new ResponseEntity<>(locationDTO, null == locationDTO ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String remove(@PathVariable("id") Long id) {
        boolean result = locationService.delete(id);
        return result ? String.format("The location with id : %s was removed", id)
                : String.format("The location with id : %s was not removed", id);
    }


}
