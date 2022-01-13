package com.unibuc.cinema_booking.mapper;

import com.unibuc.cinema_booking.dto.UserRequest;
import com.unibuc.cinema_booking.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToEntity(UserRequest userRequest) {
        return new User(userRequest.getUsername(), userRequest.getPassword(),
                userRequest.getName(), userRequest.getEmail(), userRequest.getAccountCreated(),
                userRequest.getUserCategoryId(),userRequest.getPoints());
    }

    public UserRequest mapToDTO(User savedUser) {
        return new UserRequest(savedUser.getId(), savedUser.getUsername(), savedUser.getPassword(),
                savedUser.getName(), savedUser.getEmail(), savedUser.getAccountCreated(),
                savedUser.getUserCategoryId(), savedUser.getPoints());
    }

}
