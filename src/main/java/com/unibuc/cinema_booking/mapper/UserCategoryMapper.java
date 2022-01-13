package com.unibuc.cinema_booking.mapper;

import com.unibuc.cinema_booking.dto.UserCategoryRequest;
import com.unibuc.cinema_booking.model.UserCategory;
import org.springframework.stereotype.Component;

@Component
public class UserCategoryMapper {

    public UserCategory mapToEntity(UserCategoryRequest userCategoryRequest)
    {
        UserCategory userCategory = new UserCategory();
        userCategory.setId(userCategoryRequest.getId());
        userCategory.setCategoryName(userCategoryRequest.getCategoryName());
        userCategory.setReservationMaxTime(userCategoryRequest.getReservationMinTime());
        userCategory.setDiscount(userCategoryRequest.getDiscount());
        userCategory.setMinimPoints(userCategoryRequest.getMinimPoints());
        return userCategory;
    }

    public UserCategoryRequest mapToDTO(UserCategory userCategory)
    {
        UserCategoryRequest userCategoryRequest = new UserCategoryRequest();
        userCategoryRequest.setId(userCategory.getId());
        userCategoryRequest.setCategoryName(userCategory.getCategoryName());
        userCategoryRequest.setDiscount(userCategory.getDiscount());
        userCategoryRequest.setReservationMinTime(userCategory.getReservationMaxTime());
        userCategoryRequest.setMinimPoints(userCategory.getMinimPoints());
        return userCategoryRequest;
    }
}
