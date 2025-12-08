import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  ConsultationRequest,
  ConsultationResponse,
  ConsultationReplyRequest
} from '../../models/consultation.model';

@Injectable({
  providedIn: 'root'
})
export class ConsultationService {

  private apiUrl = 'http://localhost:8080/api/v1/consultations';

  constructor(private http: HttpClient) { }

  create(req: ConsultationRequest): Observable<ConsultationResponse> {
    return this.http.post<ConsultationResponse>(this.apiUrl, req);
  }

  reply(id: number, req: ConsultationReplyRequest): Observable<ConsultationResponse> {
    return this.http.post<ConsultationResponse>(`${this.apiUrl}/${id}/reply`, req);
  }

  getById(id: number): Observable<ConsultationResponse> {
    return this.http.get<ConsultationResponse>(`${this.apiUrl}/${id}`);
  }

  getByPatient(patientId: number): Observable<ConsultationResponse[]> {
    return this.http.get<ConsultationResponse[]>(`${this.apiUrl}/patient/${patientId}`);
  }

  getByMedic(medicId: number): Observable<ConsultationResponse[]> {
    return this.http.get<ConsultationResponse[]>(`${this.apiUrl}/medic/${medicId}`);
  }
}
