package com.unibuc.cinema_booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.cinema_booking.dto.CinemaRequest;
import com.unibuc.cinema_booking.exception.EntityNotFoundException;
import com.unibuc.cinema_booking.mapper.CinemaMapper;
import com.unibuc.cinema_booking.model.Cinema;
import com.unibuc.cinema_booking.service.CinemaService;
import com.unibuc.cinema_booking.util.CinemaDtoUtil;
import com.unibuc.cinema_booking.util.CinemaUtil;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CinemaController.class)
class CinemaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CinemaService cinemaService;

    @MockBean
    private CinemaMapper cinemaMapper;

    @Test
    public void testCreateCinema() throws Exception {
        // Arrange
        CinemaRequest cinemaDto = CinemaDtoUtil.aCinemaDto(0L);
        CinemaRequest cinemaRequestSaved = CinemaDtoUtil.aCinemaDto(1L);

        when(cinemaService.create(any())).thenReturn(cinemaRequestSaved);

        // Act
        MvcResult result = mockMvc.perform(post("/cinemas")
                .content(objectMapper.writeValueAsString(cinemaRequestSaved))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(cinemaRequestSaved));
    }

    @Test
    public void testGetOne() throws Exception {
        // Arrange
        long id = 1L;
        CinemaRequest cinemaRequestSaved = CinemaDtoUtil.aCinemaDto(1L);


        when(cinemaService.getOne(any())).thenReturn(cinemaRequestSaved);

        // Act
        MvcResult result = mockMvc.perform(get("/cinemas/" + id))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(cinemaRequestSaved));
    }

    @Test
    public void testGetOneCascadesException() throws Exception {
        long id = 3L;
        CinemaRequest cinemaRequestSaved = CinemaDtoUtil.aCinemaDto(1L);
        when(cinemaService.getOne(id))
                .thenThrow(new EntityNotFoundException("Cinema",id));

        // Act
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> cinemaService.getOne(id),
                "The Cinema with id 3 doesn't exist"
        );

        // Assert
        assertEquals("The Cinema with id 3 doesn't exist ", exception.getMessage());
    }

    @Test
    public void testGetAll() throws Exception {
        // Arrange
        List<CinemaRequest> cinemasDTO = new ArrayList<CinemaRequest>();
        cinemasDTO.add(CinemaDtoUtil.aCinemaDto(1L));
        cinemasDTO.add(CinemaDtoUtil.aCinemaDto(2L));

        when(cinemaService.getAll()).thenReturn(cinemasDTO);

        // Act
        MvcResult result = mockMvc.perform(get("/cinemas"))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        AssertionsForClassTypes.assertThat(result).isNotNull();

    }



}