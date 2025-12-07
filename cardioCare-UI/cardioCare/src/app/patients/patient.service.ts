import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http'; // <--- IMPORTA HttpParams
import { Observable } from 'rxjs';
import { Patient, PageResponse } from '../models/patient'; // <--- IMPORTA PageResponse

@Injectable({ providedIn: 'root' })
export class PatientService {
  private api = 'http://localhost:8080/api/pacientes';

  constructor(private http: HttpClient) {}

  // Modificamos el método para recibir página y tamaño
  getAll(page: number, size: number): Observable<PageResponse<Patient>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    // Ahora esperamos un PageResponse, no un array directo
    return this.http.get<PageResponse<Patient>>(this.api, { params });
  }

  getById(id: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.api}/${id}`);
  }
}
