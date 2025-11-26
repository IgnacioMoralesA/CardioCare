import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PatientService } from './patient.service';
import { Patient } from "../models/patient";
import { switchMap } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-patient-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './patient-detail.component.html'
})
export class PatientDetailComponent implements OnInit {
  patient?: Patient;
  loading: boolean = true;
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        switchMap(params => {
          const id = Number(params.get('id'));
          if (isNaN(id)) {
            this.errorMessage = 'ID de paciente inválido';
            this.loading = false;
            return of<Patient | undefined>(undefined);
          }
          return this.patientService.getById(id);
        })
      )
      .subscribe({
        next: (data) => {
          if (data) {
            this.patient = data; // asignación directa
            console.log('Paciente cargado:', this.patient);
          } else {
            this.errorMessage = 'Paciente no encontrado';
          }
          this.loading = false;
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Error al cargar el paciente';
          this.loading = false;
        }
      });
  }
}
