package cl.ufro.dci.cardiocare.security.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String role;
}
