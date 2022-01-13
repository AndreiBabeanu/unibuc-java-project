package com.unibuc.cinema_booking.model;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserCategory {

    private long id;
    private EUserCategory categoryName;
    private int reservationMaxTime;
    private int discount;
    private int minimPoints;

}

