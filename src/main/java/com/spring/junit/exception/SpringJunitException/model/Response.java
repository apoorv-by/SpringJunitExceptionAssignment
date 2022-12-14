package com.spring.junit.exception.SpringJunitException.model;

public class Response {

    private int status;

    private String message;

    public Response(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
