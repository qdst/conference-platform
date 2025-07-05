package com.conference.platform.control.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(InvalidInputException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleException(InvalidInputException invalidInputException) {
    return ErrorResponse.builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .code(HttpStatus.BAD_REQUEST.name())
        .message(invalidInputException.getMessage())
        .build();
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleException(NotFoundException notFoundException) {
    return ErrorResponse.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .code(HttpStatus.NOT_FOUND.name())
        .message(notFoundException.getMessage())
        .build();
  }

  @ExceptionHandler(ConflictStateException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse handleException(ConflictStateException conflictStateException) {
    return ErrorResponse.builder()
        .status(HttpStatus.CONFLICT.value())
        .code(HttpStatus.CONFLICT.name())
        .message(conflictStateException.getMessage())
        .build();
  }


}
