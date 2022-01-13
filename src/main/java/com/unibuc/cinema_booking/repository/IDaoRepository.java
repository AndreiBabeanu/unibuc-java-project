package com.unibuc.cinema_booking.repository;

import java.util.List;
import java.util.Optional;

public interface IDaoRepository<T> {


    T create(T object);
    boolean delete(Long id);
    Optional<T> getOne(Long id);
    List<T> getAll();

}
