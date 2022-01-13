package com.unibuc.cinema_booking.controller;

import com.unibuc.cinema_booking.dto.LocationRequest;
import com.unibuc.cinema_booking.dto.MovieRequest;
import com.unibuc.cinema_booking.dto.SeatsForAMovieRequest;
import com.unibuc.cinema_booking.dto.SeatsForAMovieRespons;
import com.unibuc.cinema_booking.model.EMovieType;
import com.unibuc.cinema_booking.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieRequest> create(@RequestBody @Valid MovieRequest movieRequest) {
        MovieRequest savedMovieDTO = movieService.create(movieRequest);
        return new ResponseEntity<>(savedMovieDTO, null == savedMovieDTO ? HttpStatus.EXPECTATION_FAILED : HttpStatus.CREATED);
    }

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<MovieRequest> getAll() {
//        return movieService.getAll();
//    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieRequest> getOne(@PathVariable("id") Long id) {
        MovieRequest movieDTO = movieService.getOne(id);
        return new ResponseEntity<>(movieDTO, null == movieDTO ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String remove(@PathVariable("id") Long id) {
        boolean result = movieService.delete(id);
        return result ? String.format("The movie with id : %s was removed", id)
                : String.format("The movie with id : %s was not removed", id);
    }

    @PostMapping(value = "/seats")
    public List<SeatsForAMovieRespons> getSeatsForMovie(@RequestBody @Valid SeatsForAMovieRequest movieRequest) {
        List<SeatsForAMovieRespons> listOfSeats = movieService.getSeatsFromAShowTime(movieRequest);
        return listOfSeats;
    }

    /*this will result in various possible urls:
    GET /movies
    GET /movies?type=ACTION
    GET /movies?type=COMEDY
    GET /movies?type=ACTION&is3D=true
    GET /movies?type=ACTION&rating=5
    GET /movies?rating=0
  and so on, depending on the values for the filters. each filter is optional
 */
    @GetMapping
    public List<MovieRequest> getAllMovies(
            //by default, a request param is required. for it to be optional, we use required = false
            @RequestParam(required = false) EMovieType type,
            @RequestParam(required = false) Boolean is3D,
            @RequestParam(required = false) Integer rating){
        return movieService.getMoviesBy(type, is3D, rating);
    }

}
