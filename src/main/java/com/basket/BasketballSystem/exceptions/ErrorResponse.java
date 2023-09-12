package com.basket.BasketballSystem.exceptions;


public class ErrorResponse {
    private String message;

    private int status = 400;
    public ErrorResponse() {
    }
    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public int getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
