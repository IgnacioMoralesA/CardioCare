package cl.ufro.dci.cardiocare.dashboard.dto;

import lombok.Data;

@Data
public class DashboardItemResponse {

    private Long id;
    private String widgetName;
    private String dataJson;
    private Long ownerId;
}
