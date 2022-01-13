package com.unibuc.cinema_booking.mapper;

import com.unibuc.cinema_booking.dto.SeatRequest;
import com.unibuc.cinema_booking.model.Seat;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public Seat mapToEntity(SeatRequest seatRequest)
    {
        return new Seat(seatRequest.getId(),seatRequest.getCinemaHallId(),seatRequest.getNumberSeat(),
                seatRequest.getRowSeat(),seatRequest.getColumnSeat(),seatRequest.getIsReserved());
    }
    public SeatRequest mapToDTO(Seat seat)
    {
        return  new SeatRequest(seat.getId(), seat.getCinemaHallId(), seat.getNumberSeat(),
                seat.getRowSeat(), seat.getColumnSeat(), seat.getIsReserved());
    }
}
