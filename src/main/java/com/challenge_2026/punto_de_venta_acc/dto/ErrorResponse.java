package com.challenge_2026.punto_de_venta_acc.dto;

public class ErrorResponse {

    String message;

    String description;

    public ErrorResponse() {}

    public ErrorResponse(String message, String description) {
        this.message = message;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
