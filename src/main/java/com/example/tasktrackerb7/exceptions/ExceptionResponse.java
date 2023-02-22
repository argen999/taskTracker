package com.example.tasktrackerb7.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionResponse extends Throwable {

    private HttpStatus httpStatus;
    private String exceptionName;
    private String message;

    public ExceptionResponse(HttpStatus httpStatus, String exceptionName, String message) {
        this.httpStatus = httpStatus;
        this.exceptionName = exceptionName;
        this.message = message;
    }
}
