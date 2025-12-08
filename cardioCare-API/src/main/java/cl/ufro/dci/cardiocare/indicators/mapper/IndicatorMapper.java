package cl.ufro.dci.cardiocare.indicators.mapper;

import org.springframework.stereotype.Component;

@Component
public class IndicatorMapper {

    public cl.ufro.dci.cardiocare.indicators.dto.IndicatorDTO toDTO(cl.ufro.dci.cardiocare.indicators.domain.Indicator i) {
        cl.ufro.dci.cardiocare.medicalRecord.domain.MedicalRecord mr = i.getMedicalRecord();
        cl.ufro.dci.cardiocare.patient.domain.Patient p = mr.getPatient();

        return cl.ufro.dci.cardiocare.indicators.dto.IndicatorDTO.builder()
                .id(i.getId())
                .medicalRecordId(mr.getId())
                .patientId(p.getId())
                .type(i.getType())
                .value(i.getValue())
                .unit(i.getUnit())
                .timestamp(i.getTimestamp())
                .build();
    }
}
