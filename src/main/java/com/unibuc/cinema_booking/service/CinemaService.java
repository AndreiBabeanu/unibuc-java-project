package com.unibuc.cinema_booking.service;

import com.unibuc.cinema_booking.dto.CinemaRequest;
import com.unibuc.cinema_booking.dto.LocationRequest;
import com.unibuc.cinema_booking.exception.EntityNotFoundException;
import com.unibuc.cinema_booking.mapper.CinemaMapper;
import com.unibuc.cinema_booking.model.Cinema;
import com.unibuc.cinema_booking.repository.CinemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CinemaService {

    private CinemaRepository cinemaRepository;
    private CinemaMapper cinemaMapper;

    public CinemaService(CinemaRepository cinemaRepository, CinemaMapper cinemaMapper) {
        this.cinemaRepository = cinemaRepository;
        this.cinemaMapper = cinemaMapper;
    }

    public CinemaRequest create(CinemaRequest cinemaRequest)
    {
        //map the request to entity
        Cinema cinema = cinemaMapper.mapToEntity(cinemaRequest);

        //save the entity to database
        Cinema savedCinema = cinemaRepository.create(cinema);

        //map the entity back to request
        CinemaRequest cinemaRequestSaved = cinemaMapper.mapToDTO(savedCinema);

        return cinemaRequestSaved;

    }

    public List<CinemaRequest> getAll() {
        return cinemaRepository.getAll()
                .stream()
                .map(cinema -> cinemaMapper.mapToDTO(cinema))
                .collect(Collectors.toList());
    }

    public boolean delete(Long id) {
        Optional<Cinema> optionalCinema = cinemaRepository.getAll()
                .stream()
                .filter(cinema -> cinema.getId() == id)
                .findAny();
        if (optionalCinema.isPresent()) {
            cinemaRepository.delete(id);
            return true;
        }
        return false;
    }

    public CinemaRequest getOne(Long id) {

        Optional<Cinema> optionalCinema = cinemaRepository.getOne(id);
        if(optionalCinema.isPresent()) {
            return this.cinemaMapper.mapToDTO(optionalCinema.get());
        } else {
            throw new EntityNotFoundException("Cinema",id);
        }

    }
}
