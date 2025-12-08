import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  PatientProgressDTO,
  DashboardItemResponse,
  DashboardItemRequest
} from '../models/dashboard.model';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private apiUrl = 'http://localhost:8080/api/v1/dashboard';

  constructor(private http: HttpClient) { }

  // --- ANALYTICS ---
  getPatientProgress(patientId: number): Observable<PatientProgressDTO> {
    return this.http.get<PatientProgressDTO>(`${this.apiUrl}/analytics/patient/${patientId}`);
  }

  // --- DASHBOARD ITEMS (WIDGETS) ---
  createItem(request: DashboardItemRequest): Observable<DashboardItemResponse> {
    return this.http.post<DashboardItemResponse>(this.apiUrl, request);
  }

  getItemsByOwner(ownerId: number): Observable<DashboardItemResponse[]> {
    return this.http.get<DashboardItemResponse[]>(`${this.apiUrl}/owner/${ownerId}`);
  }

  deleteItem(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
