package com.unibuc.cinema_booking.service;

import com.unibuc.cinema_booking.dto.SeatsForAMovieRequest;
import com.unibuc.cinema_booking.dto.SeatsForAMovieRespons;
import com.unibuc.cinema_booking.dto.MovieRequest;
import com.unibuc.cinema_booking.exception.EntityNotFoundException;
import com.unibuc.cinema_booking.mapper.MovieMapper;
import com.unibuc.cinema_booking.model.EMovieType;
import com.unibuc.cinema_booking.model.Movie;
import com.unibuc.cinema_booking.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private MovieRepository movieRepository;
    private MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    public MovieRequest create(MovieRequest movieRequest)
    {
        System.out.println(movieRequest.getType().toString());
        //map the request to entity
        Movie movie = movieMapper.mapToEntity(movieRequest);

        //save the entity to database
        Movie savedMovie = movieRepository.create(movie);

        //map the entity back to request
        MovieRequest movieRequestSaved = movieMapper.mapToDTO(savedMovie);

        return movieRequestSaved;

    }

    public List<MovieRequest> getAll() {
        return movieRepository.getAll()
                .stream()
                .map(movie -> movieMapper.mapToDTO(movie))
                .collect(Collectors.toList());
    }

    public boolean delete(Long id) {
        Optional<Movie> optionalMovie = movieRepository.getAll()
                .stream()
                .filter(movie -> movie.getId() == id)
                .findAny();
        if (optionalMovie.isPresent()) {
            movieRepository.delete(id);
            return true;
        }
        return false;
    }

    public MovieRequest getOne(Long id) {

        Optional<Movie> optionalMovie = movieRepository.getOne(id);
        if(optionalMovie.isPresent()) {
            return this.movieMapper.mapToDTO(optionalMovie.get());
        } else {
            throw new EntityNotFoundException("Movie",id);
        }

    }

    public List<SeatsForAMovieRespons> getSeatsFromAShowTime(SeatsForAMovieRequest request)
    {
        Optional<Movie> optionalMovie = movieRepository.getOne(request.getIdMovie());
        if(optionalMovie.isPresent()) {
            List<SeatsForAMovieRespons> seats = movieRepository.getSeatsFromAShowTime(request);
            return seats;
        } else {
            throw new EntityNotFoundException("Movie",request.getIdMovie());
        }

    }


    public List<MovieRequest> getMoviesBy(EMovieType type, Boolean is3D, Integer rating) {
        List<MovieRequest>  movies = getAll();
        List<MovieRequest> moviesFiltrated = movies.stream()
                .filter(movie -> {
                    if (type!= null) {//we filter by type only if the type was sent in the request
                        if (is3D != null) { //we filter by is3D only if the type was sent in the request
                            if(rating != null) {
                                return type.equals(movie.getType()) && is3D == movie.getIs3D() && rating == movie.getRating();
                            }else{
                                return type.equals(movie.getType()) && is3D == movie.getIs3D();
                            }
                        } else {
                            return type.equals(movie.getType());
                        }
                    } else {//no filters are sent in the request, all movies accounts should be returned
                        return true;
                    }
                })
                .collect(Collectors.toList());
        return moviesFiltrated;
    }
}
