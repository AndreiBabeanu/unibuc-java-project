package com.unibuc.cinema_booking.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotNull
    @Length(min = 4, max = 16)
    private String username;

    @NotNull
    @Length(min = 4, max = 40)
    private String password;

}
