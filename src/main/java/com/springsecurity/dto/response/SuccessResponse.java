package com.springsecurity.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

public class SuccessResponse {
    public String status;
    public HttpStatus code;
    public String message;
    public Object data;

    public SuccessResponse status(String status) {
        this.status = status;
        return this;
    }

    public SuccessResponse code(HttpStatus code) {
        this.code = code;
        return this;
    }

    public SuccessResponse message(String message) {
        this.message = message;
        return this;
    }

    public SuccessResponse data(Object data) {
        this.data = data;
        return this;
    }

    public Object getData() {
        return data;
    }

    public String convertToJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
