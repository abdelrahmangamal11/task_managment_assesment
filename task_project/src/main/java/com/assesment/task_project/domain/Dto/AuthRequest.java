package com.assesment.task_project.domain.Dto;

public class AuthRequest {
    public record LoginRequest(
            String email,
            String password
    ){}
}


