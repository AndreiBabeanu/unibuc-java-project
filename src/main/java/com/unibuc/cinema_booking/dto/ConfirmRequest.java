package com.unibuc.cinema_booking.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRequest {

    @NotNull
    private long idUser;
    @NotNull
    private long idReservation;

}
