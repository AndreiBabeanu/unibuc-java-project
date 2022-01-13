package com.unibuc.cinema_booking.controller;
import com.unibuc.cinema_booking.dto.UserCategoryRequest;
import com.unibuc.cinema_booking.dto.UserRequest;
import com.unibuc.cinema_booking.model.EUserCategory;
import com.unibuc.cinema_booking.service.UserCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
@RequestMapping("/userCategories")
public class UserCategoryController {

    private UserCategoryService userCategoryService;

    public UserCategoryController(UserCategoryService userCategoryService) {
        this.userCategoryService = userCategoryService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserCategoryRequest> getOne(@PathVariable("id") Long id) {
        UserCategoryRequest userCategoryDto = userCategoryService.getOne(id);
        return new ResponseEntity<>(userCategoryDto, null == userCategoryDto ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping(value = "/type/{Name}")
    public ResponseEntity<UserCategoryRequest> getUserCategoryByName(@PathVariable("Name") EUserCategory nameCategory) {
        UserCategoryRequest userCategoryDto = userCategoryService.getUserCategoryByName(nameCategory);
        return new ResponseEntity<>(userCategoryDto, null == userCategoryDto ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserCategoryRequest> getAll() {
        return userCategoryService.getAll();
    }


}
