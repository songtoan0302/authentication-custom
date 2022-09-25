package org.aibles.authentication.controller.advice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.aibles.authentication.exception.BaseException;
import org.aibles.authentication.exception.UnauthorizedException;
import org.aibles.authentication.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(value = {BaseException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ExceptionResponse unauthorizedException(BaseException baseException) {
    ExceptionResponse response = new ExceptionResponse();
    response.setError(baseException.getCode());
    response.setMessage(baseException.getParams());
    response.setTimestamp(Instant.now());
    return response;
  }

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    exception.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

}
