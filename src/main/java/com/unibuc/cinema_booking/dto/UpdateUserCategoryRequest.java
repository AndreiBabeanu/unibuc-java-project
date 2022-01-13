package com.unibuc.cinema_booking.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserCategoryRequest {

    @NotNull
    private long userId;
    @NotNull
    private long userCategoryId;

}
