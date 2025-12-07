import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IndicatorRequest, IndicatorResponse } from '../models/indicator.model';

@Injectable({
  providedIn: 'root'
})
export class IndicatorService {

  private apiUrl = 'http://localhost:8080/api/v1/indicators';

  constructor(private http: HttpClient) { }

  create(indicator: IndicatorRequest): Observable<IndicatorResponse> {
    return this.http.post<IndicatorResponse>(this.apiUrl, indicator);
  }

  getByPatient(patientId: number): Observable<IndicatorResponse[]> {
    return this.http.get<IndicatorResponse[]>(`${this.apiUrl}/patient/${patientId}`);
  }
}
