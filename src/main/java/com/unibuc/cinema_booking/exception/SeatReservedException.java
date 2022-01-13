package com.unibuc.cinema_booking.exception;

public class SeatReservedException extends RuntimeException {
    public SeatReservedException(int number)
    { super(String.format("The seat with the number %d is already reserved",number)); }
}
