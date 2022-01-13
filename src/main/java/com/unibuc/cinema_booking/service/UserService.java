package com.unibuc.cinema_booking.service;

import com.unibuc.cinema_booking.dto.*;
import com.unibuc.cinema_booking.exception.EntityAlreadyExistException;
import com.unibuc.cinema_booking.exception.EntityNotFoundException;
import com.unibuc.cinema_booking.exception.LoginFailException;
import com.unibuc.cinema_booking.mapper.UserMapper;
import com.unibuc.cinema_booking.model.EUserCategory;
import com.unibuc.cinema_booking.model.User;
import com.unibuc.cinema_booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    //to call userCategoryService
    private UserCategoryService userCategoryService;


    @Autowired
    public void setUserCategoryService(UserCategoryService userCategoryService) {
        this.userCategoryService = userCategoryService;
    }

    public UserService(UserRepository userRepository, UserMapper userMapper, UserCategoryService userCategoryService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        setUserCategoryService(userCategoryService);
    }

    public UserRequest createUser(UserRequest userRequest, EUserCategory userCategory) {

        if(userRepository.checkUsernameExist(userRequest.getUsername()))
            throw new EntityAlreadyExistException("User","username",userRequest.getUsername());

        userRequest.setUserCategoryId(userCategoryService.getUserCategoryByName(userCategory).getId());

        // map dto to entity
        User user = this.userMapper.mapToEntity(userRequest);

        // saved entity in database
        User savedUser = userRepository.create(user);

        // mapped entity back to dto mapToDTOWithId
        // UserRequest saveUserDto = this.userMapper.mapToDto(savedUser);
        // if we want to see the id
        UserRequest saveUserDto = this.userMapper.mapToDTO(savedUser);

        // return saved dto
        return saveUserDto;
    }

    public UserRequest getOne(Long id) {

        Optional<User> userOptional = userRepository.getOne(id);
        if(userOptional.isPresent()) {
            return this.userMapper.mapToDTO(userOptional.get());
        } else {
            throw new EntityNotFoundException("User",id);
        }

    }

    public List<UserRequest> getAll() {
        return userRepository.getAll()
                .stream()
                .map(user -> userMapper.mapToDTO(user))
                .collect(Collectors.toList());
    }

    public boolean delete(Long id) {
        Optional<User> optionalUser = userRepository.getAll()
                .stream()
                .filter(user -> user.getId() == id)
                .findAny();
        if (optionalUser.isPresent()) {
            userRepository.delete(id);
            return true;
        }
        return false;
    }

    //return the number of updates lines
    public int updateCategoryUser(UpdateUserCategoryRequest updateRequest) {

        Optional<User> userOptional = userRepository.getOne(updateRequest.getUserId());
        if(userOptional.isEmpty()) {
            throw new EntityNotFoundException("User",updateRequest.getUserId());
        }
        Optional<UserCategoryRequest> userCategoryOptional = Optional.ofNullable(userCategoryService.getOne(updateRequest.getUserCategoryId()));
        if(userCategoryOptional.isEmpty()) {
            throw new EntityNotFoundException("User category",updateRequest.getUserCategoryId());
        }

        return userRepository.updateUserCategory(updateRequest);
    }

    public UserRequest login(LoginRequest loginRequest)
    {
        Optional<User> userOptional = userRepository.getUserForLogin(loginRequest.getUsername(), loginRequest.getPassword());
        User userLogged;
        if(userOptional.isPresent()) {
            userLogged = userOptional.get();
        } else {
            throw new LoginFailException("username and password are incorrect");
        }

        // mapped entity to dto
        UserRequest loggedUserDTO = this.userMapper.mapToDTO(userLogged);

        return loggedUserDTO;
    }

    public List<HistoryReservationsUserResponse> getUserHistoryOfReservations(HistoryRequest historyRequest)
    {
        long userId = historyRequest.getIdUser();
        Optional<User> userOptional = userRepository.getOne(userId);
        if(userOptional.isPresent()) {
            return userRepository.getUserHistoryOfReservations(userId);
        } else {
            throw new EntityNotFoundException("User",userId);
        }
    }

    public int getUserDiscount(long userId)
    {
        return userRepository.getUserDiscount(userId);
    }

    public int updateUserPoints(int points, long idUser)
    {
        return userRepository.updatePointsUser(points,idUser);
    }
}





