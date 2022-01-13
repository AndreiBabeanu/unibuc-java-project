package com.unibuc.cinema_booking.service;

import com.unibuc.cinema_booking.dto.CinemaRequest;
import com.unibuc.cinema_booking.exception.EntityNotFoundException;
import com.unibuc.cinema_booking.mapper.CinemaMapper;
import com.unibuc.cinema_booking.model.Cinema;
import com.unibuc.cinema_booking.repository.CinemaRepository;
import com.unibuc.cinema_booking.util.CinemaDtoUtil;
import com.unibuc.cinema_booking.util.CinemaUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

//TODO ^_^
//TODO pentru ca cele mai multe servicii seamana intre ele, cele de operatii crud, am implementat teste pentru un sigur serviciu, din lipsa timpului (JOB + ALTE PROIECTE)
@ExtendWith(MockitoExtension.class)
class CinemaServiceTest {

    @Mock
    private CinemaRepository cinemaRepository;

    @Mock
    private CinemaMapper cinemaMapper;

    @InjectMocks
    private CinemaService cinemaService;

    @Test
    @DisplayName("Create cinema - happy flow")
    void createCinemaHappyFlow() {

        // Arrange
        CinemaRequest cinemaDto = CinemaDtoUtil.aCinemaDto(0L);
        Cinema cinema = CinemaUtil.anCinema(0L);
        Cinema savedCinema = CinemaUtil.anCinema(9L);
        CinemaRequest cinemaRequestSaved = CinemaDtoUtil.aCinemaDto(9L);



        when(cinemaMapper.mapToEntity(cinemaDto)).thenReturn(cinema);
        when(cinemaRepository.create(cinema)).thenReturn(savedCinema);
        when(cinemaMapper.mapToDTO(savedCinema)).thenReturn(cinemaRequestSaved);

        // Act
        CinemaRequest result = cinemaService.create(cinemaDto);

        //Assert
        assertThat(result).isNotNull();

        verify(cinemaMapper, times(1)).mapToEntity(cinemaDto);
        verify(cinemaMapper, times(1)).mapToDTO(savedCinema);
        verify(cinemaRepository, times(1)).create(cinema);

        verifyNoMoreInteractions(cinemaMapper, cinemaRepository);

    }


    @Test
    @DisplayName("Get cinema by ID - happy flow cinema exists")
    void getCinemaByIDHappyFlow() {

        // Arrange
        List<CinemaRequest> cinemasDTO = new ArrayList<CinemaRequest>();
        cinemasDTO.add(CinemaDtoUtil.aCinemaDto(1L));
        cinemasDTO.add(CinemaDtoUtil.aCinemaDto(2L));


        List<Cinema> cinemas = new ArrayList<Cinema>();
        cinemas.add(CinemaUtil.anCinema(1L));
        cinemas.add(CinemaUtil.anCinema(2L));

        when(cinemaRepository.getOne(1L)).thenReturn(Optional.ofNullable(cinemas.get(0)));
        when(cinemaMapper.mapToDTO(Optional.ofNullable(cinemas.get(0)).get())).thenReturn(cinemasDTO.get(0));


        CinemaRequest result = cinemaService.getOne(1L);


        assertEquals(1, result.getId());
        assertEquals(cinemas.get(0).getName(), result.getName());
        assertEquals(cinemas.get(0).getLocationId(), result.getLocationId());
        assertEquals(cinemas.get(0).getPhoneNumber(), result.getPhoneNumber());

        verify(cinemaRepository).getOne(1L);
    }

    @Test
    @DisplayName("Get cinema by ID - throws exception")
    void getCinemaByIDThrowException() {

        // Arrange
        List<CinemaRequest> cinemasDTO = new ArrayList<CinemaRequest>();
        cinemasDTO.add(CinemaDtoUtil.aCinemaDto(1L));
        cinemasDTO.add(CinemaDtoUtil.aCinemaDto(2L));


        List<Cinema> cinemas = new ArrayList<Cinema>();
        cinemas.add(CinemaUtil.anCinema(1L));
        cinemas.add(CinemaUtil.anCinema(2L));

        when(cinemaRepository.getOne(3L)).thenReturn(Optional.empty());


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> cinemaService.getOne(3L));


        assertEquals("The Cinema with id 3 doesn't exist ", exception.getMessage());

    }

    @Test
    @DisplayName("Get all cinemas - happy flow")
    void getAllCinemasHappyFlow() {

        // Arrange
        List<CinemaRequest> cinemasDTO = new ArrayList<CinemaRequest>();
        cinemasDTO.add(CinemaDtoUtil.aCinemaDto(1L));
        cinemasDTO.add(CinemaDtoUtil.aCinemaDto(2L));

        List<Cinema> cinemas = new ArrayList<Cinema>();
        cinemas.add(CinemaUtil.anCinema(1L));
        cinemas.add(CinemaUtil.anCinema(2L));

        when(cinemaRepository.getAll()).thenReturn(cinemas);
        when(cinemaMapper.mapToDTO(cinemas.get(0))).thenReturn(cinemasDTO.get(0));

        // Act
        List<CinemaRequest> result = cinemaService.getAll();

        //Assert
        assertThat(result).isNotNull();
        assertEquals(2, result.size());

        verify(cinemaRepository, times(1)).getAll();

    }

    @Test
    void deleteCinemaByIDHappyFlow() {
        // Arrange
        List<CinemaRequest> cinemasDTO = new ArrayList<CinemaRequest>();
        cinemasDTO.add(CinemaDtoUtil.aCinemaDto(1L));
        cinemasDTO.add(CinemaDtoUtil.aCinemaDto(2L));


        List<Cinema> cinemas = new ArrayList<Cinema>();
        cinemas.add(CinemaUtil.anCinema(1L));
        cinemas.add(CinemaUtil.anCinema(2L));

        //arrange
        when(cinemaRepository.delete(1L)).thenReturn(true);

        //act
        Boolean result = cinemaRepository.delete(1L);
        assertEquals(true,result);
        verify(cinemaRepository, times(1)).delete(1L);
    }

}