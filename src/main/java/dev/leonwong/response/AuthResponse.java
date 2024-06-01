package dev.leonwong.response;

import dev.leonwong.model.UserRole;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;

    private String message;

    private UserRole role;
}
