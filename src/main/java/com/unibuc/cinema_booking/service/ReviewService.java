package com.unibuc.cinema_booking.service;

import com.unibuc.cinema_booking.dto.LocationRequest;
import com.unibuc.cinema_booking.dto.ReviewRequest;
import com.unibuc.cinema_booking.exception.EntityNotFoundException;
import com.unibuc.cinema_booking.mapper.ReviewMapper;
import com.unibuc.cinema_booking.model.Review;
import com.unibuc.cinema_booking.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    public ReviewRequest create(ReviewRequest reviewRequest)
    {
        //map the request to entity
        Review review = reviewMapper.mapToEntity(reviewRequest);

        //save the entity to database
        Review savedReview = reviewRepository.create(review);

        //map the entity back to request
        ReviewRequest savedReviewRequest = reviewMapper.mapToDTO(savedReview);
        return savedReviewRequest;
    }

    public boolean delete(Long id) {
        Optional<Review> optionalReview = reviewRepository.getAll()
                .stream()
                .filter(review -> review.getId() == id)
                .findAny();
        if (optionalReview.isPresent()) {
            reviewRepository.delete(id);
            return true;
        }
        throw new EntityNotFoundException("Review",id);
    }

    public ReviewRequest getOne(Long id) {

        Optional<Review> optionalReview = reviewRepository.getOne(id);
        if(optionalReview.isPresent()) {
            return this.reviewMapper.mapToDTO(optionalReview.get());
        } else {
            throw new EntityNotFoundException("Review",id);
        }

    }

    public List<ReviewRequest> getAll() {
        return reviewRepository.getAll()
                .stream()
                .map(review -> reviewMapper.mapToDTO(review))
                .collect(Collectors.toList());
    }


}
