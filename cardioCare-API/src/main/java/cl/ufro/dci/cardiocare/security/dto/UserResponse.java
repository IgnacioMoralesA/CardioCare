package cl.ufro.dci.cardiocare.security.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
}
