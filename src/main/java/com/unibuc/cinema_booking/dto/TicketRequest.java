package com.unibuc.cinema_booking.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    private long id;
    @NotNull
    private long movieShowtimeId;
    @NotNull
    private int price;
    @NotNull
    private int numberSeat;

    private Timestamp date_bought;

}
