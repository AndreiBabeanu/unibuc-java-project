package com.unibuc.cinema_booking.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatsForAMovieRespons {


    private long idSeat;
    private long idShowtime;
    private String nameCinema;
    private String nameHall;
    private String date;
    private int numberSeat;
    private int columnSeat;
    private int rowSeat;
    private Boolean isReserved;
}
