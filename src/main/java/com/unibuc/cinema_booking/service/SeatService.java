package com.unibuc.cinema_booking.service;

import com.unibuc.cinema_booking.dto.LocationRequest;
import com.unibuc.cinema_booking.dto.SeatRequest;
import com.unibuc.cinema_booking.exception.EntityNotFoundException;
import com.unibuc.cinema_booking.mapper.SeatMapper;
import com.unibuc.cinema_booking.model.Location;
import com.unibuc.cinema_booking.model.Seat;
import com.unibuc.cinema_booking.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatService {

    private SeatRepository seatRepository;
    private SeatMapper seatMapper;

    public SeatService(SeatRepository seatRepository, SeatMapper seatMapper) {
        this.seatRepository = seatRepository;
        this.seatMapper = seatMapper;
    }

    public SeatRequest create(SeatRequest seatRequest)
    {
        //map the request to entity
        Seat seat = seatMapper.mapToEntity(seatRequest);

        //save the entity to database
        Seat savedSeat = seatRepository.create(seat);

        //map the entity back to request
        SeatRequest seatRequestSaved = seatMapper.mapToDTO(savedSeat);

        return seatRequestSaved;

    }

//    public List<SeatRequest> getAll() {
//        return seatRepository.getAll()
//                .stream()
//                .map(seat -> seatMapper.mapToDTO(seat))
//                .collect(Collectors.toList());
//    }

    public boolean delete(Long id) {
        Optional<Seat> optionalSeat = seatRepository.getAll()
                .stream()
                .filter(seat -> seat.getId() == id)
                .findAny();
        if (optionalSeat.isPresent()) {
            seatRepository.delete(id);
            return true;
        }
        return false;
    }

    public SeatRequest getOne(Long id) {

        Optional<Seat> optionalSeat = seatRepository.getOne(id);
        if(optionalSeat.isPresent()) {
            return this.seatMapper.mapToDTO(optionalSeat.get());
        } else {
            throw new EntityNotFoundException("Seat",id);
        }

    }


    public SeatRequest getOneByIDHallsAndNumber(Long id, int numberSeat) {

        Optional<Seat> optionalSeat = seatRepository.getOneByIDHallsAndNumber(id,numberSeat);
        if(optionalSeat.isPresent()) {
            return this.seatMapper.mapToDTO(optionalSeat.get());
        } else {
            throw new EntityNotFoundException("Seat",id);
        }

    }

    public int setSeatReservedFree(long idSeat, Boolean value)
    {
      return seatRepository.setSeatReservedFree(idSeat,value);
    }

}
