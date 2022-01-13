package com.unibuc.cinema_booking.service;

import com.unibuc.cinema_booking.dto.*;
import com.unibuc.cinema_booking.exception.EntityNotFoundException;
import com.unibuc.cinema_booking.mapper.ReservationMapper;
import com.unibuc.cinema_booking.model.Location;
import com.unibuc.cinema_booking.model.Reservation;
import com.unibuc.cinema_booking.model.User;
import com.unibuc.cinema_booking.model.UserCategory;
import com.unibuc.cinema_booking.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private ReservationMapper reservationMapper;


    //other service
    private UserService userService;
    private TicketService ticketService;
    private UserCategoryService userCategoryService;

    @Autowired
    public void setServices(UserService userService, TicketService ticketService, UserCategoryService userCategoryService) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.userCategoryService = userCategoryService;
    }

    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper,
                              UserService userService, TicketService ticketService, UserCategoryService userCategoryService) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        setServices(userService, ticketService, userCategoryService);
    }

    public ReservationRequest create(ReservationRequest reservationRequest) {
        //map the request to entity
        Reservation reservation = reservationMapper.mapToEntity(reservationRequest);

        //save the entity to database
        Reservation savedReservation = reservationRepository.create(reservation);

        //map the entity back to request
        ReservationRequest reservationRequestSaved = reservationMapper.mapToDTO(savedReservation);

        return reservationRequestSaved;

    }

    public ReservationRequest createReservation(CreateReservationRequest createReservationRequest) {
        int discount = userService.getUserDiscount(createReservationRequest.getUserId());

        //ticket-ul a fost creat
        TicketRequest ticketRequest = ticketService.createTicketForReservation(createReservationRequest.getIdSeat(), createReservationRequest.getMovieShowtimeId(), discount);

        ReservationRequest reservationRequest = new ReservationRequest(createReservationRequest.getUserId(), ticketRequest.getId());

        return create(reservationRequest);
    }

    public List<ReservationRequest> getAll() {
        return reservationRepository.getAll()
                .stream()
                .map(reservation -> reservationMapper.mapToDTO(reservation))
                .collect(Collectors.toList());
    }

    public boolean delete(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.getAll()
                .stream()
                .filter(reservation -> reservation.getId() == id)
                .findAny();
        if (optionalReservation.isPresent()) {
            ReservationRequest reservationRequest = getOne(id);
            reservationRepository.delete(id);
            ticketService.delete(reservationRequest.getTicketId());
            return true;
        }
        return false;
    }

    public ReservationRequest getOne(Long id) {

        Optional<Reservation> optionalReservation = reservationRepository.getOne(id);
        if (optionalReservation.isPresent()) {
            return this.reservationMapper.mapToDTO(optionalReservation.get());
        } else {
            throw new EntityNotFoundException("Reservation", id);
        }
    }

    public List<ReservationsForUserResponse> getAllReservationForUser(Long id) {

        return reservationRepository.getAllReservationForAUser(id);
    }

    //return the number of updates lines
    public int confirmReservation(ConfirmRequest confirmRequest) {
        int rowsUpdated = reservationRepository.confirmReservation(confirmRequest.getIdReservation());

        ///Update user to get points for confirmations
        // Check if user will have enough points to up in rank
        if (rowsUpdated == 1) {
            UserRequest userRequest = userService.getOne(confirmRequest.getIdUser());
            int newPoints = (int) (userRequest.getPoints() + 20);
            int rowsUpdatedPoints = userService.updateUserPoints(newPoints, confirmRequest.getIdUser());
            UserCategoryRequest userCategoryRequest = userCategoryService.getOne(userRequest.getUserCategoryId());
            if (rowsUpdatedPoints == 1 && newPoints > userCategoryRequest.getMinimPoints()) {
                if (userCategoryRequest.getId() < 5) {
                    UpdateUserCategoryRequest updateUserCategoryRequest = new UpdateUserCategoryRequest(userRequest.getId(), userCategoryRequest.getId() + 1);
                    int updatedUserCategoryForUser = userService.updateCategoryUser(updateUserCategoryRequest);
                    return updatedUserCategoryForUser;
                }
            }
        }
        throw new RuntimeException("Nu s-a putut realiza update-ul");

    }
}