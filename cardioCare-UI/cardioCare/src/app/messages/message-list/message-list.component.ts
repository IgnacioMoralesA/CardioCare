import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from '../services/message.service';
import { MessageResponse, ChatThread } from '../models/message.model';
import { forkJoin } from 'rxjs'; // Importante para unir peticiones

@Component({
  selector: 'app-message-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.css']
})
export class MessageListComponent implements OnInit {

  currentUserId: number = 0;

  // Listas de datos
  threads: ChatThread[] = [];       // Lista para el sidebar
  activeThread: ChatThread | null = null; // Chat abierto actualmente

  // Formularios
  chatForm: FormGroup;
  newChatForm: FormGroup;

  // Control de vista
  showNewChatModal = false;

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService
  ) {
    this.chatForm = this.fb.group({ content: ['', Validators.required] });

    this.newChatForm = this.fb.group({
      receiverId: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
      consultationId: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
      content: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.extractUserFromToken();

    // Solo cargamos mensajes si logramos obtener un ID válido
    if (this.currentUserId && this.currentUserId !== 0) {
      this.loadAllMessages();
    }
  }

  // --- CORRECCIÓN: Leer ID directamente del localStorage ---
  private extractUserFromToken(): void {
    const storedId = localStorage.getItem('userId');

    if (storedId) {
      this.currentUserId = Number(storedId);
      console.log('✅ ID de usuario cargado:', this.currentUserId);
    } else {
      console.error('❌ No se encontró "userId" en el localStorage. Revisa el Login.');
    }
  }

  // --- LÓGICA PRINCIPAL DE AGRUPACIÓN ---
  loadAllMessages(): void {
    if (!this.currentUserId) return;

    // Pedimos Inbox y Sent simultáneamente
    forkJoin({
      inbox: this.messageService.getInbox(this.currentUserId),
      sent: this.messageService.getSent(this.currentUserId)
    }).subscribe({
      next: (result) => {
        // 1. Unimos todo en un solo array
        const all = [...result.inbox, ...result.sent];

        // 2. Agrupamos por ConsultationID
        const groups = new Map<number, MessageResponse[]>();
        all.forEach(msg => {
          if (!groups.has(msg.consultationId)) groups.set(msg.consultationId, []);
          groups.get(msg.consultationId)?.push(msg);
        });

        // 3. Convertimos el Mapa a un Array de "Hilos" (ChatThread)
        this.threads = Array.from(groups.keys()).map(cid => {
          const msgs = groups.get(cid) || [];

          // Ordenar mensajes cronológicamente (viejo -> nuevo)
          msgs.sort((a, b) => new Date(a.sentAt).getTime() - new Date(b.sentAt).getTime());

          const lastMsg = msgs[msgs.length - 1];

          // Determinar quién es el "otro" usuario (Partner)
          const partner = (lastMsg.senderId === this.currentUserId)
            ? lastMsg.receiverId
            : lastMsg.senderId;

          return {
            consultationId: cid,
            partnerId: partner,
            lastMessage: lastMsg.content,
            lastDate: new Date(lastMsg.sentAt),
            messages: msgs
          };
        });

        // 4. Ordenar los hilos: El que tenga el mensaje más reciente va primero
        this.threads.sort((a, b) => b.lastDate.getTime() - a.lastDate.getTime());

        // Si hay un chat abierto, actualizamos sus datos en tiempo real
        if (this.activeThread) {
          const updated = this.threads.find(t => t.consultationId === this.activeThread?.consultationId);
          if (updated) {
            this.activeThread = updated;
            this.scrollToBottom();
          }
        }
      },
      error: (err) => console.error('Error cargando mensajes', err)
    });
  }

  selectThread(thread: ChatThread): void {
    this.activeThread = thread;
    this.showNewChatModal = false;
    this.scrollToBottom();
  }

  sendMessage(): void {
    if (this.chatForm.invalid || !this.activeThread) return;

    const req = {
      senderId: this.currentUserId,
      receiverId: this.activeThread.partnerId,
      consultationId: this.activeThread.consultationId,
      content: this.chatForm.value.content
    };

    this.messageService.send(req).subscribe({
      next: () => {
        this.chatForm.reset();
        this.loadAllMessages(); // Recargar para ver el mensaje nuevo
      }
    });
  }

  startNewChat(): void {
    if (this.newChatForm.invalid) return;
    const val = this.newChatForm.value;

    const req = {
      senderId: this.currentUserId,
      receiverId: Number(val.receiverId),
      consultationId: Number(val.consultationId),
      content: val.content
    };

    this.messageService.send(req).subscribe({
      next: () => {
        this.newChatForm.reset();
        this.showNewChatModal = false;
        this.loadAllMessages(); // Recargar para ver el nuevo hilo
      },
      error: () => alert('Error al enviar. Verifica los IDs.')
    });
  }

  scrollToBottom(): void {
    setTimeout(() => {
      const el = document.getElementById('chatHistory');
      if (el) el.scrollTop = el.scrollHeight;
    }, 100);
  }
}
