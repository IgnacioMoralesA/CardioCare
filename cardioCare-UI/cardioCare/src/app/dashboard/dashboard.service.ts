import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { KPIResponse } from '../models/kpi';

@Injectable({ providedIn: 'root' })
export class DashboardService {
  private api = 'http://localhost:8080/api/dashboard';

  constructor(private http: HttpClient) {}

  getKPIs(): Observable<KPIResponse> {
    return this.http.get<KPIResponse>(`${this.api}/kpis`);
  }
}

