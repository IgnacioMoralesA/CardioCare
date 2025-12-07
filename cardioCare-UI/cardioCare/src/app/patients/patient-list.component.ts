import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PatientService } from './patient.service';
import { Patient } from '../models/patient';

@Component({
  selector: 'app-patient-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './patient-list.component.html',
  // Si tienes CSS, asegúrate de vincularlo aquí
  styleUrls: ['./patient-list.component.css']
})
export class PatientListComponent implements OnInit {
  patients: Patient[] = [];

  // Variables de paginación
  currentPage: number = 0;
  pageSize: number = 5; // Debe coincidir con lo que quieras mostrar
  totalPages: number = 0;
  totalElements: number = 0;

  constructor(private patientService: PatientService) {}

  ngOnInit() {
    this.cargarPacientes();
  }

  cargarPacientes() {
    this.patientService.getAll(this.currentPage, this.pageSize).subscribe(data => {
      this.patients = data.content;       // <--- OJO: Extraemos 'content'
      this.totalPages = data.totalPages;  // Guardamos total de páginas
      this.totalElements = data.totalElements;
    });
  }

  // Método para los botones
  cambiarPagina(delta: number) {
    const nuevaPagina = this.currentPage + delta;
    if (nuevaPagina >= 0 && nuevaPagina < this.totalPages) {
      this.currentPage = nuevaPagina;
      this.cargarPacientes();
    }
  }
}
