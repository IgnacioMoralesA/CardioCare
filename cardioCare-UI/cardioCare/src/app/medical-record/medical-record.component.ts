import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MedicalRecordService } from './services/medical-record.service';
import { MedicalRecordResponse } from './models/medical-record.model';

@Component({
  selector: 'app-medical-record',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './medical-record.component.html',
  styleUrls: ['./medical-record.component.css']
})
export class MedicalRecordComponent implements OnInit {

  recordForm: FormGroup;
  records: MedicalRecordResponse[] = [];
  loading = false;

  // IMPORTANTE: En una app real, esto viene de la ruta (ActivatedRoute)
  currentPatientId = 1;

  // Usuario logueado (Doctor)
  currentUserEmail = 'medico@hospital.com';

  constructor(
    private fb: FormBuilder,
    private recordService: MedicalRecordService
  ) {
    // Inicializamos el formulario
    // recordDate se inicializa con la fecha de hoy
    const today = new Date().toISOString().substring(0, 10);

    this.recordForm = this.fb.group({
      recordDate: [today, Validators.required],
      description: ['', [Validators.required, Validators.minLength(10)]],
      recommendations: [''],
    });
  }

  ngOnInit(): void {
    // Intentar recuperar el usuario real si lo guardaste en el login
    // const user = JSON.parse(localStorage.getItem('user') || '{}');
    // if(user.email) this.currentUserEmail = user.email;

    this.loadRecords();
  }

  loadRecords(): void {
    this.loading = true;
    this.recordService.getByPatient(this.currentPatientId).subscribe({
      next: (data) => {
        // Ordenamos: el más reciente primero
        this.records = data.sort((a, b) =>
          new Date(b.recordDate).getTime() - new Date(a.recordDate).getTime()
        );
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando historial', err);
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.recordForm.invalid) return;

    const request = {
      patientId: this.currentPatientId,
      createdBy: this.currentUserEmail,
      ...this.recordForm.value
    };

    this.recordService.create(request).subscribe({
      next: (res) => {
        alert('Ficha guardada correctamente');
        this.records.unshift(res); // Agregamos al inicio de la lista visual
        // Limpiamos solo los campos de texto, mantenemos la fecha
        this.recordForm.patchValue({
          description: '',
          recommendations: ''
        });
      },
      error: (err) => {
        console.error(err);
        alert('Error al guardar. Verifica que tengas rol de MÉDICO.');
      }
    });
  }

  deleteRecord(id: number): void {
    if(!confirm('¿Eliminar este registro? Solo un ADMIN puede hacer esto.')) return;

    this.recordService.delete(id).subscribe({
      next: () => {
        this.records = this.records.filter(r => r.id !== id);
      },
      error: (err) => alert('No tienes permisos de Administrador para eliminar fichas.')
    });
  }
}
