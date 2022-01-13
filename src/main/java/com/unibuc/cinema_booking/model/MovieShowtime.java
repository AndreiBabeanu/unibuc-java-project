package com.unibuc.cinema_booking.model;
import java.time.LocalDateTime;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieShowtime {

    private long id;
    private long movieId;
    private long cinemaHallId;
    private String date;
}
