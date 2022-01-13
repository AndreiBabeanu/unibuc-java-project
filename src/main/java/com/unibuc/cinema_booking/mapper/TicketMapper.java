package com.unibuc.cinema_booking.mapper;

import com.unibuc.cinema_booking.dto.TicketRequest;
import com.unibuc.cinema_booking.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public Ticket mapToEntity(TicketRequest ticketRequest)
    {
        return new Ticket(ticketRequest.getId(),ticketRequest.getMovieShowtimeId(),ticketRequest.getPrice(),ticketRequest.getNumberSeat(),ticketRequest.getDate_bought());
    }

    public TicketRequest mapToDTO(Ticket ticket)
    {
        return new TicketRequest(ticket.getId(),ticket.getMovieShowtimeId(),ticket.getPrice(), ticket.getNumberSeat(), ticket.getDate_bought());
    }
}
