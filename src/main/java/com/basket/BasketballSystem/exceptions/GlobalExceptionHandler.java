package com.basket.BasketballSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<List<ErrorResponse>> handleBadRequestException(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        List<ErrorResponse> errorResponses = new ArrayList<>();
        errorResponses.add(errorResponse);
        return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ErrorResponse>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorResponses.add(new ErrorResponse( fieldError.getDefaultMessage()));
        }

        return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<List<ErrorResponse>> handleAuthenticationException(BadCredentialsException ex) {

        ErrorResponse errorResponse = new ErrorResponse("Usuario o contraseña incorrectos");
        List<ErrorResponse> errorResponses = new ArrayList<>();
        errorResponses.add(errorResponse);
        return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);

    }

}
