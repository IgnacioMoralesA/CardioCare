package cl.ufro.dci.cardiocare.medic.dto;

import lombok.Data;

@Data
public class MedicResponse {
    private Long id;
    private String name;
    private String email;
    private String specialty;
    private String licenseNumber;
    private String scheduleJson;
}
