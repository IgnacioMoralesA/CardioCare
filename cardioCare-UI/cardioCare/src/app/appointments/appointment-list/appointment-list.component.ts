import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AppointmentService } from '../services/appointment.service';
import { AppointmentResponse } from '../../models/appointment.model';

@Component({
  selector: 'app-appointment-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  providers: [DatePipe],
  templateUrl: './appointment-list.component.html',
  styleUrls: ['./appointment-list.component.css']
})
export class AppointmentListComponent implements OnInit {

  appointments: AppointmentResponse[] = [];
  appointmentForm: FormGroup;

  loading = false;
  role: string = '';
  currentUserId: number = 0;

  constructor(
    private appointmentService: AppointmentService,
    private fb: FormBuilder
  ) {
    this.appointmentForm = this.fb.group({
      medicId: ['', Validators.required], // Idealmente sería un Select cargando médicos
      dateTime: ['', Validators.required],
      modality: ['PRESENTIAL', Validators.required],
      notes: ['']
    });
  }

  ngOnInit(): void {
    // Recuperar datos del usuario logueado
    this.role = localStorage.getItem('role') || '';
    const storedId = localStorage.getItem('userId');
    if (storedId) this.currentUserId = Number(storedId);

    this.loadAppointments();
  }

  loadAppointments(): void {
    this.loading = true;

    // Lógica para cargar según rol
    if (this.role === 'ROLE_MEDIC') {
      // Si soy médico, cargo MI agenda
      // NOTA: Asumimos que el ID del usuario logueado es el mismo ID del médico en la tabla Appointment
      // En un sistema real, habría que buscar el ID del médico asociado al usuario.
      // Para este ejemplo usaremos currentUserId directo.
      this.appointmentService.getByMedic(this.currentUserId).subscribe(this.handleData());

    } else {
      // Si soy paciente (o admin), cargo mis citas
      this.appointmentService.getByPatient(this.currentUserId).subscribe(this.handleData());
    }
  }

  private handleData() {
    return {
      next: (data: AppointmentResponse[]) => {
        // Ordenar por fecha
        this.appointments = data.sort((a, b) => new Date(a.dateTime).getTime() - new Date(b.dateTime).getTime());
        this.loading = false;
      },
      error: (err: any) => {
        console.error(err);
        this.loading = false;
      }
    };
  }

  // Crear Cita (Solo Pacientes)
  scheduleAppointment(): void {
    if (this.appointmentForm.invalid) return;

    // Formatear fecha a ISO String para ZonedDateTime
    // El input datetime-local devuelve "2023-10-10T10:00", Java necesita esa estructura
    const rawDate = this.appointmentForm.get('dateTime')?.value;
    const isoDate = new Date(rawDate).toISOString();

    const request = {
      patientId: this.currentUserId,
      ...this.appointmentForm.value,
      dateTime: isoDate // Reemplazamos con la fecha formateada
    };

    this.appointmentService.create(request).subscribe({
      next: (res) => {
        alert('Cita agendada correctamente');
        this.appointments.push(res);
        this.appointmentForm.reset({ modality: 'PRESENTIAL' });
      },
      error: (err) => alert('Error al agendar cita.')
    });
  }

  // Cambiar Estado (Para Médicos)
  changeStatus(appointment: AppointmentResponse, newStatus: string): void {
    if(!confirm(`¿Cambiar estado a ${newStatus}?`)) return;

    this.appointmentService.updateStatus(appointment.id, newStatus).subscribe({
      next: (res) => {
        // Actualizar en la lista local
        appointment.status = res.status;
      },
      error: (err) => alert('Error actualizando estado')
    });
  }

  // Helpers visuales
  getStatusClass(status: string): string {
    switch(status) {
      case 'CONFIRMED': return 'badge-success';
      case 'CANCELLED': return 'badge-danger';
      case 'COMPLETED': return 'badge-info';
      default: return 'badge-warning'; // PENDING
    }
  }

  getStatusLabel(status: string): string {
    const map: {[key: string]: string} = {
      'PENDING': 'Pendiente',
      'CONFIRMED': 'Confirmada',
      'CANCELLED': 'Cancelada',
      'COMPLETED': 'Realizada'
    };
    return map[status] || status;
  }
}
