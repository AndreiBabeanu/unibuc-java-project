package com.unibuc.cinema_booking.model;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationTicketAssignment {

    private long id;
    private long idReservation;
    private long idTicket;
}
