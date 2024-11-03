package com.project.SamServices.exceptions;

import org.springframework.web.bind.annotation.RestControllerAdvice;


public class BookingFailedException extends RuntimeException {
    public BookingFailedException(String message) {
        super(message);
    }

}
