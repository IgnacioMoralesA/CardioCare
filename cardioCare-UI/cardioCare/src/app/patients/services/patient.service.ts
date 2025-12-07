import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PatientRequest, PatientResponse } from '../../models/patient.model';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private apiUrl = 'http://localhost:8080/api/v1/patients';

  constructor(private http: HttpClient) { }

  // Obtener todos (Requiere ADMIN según tu controller)
  getAll(): Observable<PatientResponse[]> {
    return this.http.get<PatientResponse[]>(this.apiUrl);
  }

  // Obtener por ID
  getById(id: number): Observable<PatientResponse> {
    return this.http.get<PatientResponse>(`${this.apiUrl}/${id}`);
  }

  // Crear ficha de paciente (Para usuario existente)
  create(patient: PatientRequest): Observable<PatientResponse> {
    return this.http.post<PatientResponse>(this.apiUrl, patient);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
