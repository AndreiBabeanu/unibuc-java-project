package com.unibuc.cinema_booking.service;

import com.unibuc.cinema_booking.dto.UserCategoryRequest;
import com.unibuc.cinema_booking.exception.EntityNotFoundException;
import com.unibuc.cinema_booking.mapper.UserCategoryMapper;
import com.unibuc.cinema_booking.model.EUserCategory;
import com.unibuc.cinema_booking.model.UserCategory;
import com.unibuc.cinema_booking.repository.UserCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserCategoryService {

    private UserCategoryRepository userCategoryRepository;
    private UserCategoryMapper userCategoryMapper;


    public UserCategoryService(UserCategoryRepository userCategoryRepository, UserCategoryMapper userCategoryMapper) {
        this.userCategoryRepository = userCategoryRepository;
        this.userCategoryMapper = userCategoryMapper;
    }


    public UserCategoryRequest getUserCategoryByName(EUserCategory userCategory) {
        Optional<UserCategory> userCategoryOptional = userCategoryRepository.getUserCategoryByName(userCategory);
        if(userCategoryOptional.isPresent()) {
            return this.userCategoryMapper.mapToDTO(userCategoryOptional.get());
        } else {
            throw new EntityNotFoundException("User Category", "user category", userCategory.toString());
        }
    }

    public UserCategoryRequest getOne(Long id) {
        System.out.println(id);
        Optional<UserCategory> userCategoryOptional = userCategoryRepository.getOne(id);
        if(userCategoryOptional.isPresent()) {
            return this.userCategoryMapper.mapToDTO(userCategoryOptional.get());
        } else {
            throw new EntityNotFoundException("User Category", id);
        }
    }

    public List<UserCategoryRequest> getAll() {
        return userCategoryRepository.getAll()
                .stream()
                .map(category -> userCategoryMapper.mapToDTO(category))
                .collect(Collectors.toList());
    }
}
