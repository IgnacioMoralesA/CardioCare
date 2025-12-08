import { RouterModule } from '@angular/router';
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';

// CORRECCIÓN 1: Rutas relativas.
// Si el servicio está en 'src/app/medical-record/services/', usa './' no '../'
import { MedicalRecordService } from './services/medical-record.service';

// Si moviste el modelo a la carpeta compartida 'src/app/models/', usa '../models/'
// Si sigue en 'src/app/medical-record/models/', usa './models/'
// Aquí asumo que está en la carpeta compartida 'src/app/models/'
import { MedicalRecordResponse } from "./models/medical-record.model";

@Component({
  selector: 'app-medical-record',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './medical-record.component.html',
  styleUrls: ['./medical-record.component.css']
})
export class MedicalRecordComponent implements OnChanges {

  @Input() patientId: number | null = null;

  recordForm: FormGroup;
  records: MedicalRecordResponse[] = [];
  loading = false;
  currentUserEmail = '';
  userRole = '';

  constructor(
    private fb: FormBuilder,
    private recordService: MedicalRecordService // Esto fallaba porque la importación de arriba estaba mal
  ) {
    const today = new Date().toISOString().substring(0, 10);

    this.recordForm = this.fb.group({
      recordDate: [today, Validators.required],
      description: ['', [Validators.required, Validators.minLength(10)]],
      recommendations: [''],
    });

    this.extractUserInfoFromToken();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['patientId'] && this.patientId) {
      this.loadRecords();
    }
  }

  extractUserInfoFromToken(): void {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        this.currentUserEmail = payload.sub;
        this.userRole = payload.role || '';
      } catch (e) {
        console.error('Error decodificando token', e);
      }
    }
  }

  loadRecords(): void {
    if (!this.patientId) return;

    this.loading = true;
    this.recordService.getByPatient(this.patientId).subscribe({
      // CORRECCIÓN 2: Tipos explícitos (data: MedicalRecordResponse[])
      next: (data: MedicalRecordResponse[]) => {
        // CORRECCIÓN 3: Tipos en el sort ((a: any, b: any) o tipado correcto)
        this.records = data.sort((a: MedicalRecordResponse, b: MedicalRecordResponse) =>
          new Date(b.recordDate).getTime() - new Date(a.recordDate).getTime()
        );
        this.loading = false;
      },
      // CORRECCIÓN 4: Tipo para error (err: any)
      error: (err: any) => {
        console.error('Error cargando historial', err);
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.recordForm.invalid || !this.patientId) return;

    const request = {
      patientId: this.patientId,
      createdBy: this.currentUserEmail,
      ...this.recordForm.value
    };

    this.recordService.create(request).subscribe({
      // CORRECCIÓN 5: Tipo explícito para la respuesta
      next: (res: MedicalRecordResponse) => {
        alert('Ficha guardada correctamente');
        this.records.unshift(res);
        this.recordForm.patchValue({ description: '', recommendations: '' });
      },
      error: (err: any) => {
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
      error: (err: any) => alert('No tienes permisos de Administrador.')
    });
  }
}
