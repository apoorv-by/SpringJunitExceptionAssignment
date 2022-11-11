package com.spring.junit.exception.SpringJunitException.exceptionHandling;

public class MovieException extends Exception{

    public static final long serialVersionID = 1L;
    private String errorMessage;

    public MovieException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }

}
