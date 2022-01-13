package com.unibuc.cinema_booking.mapper;

import com.unibuc.cinema_booking.dto.ReviewRequest;
import com.unibuc.cinema_booking.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review mapToEntity(ReviewRequest reviewRequest)
    {
        return new Review(reviewRequest.getId(), reviewRequest.isLiked(), reviewRequest.getStars(), reviewRequest.getDescription());

    }

    public ReviewRequest mapToDTO(Review review)
    {
        return new ReviewRequest(review.getId(), review.isLiked(), review.getStars(), review.getDescription());
    }
}
