package com.unibuc.cinema_booking.exception;

public class MethodNotImplementedException extends RuntimeException {
    public MethodNotImplementedException(String nameMethod)
    { super(String.format("the called method %s is not implemented because it is not necessary",nameMethod)); }
}
