package com.unibuc.cinema_booking.dto;


import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    private long id;
    private long reviewId;
    @NotNull
    private long userId;
    @NotNull
    private long ticketId;

    private boolean confirmed;

    public ReservationRequest(@NotNull long userId, @NotNull long ticketId) {
        this.userId = userId;
        this.ticketId = ticketId;
        this.id = 0;
        this.confirmed = false;
    }
}