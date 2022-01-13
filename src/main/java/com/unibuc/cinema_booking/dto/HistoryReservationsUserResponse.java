package com.unibuc.cinema_booking.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryReservationsUserResponse {

    private String username;
    private String email;
    private Boolean confirmed;
    private int numberSeat;
    private int price;
    private String date;
}
