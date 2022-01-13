package com.unibuc.cinema_booking.service;


import com.unibuc.cinema_booking.dto.*;
import com.unibuc.cinema_booking.exception.EntityNotFoundException;
import com.unibuc.cinema_booking.exception.SeatReservedException;
import com.unibuc.cinema_booking.mapper.TicketMapper;
import com.unibuc.cinema_booking.model.CinemaHall;
import com.unibuc.cinema_booking.model.Reservation;
import com.unibuc.cinema_booking.model.Seat;
import com.unibuc.cinema_booking.model.Ticket;
import com.unibuc.cinema_booking.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private TicketRepository ticketRepository;
    private TicketMapper ticketMapper;

    //to call services
    private SeatService seatService;
    private MovieService movieService;
    private MoviesShowtimeService moviesShowtimeService;

    @Autowired
    public void setServices(SeatService seatService, MovieService movieService, MoviesShowtimeService moviesShowtimeService) {
        this.seatService = seatService;
        this.movieService = movieService;
        this.moviesShowtimeService = moviesShowtimeService;
    }

    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper, SeatService seatService, MovieService movieService, MoviesShowtimeService moviesShowtimeService) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        setServices(seatService,movieService,moviesShowtimeService);
    }
//    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
//        this.ticketRepository = ticketRepository;
//        this.ticketMapper = ticketMapper;
//    }

    public TicketRequest create(TicketRequest ticketRequest)
    {
        //map the request to entity
        Ticket ticket = ticketMapper.mapToEntity(ticketRequest);

        //save the entity to database
        Ticket savedTicket = ticketRepository.create(ticket);

        //map the entity back to request
        TicketRequest ticketRequestSaved = ticketMapper.mapToDTO(savedTicket);

        return ticketRequestSaved;

    }


    public List<TicketRequest> getAll() {
        return ticketRepository.getAll()
                .stream()
                .map(ticket -> ticketMapper.mapToDTO(ticket))
                .collect(Collectors.toList());
    }

    public boolean delete(Long id) {

        Optional<Ticket> ticketOptional = ticketRepository.getAll()
                .stream()
                .filter(ticket -> ticket.getId() == id)
                .findAny();
        if (ticketOptional.isPresent()) {
            //todo seatService.setSeatReservedFree(idSeat,false);
            TicketRequest ticketRequest = getOne(id);
            MovieShowtimeRequest movieShowtimeRequest = moviesShowtimeService.getOne(ticketRequest.getMovieShowtimeId());
            SeatRequest seatRequest = seatService.getOneByIDHallsAndNumber(movieShowtimeRequest.getCinemaHallId(), ticketRequest.getNumberSeat());
            seatService.setSeatReservedFree(seatRequest.getId(),false);
            ticketRepository.delete(id);
            return true;
        }
        return false;
    }

    public TicketRequest getOne(Long id) {

        Optional<Ticket> ticketOptional = ticketRepository.getOne(id);
        if(ticketOptional.isPresent()) {
            return this.ticketMapper.mapToDTO(ticketOptional.get());
        } else {
            throw new EntityNotFoundException("Ticket",id);
        }
    }

    public TicketRequest createTicketForReservation(long idSeat, long movieShowtimeId, int discount) {

//        int normalMoviePrice = 120;
//        int movie3DPrice = 150;
        SeatRequest seat = seatService.getOne(idSeat);
        if(seat.getIsReserved())
        {
           throw new SeatReservedException(seat.getNumberSeat());
        }
        MovieShowtimeRequest movieShowtimeRequest = moviesShowtimeService.getOne(movieShowtimeId);
        MovieRequest movieRequest = movieService.getOne(movieShowtimeRequest.getMovieId());

        //get the price with discount from user;
        int priceTicket = movieRequest.getIs3D() ? 120 : 150;
        priceTicket = priceTicket - Math.round((discount*priceTicket)/100);

        TicketRequest newTicket = new TicketRequest(0,movieShowtimeId,priceTicket,seat.getNumberSeat(),null);
        TicketRequest savedTicket = create(newTicket);
        //TODO update seat to reserved;
        seatService.setSeatReservedFree(idSeat,true);

        return savedTicket;
    }

}
