import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessageService } from './message.service';
import { Message } from '../models/message';

@Component({
  selector: 'app-message-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.css']
})
export class MessageListComponent implements OnInit {

  // Mapa para agrupar: "Dr. House" -> [Mensaje1, Mensaje2]
  conversations = new Map<string, Message[]>();

  // Lista de nombres de los doctores/remitentes (para la barra lateral)
  senders: string[] = [];

  // Usuario seleccionado actualmente
  selectedSender: string | null = null;

  // Mensajes del usuario seleccionado
  currentMessages: Message[] = [];

  loading = true;

  constructor(private msgService: MessageService) {}

  ngOnInit(): void {
    this.msgService.getAll().subscribe(data => {
      this.groupMessages(data);
      this.loading = false;
    });
  }

  // Lógica para agrupar mensajes por Remitente
  groupMessages(allMessages: Message[]) {
    this.conversations.clear();

    allMessages.forEach(msg => {
      if (!this.conversations.has(msg.sender)) {
        this.conversations.set(msg.sender, []);
      }
      this.conversations.get(msg.sender)?.push(msg);
    });

    // Extraemos las llaves (nombres) para la lista lateral
    this.senders = Array.from(this.conversations.keys());

    // Opcional: Seleccionar el primero automáticamente
    if (this.senders.length > 0) {
      this.selectChat(this.senders[0]);
    }
  }

  // Al hacer click en un usuario de la izquierda
  selectChat(sender: string) {
    this.selectedSender = sender;
    this.currentMessages = this.conversations.get(sender) || [];
  }

  // Funciones visuales (colores e iniciales)
  getInitials(name: string): string {
    const parts = name.split(' ');
    return parts.length > 1 ? (parts[0][0] + parts[1][0]).toUpperCase() : name.substring(0, 2).toUpperCase();
  }

  getAvatarColor(name: string): string {
    let hash = 0;
    for (let i = 0; i < name.length; i++) hash = name.charCodeAt(i) + ((hash << 5) - hash);
    const c = (hash & 0x00FFFFFF).toString(16).toUpperCase();
    return '#' + '00000'.substring(0, 6 - c.length) + c;
  }
}
