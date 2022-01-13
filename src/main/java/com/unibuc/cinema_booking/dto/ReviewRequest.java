package com.unibuc.cinema_booking.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    private long id;

    @NotNull
    private boolean liked;
    private int stars;
    private String description;
}
