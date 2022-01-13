package com.unibuc.cinema_booking.mapper;

import com.unibuc.cinema_booking.dto.MovieRequest;
import com.unibuc.cinema_booking.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public Movie mapToEntity(MovieRequest movieRequest)
    {
        return new Movie(movieRequest.getId(),movieRequest.getName(),movieRequest.getDurationMinutes(), movieRequest.getIs3D(),
                movieRequest.getType(),movieRequest.getRating(),movieRequest.getDescription(),movieRequest.getReleaseYear());
    }

    public MovieRequest mapToDTO(Movie movie)
    {
        return new MovieRequest(movie.getId(),movie.getName(),movie.getDurationMinutes(),movie.getIs3D(),movie.getType(),
                movie.getRating(), movie.getDescription(), movie.getReleaseYear());
    }
}
