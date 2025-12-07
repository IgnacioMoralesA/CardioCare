package cl.ufro.dci.cardiocare.security.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @Size(min = 6)
    @NotBlank
    private String password;

    @NotBlank
    private String role; // ROLE_ADMIN, ROLE_PATIENT, ROLE_MEDIC, ROLE_FAMILY
}
