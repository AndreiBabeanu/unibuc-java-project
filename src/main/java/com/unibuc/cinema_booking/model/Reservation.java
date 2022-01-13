package com.unibuc.cinema_booking.model;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {
    private long id;
    private long reviewId;
    private long userId;
    private long ticketId;
    private boolean confirmed;
}
