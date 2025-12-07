import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MedicRequest, MedicResponse } from '../models/medic.model';

@Injectable({
  providedIn: 'root'
})
export class MedicService {

  private apiUrl = 'http://localhost:8080/api/v1/medics';

  constructor(private http: HttpClient) { }

  // Obtener todos (Solo Admin)
  getAll(): Observable<MedicResponse[]> {
    return this.http.get<MedicResponse[]>(this.apiUrl);
  }

  // Obtener por ID
  getById(id: number): Observable<MedicResponse> {
    return this.http.get<MedicResponse>(`${this.apiUrl}/${id}`);
  }

  // Crear perfil médico (Asociar a usuario existente)
  create(medic: MedicRequest): Observable<MedicResponse> {
    return this.http.post<MedicResponse>(this.apiUrl, medic);
  }

  // Eliminar perfil médico
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
