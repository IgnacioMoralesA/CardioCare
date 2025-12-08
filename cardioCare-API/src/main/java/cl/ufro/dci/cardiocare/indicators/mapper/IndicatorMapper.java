package cl.ufro.dci.cardiocare.indicators.mapper;

import cl.ufro.dci.cardiocare.indicators.domain.Indicator;
import cl.ufro.dci.cardiocare.indicators.dto.IndicatorDTO;
import org.springframework.stereotype.Component;

@Component
public class IndicatorMapper {

    public IndicatorDTO toDTO(Indicator i) {
        return IndicatorDTO.builder()
                .id(i.getId())
                .medicalRecordId(i.getMedicalRecord().getId())
                .patientId(i.getMedicalRecord().getPatientId())
                .type(i.getType())
                .value(i.getValue())
                .unit(i.getUnit())
                .timestamp(i.getTimestamp())
                .build();
    }
}

