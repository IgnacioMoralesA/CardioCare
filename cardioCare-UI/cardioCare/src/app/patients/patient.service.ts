import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Patient } from '../models/patient';

@Injectable({ providedIn: 'root' })
export class PatientService {
  private api = 'http://localhost:8080/api/pacientes';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Patient[]> {
    return this.http.get<Patient[]>(this.api);
  }

  getById(id: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.api}/${id}`);
  }
}
