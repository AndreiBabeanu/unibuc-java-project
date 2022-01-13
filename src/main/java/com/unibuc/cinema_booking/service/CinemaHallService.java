package com.unibuc.cinema_booking.service;

import com.unibuc.cinema_booking.dto.CinemaHallRequest;
import com.unibuc.cinema_booking.exception.EntityNotFoundException;
import com.unibuc.cinema_booking.mapper.CinemaHallMapper;
import com.unibuc.cinema_booking.model.Cinema;
import com.unibuc.cinema_booking.model.CinemaHall;
import com.unibuc.cinema_booking.repository.CinemaHallRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CinemaHallService {

    CinemaHallRepository cinemaHallRepository;
    CinemaHallMapper cinemaHallMapper;

    public CinemaHallService(CinemaHallRepository cinemaHallRepository, CinemaHallMapper cinemaHallMapper) {
        this.cinemaHallRepository = cinemaHallRepository;
        this.cinemaHallMapper = cinemaHallMapper;
    }

    public CinemaHallRequest create(CinemaHallRequest cinemaHallRequest)
    {
        //map the request to entity
        CinemaHall cinemaHall = cinemaHallMapper.mapToEntity(cinemaHallRequest);

        //save the entity to database
        CinemaHall savedCinemaHall = cinemaHallRepository.create(cinemaHall);

        //map the entity back to request
        CinemaHallRequest cinemaHallRequestSaved = cinemaHallMapper.mapToDTO(savedCinemaHall);

        return cinemaHallRequestSaved;

    }

    public List<CinemaHallRequest> getAll() {
        return cinemaHallRepository.getAll()
                .stream()
                .map(hall -> cinemaHallMapper.mapToDTO(hall))
                .collect(Collectors.toList());
    }

    public boolean delete(Long id) {
        Optional<CinemaHall> optionalHall = cinemaHallRepository.getAll()
                .stream()
                .filter(hall -> hall.getId() == id)
                .findAny();
        if (optionalHall.isPresent()) {
            cinemaHallRepository.delete(id);
            return true;
        }
        return false;
    }

    public CinemaHallRequest getOne(Long id) {

        Optional<CinemaHall> optionalCinemaHall = cinemaHallRepository.getOne(id);
        if(optionalCinemaHall.isPresent()) {
            return this.cinemaHallMapper.mapToDTO(optionalCinemaHall.get());
        } else {
            throw new EntityNotFoundException("Cinema Hall",id);
        }

    }
}
