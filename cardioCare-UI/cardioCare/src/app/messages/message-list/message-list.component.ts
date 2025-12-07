import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from '../services/message.service';
import { MessageResponse } from '../models/message.model';

@Component({
  selector: 'app-message-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  providers: [DatePipe],
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.css']
})
export class MessageListComponent implements OnInit {

  messageForm: FormGroup;

  // Listas de mensajes
  chatMessages: MessageResponse[] = [];
  inboxMessages: MessageResponse[] = [];

  activeTab: 'CHAT' | 'INBOX' = 'CHAT';

  // --- DATOS SIMULADOS (En realidad vienen del Login o Routing) ---
  currentUserId = 1;      // Yo (quien envía)
  targetReceiverId = 2;   // El Médico u otro usuario
  currentConsultationId = 100; // ID de la consulta médica actual
  // -------------------------------------------------------------

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService
  ) {
    this.messageForm = this.fb.group({
      content: ['', [Validators.required, Validators.minLength(1)]]
    });
  }

  ngOnInit(): void {
    // Cargar ambas listas al inicio
    this.loadChat();
    this.loadInbox();
  }

  // Carga la conversación específica
  loadChat(): void {
    this.messageService.getByConsultation(this.currentConsultationId).subscribe({
      next: (data) => {
        // Ordenamos cronológicamente para el chat
        this.chatMessages = data.sort((a, b) =>
          new Date(a.sentAt).getTime() - new Date(b.sentAt).getTime()
        );
        this.scrollToBottom();
      },
      error: (err) => console.error('Error cargando chat', err)
    });
  }

  // Carga mensajes recibidos generales
  loadInbox(): void {
    this.messageService.getInbox(this.currentUserId).subscribe({
      next: (data) => {
        // Ordenamos del más reciente al más antiguo
        this.inboxMessages = data.sort((a, b) =>
          new Date(b.sentAt).getTime() - new Date(a.sentAt).getTime()
        );
      }
    });
  }

  sendMessage(): void {
    if (this.messageForm.invalid) return;

    const req = {
      senderId: this.currentUserId,
      receiverId: this.targetReceiverId,
      consultationId: this.currentConsultationId,
      content: this.messageForm.value.content
    };

    this.messageService.send(req).subscribe({
      next: (res) => {
        // Agregamos el mensaje al chat visualmente
        this.chatMessages.push(res);
        this.messageForm.reset();
        this.scrollToBottom();
      },
      error: (err) => console.error('Error enviando mensaje', err)
    });
  }

  // Utilidad para bajar el scroll al último mensaje
  scrollToBottom(): void {
    setTimeout(() => {
      const container = document.getElementById('chatContainer');
      if (container) {
        container.scrollTop = container.scrollHeight;
      }
    }, 100);
  }
}
