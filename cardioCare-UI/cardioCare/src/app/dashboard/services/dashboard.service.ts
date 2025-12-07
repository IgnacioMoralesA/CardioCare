import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DashboardItemRequest, DashboardItemResponse } from '../models/dashboard.model';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private apiUrl = 'http://localhost:8080/api/v1/dashboard';

  constructor(private http: HttpClient) { }

  // Crear un nuevo widget
  create(item: DashboardItemRequest): Observable<DashboardItemResponse> {
    return this.http.post<DashboardItemResponse>(this.apiUrl, item);
  }

  // Obtener widgets por ID de dueño
  getByOwner(ownerId: number): Observable<DashboardItemResponse[]> {
    return this.http.get<DashboardItemResponse[]>(`${this.apiUrl}/owner/${ownerId}`);
  }

  // Eliminar widget
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
