import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MessageRequest, MessageResponse } from '../models/message.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private apiUrl = 'http://localhost:8080/api/v1/messages';

  constructor(private http: HttpClient) { }

  send(request: MessageRequest): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(this.apiUrl, request);
  }

  getByConsultation(id: number): Observable<MessageResponse[]> {
    return this.http.get<MessageResponse[]>(`${this.apiUrl}/consultation/${id}`);
  }

  getInbox(userId: number): Observable<MessageResponse[]> {
    return this.http.get<MessageResponse[]>(`${this.apiUrl}/inbox/${userId}`);
  }

  getSent(userId: number): Observable<MessageResponse[]> {
    return this.http.get<MessageResponse[]>(`${this.apiUrl}/sent/${userId}`);
  }
}
