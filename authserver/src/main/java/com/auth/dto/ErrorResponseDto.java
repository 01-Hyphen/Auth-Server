package com.auth.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Data
public class ErrorResponseDto {

    private String message;
    private HttpStatus httpStatus;
    private String path;

}
