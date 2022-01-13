package com.unibuc.cinema_booking.mapper;


import com.unibuc.cinema_booking.dto.LocationRequest;
import com.unibuc.cinema_booking.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public Location mapToEntity(LocationRequest locationRequest) {
        return new Location(locationRequest.getId(),locationRequest.getCity(),locationRequest.getStreet(),
                locationRequest.getNumber(),locationRequest.getPostalCode());
    }

    public LocationRequest mapToDTO(Location savedLocation) {
        return new LocationRequest(savedLocation.getId(),savedLocation.getCity(),savedLocation.getStreet(),
                savedLocation.getNumber(),savedLocation.getPostalCode());
    }
}
