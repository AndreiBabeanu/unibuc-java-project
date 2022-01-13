package com.unibuc.cinema_booking.model;


import com.unibuc.cinema_booking.validators.PhoneNumber;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cinema {

    private long id;
    private long locationId;
    private String name;
    private String phoneNumber;



}
