package com.unibuc.cinema_booking.model;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    private long id;
    private boolean liked;
    private int stars;
    private String description;
}
