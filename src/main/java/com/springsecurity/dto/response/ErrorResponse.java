package com.springsecurity.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

public class ErrorResponse {
    public String status;
    public HttpStatus code;
    public String message;

    public ErrorResponse status(String status) {
        this.status = status;
        return this;
    }

    public ErrorResponse code(HttpStatus code) {
        this.code = code;
        return this;
    }

    public ErrorResponse message(String message) {
        this.message = message;
        return this;
    }

    public String convertToJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
