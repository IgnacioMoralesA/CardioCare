import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppointmentRequest, AppointmentResponse, AppointmentUpdateStatusRequest } from '../../models/appointment.model';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private apiUrl = 'http://localhost:8080/api/v1/appointments';

  constructor(private http: HttpClient) { }

  // Crear cita
  create(appointment: AppointmentRequest): Observable<AppointmentResponse> {
    return this.http.post<AppointmentResponse>(this.apiUrl, appointment);
  }

  // Obtener por ID
  getById(id: number): Observable<AppointmentResponse> {
    return this.http.get<AppointmentResponse>(`${this.apiUrl}/${id}`);
  }

  // Listar citas de un paciente
  getByPatient(patientId: number): Observable<AppointmentResponse[]> {
    return this.http.get<AppointmentResponse[]>(`${this.apiUrl}/patient/${patientId}`);
  }

  // Listar agenda de un médico
  getByMedic(medicId: number): Observable<AppointmentResponse[]> {
    return this.http.get<AppointmentResponse[]>(`${this.apiUrl}/medic/${medicId}`);
  }

  // Cambiar estado (PATCH)
  updateStatus(id: number, status: string): Observable<AppointmentResponse> {
    const body: AppointmentUpdateStatusRequest = { status };
    return this.http.patch<AppointmentResponse>(`${this.apiUrl}/${id}/status`, body);
  }

  // Eliminar (Admin)
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
