package com.assesment.task_project.customException;

public class JwtTokenInvalidException extends RuntimeException{
    public JwtTokenInvalidException(String message) {
        super(message);
    }
}
