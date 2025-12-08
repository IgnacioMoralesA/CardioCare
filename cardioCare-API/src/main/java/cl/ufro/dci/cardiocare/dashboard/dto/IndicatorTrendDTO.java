package cl.ufro.dci.cardiocare.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndicatorTrendDTO {
    private String indicatorName;
    private String unit;
    private List<GraphDataPoint> history;
}
