package cl.ufro.dci.cardiocare.dashboard.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DashboardItemRequest {

    @NotBlank
    private String widgetName;

    @NotBlank
    private String dataJson;

    @NotNull
    private Long ownerId;
}
