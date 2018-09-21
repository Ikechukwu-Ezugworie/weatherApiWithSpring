package com.bw.weatherApi.weatherApi.Exceptions;

public class BooknotFoundException extends RuntimeException {

    private String message;

    public BooknotFoundException(String message){
        this.message = message;

    }


    public String getMessage(){
       return this.message;
    }








}
