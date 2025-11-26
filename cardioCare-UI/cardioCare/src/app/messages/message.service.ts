import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Message } from '../models/message';

@Injectable({ providedIn: 'root' })
export class MessageService {
  private api = 'http://localhost:8080/api/mensajes';

  constructor(private http: HttpClient) {}

  // Obtener todos los mensajes
  getAll(): Observable<Message[]> {
    return this.http.get<any[]>(this.api).pipe(
      map(messages =>
        messages.map(msg => ({
          id: msg.id,
          content: msg.contenido,
          sender: msg.asunto,
          sentAt: msg.fecha,          // LocalDateTime en ISO string
          tag: msg.tags?.[0] || ''    // solo el primer tag, si existe
        }))
      )
    );
  }

  // Obtener un mensaje por ID
  getById(id: number): Observable<Message> {
    return this.http.get<any>(`${this.api}/${id}`).pipe(
      map(msg => ({
        id: msg.id,
        content: msg.contenido,
        sender: msg.asunto,
        sentAt: msg.fecha,
        tag: msg.tags?.[0] || ''
      }))
    );
  }
}
