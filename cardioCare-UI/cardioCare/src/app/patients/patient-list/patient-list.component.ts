import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PatientService } from '../services/patient.service';
import { PatientResponse } from '../../models/patient.model';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-patient-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.css']
})
export class PatientListComponent implements OnInit {

  patients: PatientResponse[] = [];
  patientForm: FormGroup;
  loading = false;
  errorMessage = '';

  constructor(
    private patientService: PatientService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.patientForm = this.fb.group({
      userId: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
      gender: ['', Validators.required],
      birthDate: ['', Validators.required],
      surgeryDate: [''], // Opcional
      medicalCondition: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadPatients();
  }

  loadPatients(): void {
    this.loading = true;
    this.patientService.getAll().subscribe({
      next: (data) => {
        this.patients = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando pacientes', err);
        // Manejo específico si el usuario es MEDIC (tu backend bloquea getAll para medic)
        if (err.status === 403) {
          this.errorMessage = 'Acceso denegado: Solo administradores pueden ver la lista completa.';
        } else {
          this.errorMessage = 'Error al cargar la lista de pacientes.';
        }
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.patientForm.invalid) return;

    this.patientService.create(this.patientForm.value).subscribe({
      next: (res) => {
        alert('Ficha de paciente creada exitosamente.');
        this.patients.push(res);
        this.patientForm.reset();
      },
      error: (err) => {
        console.error(err);
        alert('Error al crear ficha. Verifica que el ID de usuario exista.');
      }
    });
  }

  deletePatient(id: number): void {
    if(!confirm('¿Eliminar la ficha clínica de este paciente?')) return;

    this.patientService.delete(id).subscribe({
      next: () => {
        this.patients = this.patients.filter(p => p.id !== id);
      },
      error: (err) => alert('Error al eliminar paciente.')
    });
  }

  // Navegar al detalle (Indicadores o Historial)
  goToDetail(id: number): void {
    this.router.navigate(['/patients', id]);
  }
}
