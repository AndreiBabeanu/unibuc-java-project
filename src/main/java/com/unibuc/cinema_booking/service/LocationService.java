package com.unibuc.cinema_booking.service;


import com.unibuc.cinema_booking.dto.LocationRequest;
import com.unibuc.cinema_booking.exception.EntityNotFoundException;
import com.unibuc.cinema_booking.mapper.LocationMapper;
import com.unibuc.cinema_booking.model.Location;
import com.unibuc.cinema_booking.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private LocationRepository locationRepository;
    private LocationMapper locationMapper;

    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    public LocationRequest create(LocationRequest locationRequest)
    {
        //map the request to entity
        Location location = locationMapper.mapToEntity(locationRequest);

        //save the entity to database
        Location savedLocation = locationRepository.create(location);

        //map the entity back to request
        LocationRequest locationRequestSaved = locationMapper.mapToDTO(savedLocation);

        return locationRequestSaved;

    }

    public List<LocationRequest> getAll() {
        return locationRepository.getAll()
                .stream()
                .map(location -> locationMapper.mapToDTO(location))
                .collect(Collectors.toList());
    }

    public boolean delete(Long id) {
        Optional<Location> optionalLocation = locationRepository.getAll()
                .stream()
                .filter(location -> location.getId() == id)
                .findAny();
        if (optionalLocation.isPresent()) {
            locationRepository.delete(id);
            return true;
        }
        return false;
    }

    public LocationRequest getOne(Long id) {

        Optional<Location> optionalLocation = locationRepository.getOne(id);
        if(optionalLocation.isPresent()) {
            return this.locationMapper.mapToDTO(optionalLocation.get());
        } else {
            throw new EntityNotFoundException("Location",id);
        }

    }
}
