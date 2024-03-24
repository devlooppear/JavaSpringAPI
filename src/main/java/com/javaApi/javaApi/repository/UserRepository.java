package com.javaApi.javaApi.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import com.javaApi.javaApi.exception.ErrorResponse;
import com.javaApi.javaApi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Exception handling for specific exceptions
    @ExceptionHandler({DataIntegrityViolationException.class, EmptyResultDataAccessException.class}) 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    default ErrorResponse handleDataAccessException(DataAccessException ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
