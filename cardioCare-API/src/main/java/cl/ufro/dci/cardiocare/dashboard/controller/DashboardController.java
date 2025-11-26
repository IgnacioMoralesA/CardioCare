package cl.ufro.dci.cardiocare.dashboard.controller;

import cl.ufro.dci.cardiocare.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    @GetMapping("/kpis")
    public Map<String, Object> getKpis() {
        Map<String, Object> result = new HashMap<>();
        result.put("pacientesActivos", service.getPacientesActivos());
        result.put("consultasPendientes", service.getConsultasPendientes());
        result.put("satisfaccion", service.getSatisfaccionGeneral());
        result.put("alertasCriticas", service.getAlertasCriticas());
        return result;
    }
}

