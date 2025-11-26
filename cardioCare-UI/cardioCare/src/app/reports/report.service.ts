import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Report } from '../models/report';

@Injectable({ providedIn: 'root' })
export class ReportService {
  private api = 'http://localhost:8080/api/reportes';

  constructor(private http: HttpClient) {}

  // Debe apuntar al endpoint correcto
  getAll(): Observable<Report[]> {
    return this.http.get<Report[]>(`${this.api}/pacientes`);
  }
}
