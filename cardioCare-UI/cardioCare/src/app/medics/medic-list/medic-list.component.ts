import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MedicService } from '../services/medic.service';
import { MedicResponse } from '../models/medic.model';

@Component({
  selector: 'app-medic-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './medic-list.component.html',
  styleUrls: ['./medic-list.component.css']
})
export class MedicListComponent implements OnInit {

  medics: MedicResponse[] = [];
  medicForm: FormGroup;
  loading = false;
  errorMessage = '';

  constructor(
    private medicService: MedicService,
    private fb: FormBuilder
  ) {
    this.medicForm = this.fb.group({
      userId: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
      specialty: ['', Validators.required],
      licenseNumber: ['', Validators.required],
      scheduleJson: ['Lunes a Viernes: 09:00 - 18:00', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadMedics();
  }

  loadMedics(): void {
    this.loading = true;
    this.medicService.getAll().subscribe({
      next: (data) => {
        this.medics = data;
        this.loading = false;
      },
      error: (err) => {
        // Si el error es 403, es porque el usuario no es ADMIN
        console.error('Error cargando médicos', err);
        this.errorMessage = 'No tienes permisos para ver la lista de médicos o hubo un error.';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.medicForm.invalid) return;

    this.medicService.create(this.medicForm.value).subscribe({
      next: (res) => {
        alert('Médico registrado exitosamente');
        this.medics.push(res); // Actualizar lista localmente
        this.medicForm.reset({ scheduleJson: 'Lunes a Viernes: 09:00 - 18:00' });
      },
      error: (err) => {
        console.error(err);
        alert('Error al crear perfil. Verifica que el ID del usuario exista y no sea médico aún.');
      }
    });
  }

  deleteMedic(id: number): void {
    if (!confirm('¿Estás seguro de eliminar el perfil de este médico? El usuario base permanecerá.')) return;

    this.medicService.delete(id).subscribe({
      next: () => {
        this.medics = this.medics.filter(m => m.id !== id);
      },
      error: (err) => alert('Error al eliminar médico.')
    });
  }
}
