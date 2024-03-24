package com.javaApi.javaApi.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private Date timestamp;
    private int status;
    private String error;
    private String message;
    private String details;

    public ErrorResponse(Date timestamp, int status, String error, String message, String details) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.details = details;
    }

    public ErrorResponse(HttpStatus internalServerError, String message2) {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
