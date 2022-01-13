package com.unibuc.cinema_booking.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {

    private long id;
    private String city;
    private String Street;
    private String number;
    private String postalCode;

}
