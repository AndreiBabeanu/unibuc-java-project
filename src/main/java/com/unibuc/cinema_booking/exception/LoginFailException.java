package com.unibuc.cinema_booking.exception;

public class LoginFailException extends RuntimeException {
    public LoginFailException(String message)
    { super(String.format("Login fail : %s", message)); }
}
