package com.unibuc.cinema_booking.controller;

import com.unibuc.cinema_booking.dto.LocationRequest;
import com.unibuc.cinema_booking.dto.ReviewRequest;
import com.unibuc.cinema_booking.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewRequest> create(@RequestBody @Valid ReviewRequest reviewRequest) {
        ReviewRequest savedReviewDTO = reviewService.create(reviewRequest);
        return new ResponseEntity<>(savedReviewDTO, null == savedReviewDTO ? HttpStatus.EXPECTATION_FAILED : HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReviewRequest> getAll() {
        return reviewService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReviewRequest> getOne(@PathVariable("id") Long id) {
        ReviewRequest reviewDTO = reviewService.getOne(id);
        return new ResponseEntity<>(reviewDTO, null == reviewDTO ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String remove(@PathVariable("id") Long id) {
        boolean result = reviewService.delete(id);
        return result ? String.format("The review with id : %s was removed", id)
                : String.format("The review with id : %s was not removed", id);
    }
}
