package com.unibuc.cinema_booking.model;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private long id;
    private String city;
    private String Street;
    private String number;
    private String postalCode;

}

