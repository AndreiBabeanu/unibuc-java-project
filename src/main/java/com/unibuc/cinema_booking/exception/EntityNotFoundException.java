package com.unibuc.cinema_booking.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String nameEntity, long id) {
        super(String.format("The %s with id %d doesn't exist ", nameEntity,id));
    }

    public EntityNotFoundException(String nameEntity,String columnName, String name) {
        super(String.format("Entity: %s with %s = %s was not found", nameEntity,columnName,name));
    }
}
