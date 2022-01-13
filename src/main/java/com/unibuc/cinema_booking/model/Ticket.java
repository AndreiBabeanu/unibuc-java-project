package com.unibuc.cinema_booking.model;
import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private long id;
    private long movieShowtimeId;
    private int price;
    private int numberSeat;
    private Timestamp date_bought;

}
