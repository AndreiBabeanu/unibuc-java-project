package com.unibuc.cinema_booking.dto;

import com.unibuc.cinema_booking.model.EUserCategory;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCategoryRequest {

    private long id;

    @NotNull
    private EUserCategory categoryName;

    @Min(0)
    @Max(180)
    private int reservationMinTime;

    @Min(0)
    @Max(50)
    private int discount;

    @Min(0)
    private int minimPoints;
}
