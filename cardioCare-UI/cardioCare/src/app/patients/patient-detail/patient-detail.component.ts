import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { PatientService } from '../services/patient.service';
import { PatientResponse } from '../../models/patient.model';

// 1. Importamos el componente de ficha médica
import { MedicalRecordComponent } from '../../medical-record/medical-record.component'; // Ajusta la ruta

@Component({
  selector: 'app-patient-detail',
  standalone: true,
  // 2. Lo agregamos a los imports
  imports: [CommonModule, RouterModule, MedicalRecordComponent],
  templateUrl: './patient-detail.component.html',
  styleUrls: ['./patient-detail.component.css']
})
export class PatientDetailComponent implements OnInit {

  patient: PatientResponse | null = null;
  loading = true;
  error = '';
  patientId: number | null = null; // Guardamos el ID para pasarlo al hijo

  constructor(
    private route: ActivatedRoute,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.patientId = Number(idParam);
      this.loadPatient(this.patientId);
    } else {
      this.error = 'ID de paciente inválido.';
      this.loading = false;
    }
  }

  loadPatient(id: number): void {
    this.patientService.getById(id).subscribe({
      next: (data) => {
        this.patient = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error obteniendo paciente', err);
        this.error = 'No se pudo cargar la información del paciente.';
        this.loading = false;
      }
    });
  }

  getAge(birthDateString: string): number {
    const birthDate = new Date(birthDateString);
    const today = new Date();
    let age = today.getFullYear() - birthDate.getFullYear();
    const m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) age--;
    return age;
  }
}
