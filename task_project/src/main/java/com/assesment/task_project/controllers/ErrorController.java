package com.assesment.task_project.controllers;

import com.assesment.task_project.customException.JwtTokenExpiredException;
import com.assesment.task_project.customException.JwtTokenInvalidException;
import com.assesment.task_project.domain.Dto.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ApiErrorResponse> globalException(Exception ex){
        log.error("Error Happend",ex);
        ApiErrorResponse apiErrorResponse=ApiErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An UnExcepected Error Occured")
                .build();

        return new  ResponseEntity<>(apiErrorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ApiErrorResponse> illegalArgumentExceptionHandling(IllegalArgumentException ex){
        ApiErrorResponse apiErrorResponse=ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return new  ResponseEntity<>(apiErrorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    private ResponseEntity<ApiErrorResponse> illegalStateExceptionHandling(IllegalStateException ex){
        ApiErrorResponse apiErrorResponse=ApiErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .build();

        return new  ResponseEntity<>(apiErrorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ApiErrorResponse> illegalStateExceptionHandling(BadCredentialsException ex){
        ApiErrorResponse apiErrorResponse=ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("incorrect username or password")
                .build();
        return new  ResponseEntity<>(apiErrorResponse,HttpStatus.UNAUTHORIZED);
    }



    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<ApiErrorResponse> handleTokenExpired(JwtTokenExpiredException ex) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Token has expired")
                .build();

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtTokenInvalidException.class)
    public ResponseEntity<ApiErrorResponse> handleTokenInvalid(JwtTokenInvalidException ex) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Invalid token")
                .build();

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message("Access denied: " + ex.getMessage())
                .build();

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.FORBIDDEN);
    }
}

