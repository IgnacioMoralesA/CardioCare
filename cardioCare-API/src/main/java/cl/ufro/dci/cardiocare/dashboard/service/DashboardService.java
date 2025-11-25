package cl.ufro.dci.cardiocare.dashboard.service;

public interface DashboardService {
    long getPacientesActivos();
    long getConsultasPendientes();
    double getSatisfaccionGeneral();
    long getAlertasCriticas();
}
