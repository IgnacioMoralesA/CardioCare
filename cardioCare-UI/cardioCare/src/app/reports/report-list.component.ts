import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportService } from './report.service';
import { Report } from '../models/report';

// Importamos las librerías de PDF
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

@Component({
  selector: 'app-report',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './report-list.component.html',
  styleUrls: ['./report-list.component.css'] // Asegúrate de crear este archivo
})
export class ReportComponent implements OnInit {

  allReports: Report[] = [];      // Mantiene TODOS los datos originales
  filteredReports: Report[] = []; // Mantiene lo que se ve en pantalla

  loading = true;
  errorMessage = '';
  currentFilter: string = 'Completo'; // Para saber qué título poner al PDF

  constructor(private reportService: ReportService) {}

  ngOnInit(): void {
    this.reportService.getAll().subscribe({
      next: (data) => {
        this.allReports = data;
        this.filteredReports = data; // Al inicio mostramos todo
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Error al cargar los datos del reporte';
        this.loading = false;
      }
    });
  }

  // 1. Lógica de Filtrado
  filterBy(priority: string | null): void {
    if (priority === null) {
      this.filteredReports = this.allReports;
      this.currentFilter = 'Completo';
    } else {
      this.filteredReports = this.allReports.filter(r => r.prioridad === priority);
      this.currentFilter = `Prioridad ${priority}`;
    }
  }

  // 2. Lógica de Generación de PDF
  downloadPDF(): void {
    const doc = new jsPDF();

    // Título del PDF
    doc.setFontSize(18);
    doc.text(`Reporte de Pacientes - ${this.currentFilter}`, 14, 20);

    doc.setFontSize(12);
    doc.text(`Fecha de emisión: ${new Date().toLocaleDateString()}`, 14, 30);

    // Definimos las columnas y filas para la tabla del PDF
    const head = [['ID', 'RUT', 'Nombre', 'Apellido', 'Edad', 'Prioridad']];
    const data = this.filteredReports.map(r => [
      r.id,
      r.rut,
      r.nombre,
      r.apellido,
      r.edad,
      r.prioridad
    ]);

    // Generamos la tabla
    autoTable(doc, {
      startY: 40,
      head: head,
      body: data,
      theme: 'grid',
      headStyles: { fillColor: [0, 119, 182] } // Color azul médico
    });

    // Guardar archivo
    doc.save(`reporte_${this.currentFilter.toLowerCase().replace(' ', '_')}.pdf`);
  }
}
