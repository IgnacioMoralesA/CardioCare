import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConsultationService } from '../services/consultation.service';
import { MessageService } from '../../messages/services/message.service'; // <--- IMPORTAR
import { ConsultationResponse } from '../../models/consultation.model';
import { MessageResponse } from '../../messages/models/message.model'; // <--- IMPORTAR

@Component({
  selector: 'app-consultation-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  providers: [DatePipe],
  templateUrl: './consultation-list.component.html',
  styleUrls: ['./consultation-list.component.css']
})
export class ConsultationListComponent implements OnInit {

  // Listas
  consultations: ConsultationResponse[] = [];
  chatMessages: MessageResponse[] = []; // <--- MENSAJES DEL CHAT

  // Formularios
  createForm: FormGroup;
  chatForm: FormGroup; // <--- FORMULARIO PARA CHATEAR

  // Estado
  role: string = '';
  currentUserId: number = 0;
  selectedConsultation: ConsultationResponse | null = null;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private consultationService: ConsultationService,
    private messageService: MessageService // <--- INYECTAR SERVICIO
  ) {
    // Formulario Nueva Consulta
    this.createForm = this.fb.group({
      medicId: [''],
      priority: ['MEDIUM', Validators.required],
      message: ['', [Validators.required, Validators.minLength(10)]]
    });

    // Formulario de Chat (Nuevo mensaje)
    this.chatForm = this.fb.group({
      content: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.role = localStorage.getItem('role') || '';
    this.extractUserFromToken();
    this.loadConsultations();
  }

  private extractUserFromToken(): void {
    const storedId = localStorage.getItem('userId');
    if (storedId) this.currentUserId = Number(storedId);
  }

  loadConsultations(): void {
    this.loading = true;
    const fetch$ = (this.role === 'ROLE_MEDIC')
      ? this.consultationService.getByMedic(this.currentUserId)
      : this.consultationService.getByPatient(this.currentUserId);

    fetch$.subscribe({
      next: (data) => {
        this.consultations = data.sort((a, b) => new Date(b.sentAt).getTime() - new Date(a.sentAt).getTime());
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }

  // --- SELECCIONAR CONSULTA Y CARGAR CHAT ---
  selectConsultation(c: ConsultationResponse): void {
    this.selectedConsultation = c;
    this.chatMessages = []; // Limpiar chat anterior

    // Cargar mensajes de esta consulta específica
    this.messageService.getByConsultation(c.id).subscribe({
      next: (msgs) => {
        // Orden cronológico para el chat
        this.chatMessages = msgs.sort((a, b) => new Date(a.sentAt).getTime() - new Date(b.sentAt).getTime());
        this.scrollToBottom();
      }
    });
  }

  // --- ENVIAR MENSAJE AL CHAT ---
  sendMessage(): void {
    if (this.chatForm.invalid || !this.selectedConsultation) return;

    // Lógica para determinar el destinatario (Partner)
    // Si soy el paciente, le escribo al médico asignado. Si soy médico, al paciente.
    // Nota: Para que esto funcione perfecto, el backend debería retornar ambos IDs en ConsultationResponse.
    // Asumiremos que ConsultationResponse tiene patientId y medicId.

    let receiverId = 0;
    if (this.currentUserId === this.selectedConsultation.patientId) {
      receiverId = this.selectedConsultation.medicId;
    } else {
      receiverId = this.selectedConsultation.patientId;
    }

    const req = {
      senderId: this.currentUserId,
      receiverId: receiverId,
      consultationId: this.selectedConsultation.id,
      content: this.chatForm.value.content
    };

    this.messageService.send(req).subscribe({
      next: (res) => {
        this.chatMessages.push(res); // Agregar visualmente
        this.chatForm.reset();
        this.scrollToBottom();
      },
      error: () => alert('Error al enviar mensaje')
    });
  }

  // Crear Consulta (Solo Paciente)
  createConsultation(): void {
    if (this.createForm.invalid) return;
    const request = { patientId: this.currentUserId, ...this.createForm.value };
    if (!request.medicId) delete request.medicId;

    this.consultationService.create(request).subscribe({
      next: (res) => {
        this.consultations.unshift(res);
        this.createForm.reset({ priority: 'MEDIUM' });
        this.selectConsultation(res); // Auto-seleccionar la creada
      }
    });
  }

  // Helpers
  getPriorityClass(p: string): string {
    switch(p) {
      case 'HIGH': return 'priority-high';
      case 'MEDIUM': return 'priority-medium';
      case 'LOW': return 'priority-low';
      default: return '';
    }
  }

  scrollToBottom(): void {
    setTimeout(() => {
      const el = document.getElementById('chatContainer');
      if (el) el.scrollTop = el.scrollHeight;
    }, 100);
  }
}
