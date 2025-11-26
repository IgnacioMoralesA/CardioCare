import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessageService } from './message.service';
import { Message } from '../models/message';

@Component({
  selector: 'app-message-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './message-list.component.html'
})
export class MessageListComponent implements OnInit {
  messages: Message[] = [];
  loading: boolean = true;
  errorMessage: string = '';

  constructor(private msgService: MessageService) {}

  ngOnInit(): void {
    this.loading = true;
    this.msgService.getAll().subscribe({
      next: (data) => {
        this.messages = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Error al cargar los mensajes';
        this.loading = false;
      }
    });
  }
}

