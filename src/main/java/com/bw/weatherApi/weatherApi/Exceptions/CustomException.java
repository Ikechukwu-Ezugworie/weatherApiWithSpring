package com.bw.weatherApi.weatherApi.Exceptions;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private final String message;
    private final HttpStatus status;



    public CustomException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
