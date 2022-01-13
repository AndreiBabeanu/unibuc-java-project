package com.unibuc.cinema_booking.exception;

public class EntityAlreadyExistException extends RuntimeException {
    public EntityAlreadyExistException(String nameEntity, String  columnName, String value) {
        super(String.format("A %s with the %s = %s already exist !",nameEntity,columnName,value));
    }
}
