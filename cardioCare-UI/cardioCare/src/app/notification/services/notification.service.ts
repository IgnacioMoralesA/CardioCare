import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NotificationRequest, NotificationResponse } from '../../models/notification.model';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private apiUrl = 'http://localhost:8080/api/v1/notifications';

  constructor(private http: HttpClient) { }

  // Obtener notificaciones de un usuario
  getByUser(userId: number): Observable<NotificationResponse[]> {
    return this.http.get<NotificationResponse[]>(`${this.apiUrl}/user/${userId}`);
  }

  // Marcar como leída (PATCH)
  markAsRead(id: number): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}/read`, {});
  }

  // Enviar notificación (Generalmente esto lo hace el backend, pero útil para pruebas)
  send(req: NotificationRequest): Observable<NotificationResponse> {
    return this.http.post<NotificationResponse>(this.apiUrl, req);
  }
}
