package com.unibuc.cinema_booking.mapper;

import com.unibuc.cinema_booking.dto.ReservationRequest;
import com.unibuc.cinema_booking.model.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public Reservation mapToEntity(ReservationRequest reservationRequest)
    {
        return new Reservation(reservationRequest.getId(), reservationRequest.getReviewId(), reservationRequest.getUserId(), reservationRequest.getTicketId(), reservationRequest.isConfirmed());

    }

    public ReservationRequest mapToDTO(Reservation reservation)
    {
        return new ReservationRequest(reservation.getId(), reservation.getReviewId(), reservation.getUserId(), reservation.getTicketId(), reservation.isConfirmed());
    }

}
