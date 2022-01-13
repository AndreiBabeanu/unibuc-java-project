package com.unibuc.cinema_booking.dto;
import com.unibuc.cinema_booking.validators.PhoneNumber;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CinemaRequest {

    private long id;

    @NotNull
    private long locationId;

    @NotBlank(message = "This field is required.")
    private String name;

    @PhoneNumber
    private String phoneNumber;

}
