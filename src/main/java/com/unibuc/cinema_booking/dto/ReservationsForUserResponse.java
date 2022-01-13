package com.unibuc.cinema_booking.dto;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationsForUserResponse {

    private long idReservation;
    private String username;
    private String email;
    private Boolean confirmed;
    private int numberSeat;
    private int price;
    private String date;


}