import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MedicalRecordRequest, MedicalRecordResponse } from '../models/medical-record.model';

@Injectable({
  providedIn: 'root'
})
export class MedicalRecordService {

  private apiUrl = 'http://localhost:8080/api/v1/medical-records';

  constructor(private http: HttpClient) { }

  create(record: MedicalRecordRequest): Observable<MedicalRecordResponse> {
    return this.http.post<MedicalRecordResponse>(this.apiUrl, record);
  }

  getByPatient(patientId: number): Observable<MedicalRecordResponse[]> {
    return this.http.get<MedicalRecordResponse[]>(`${this.apiUrl}/patient/${patientId}`);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
