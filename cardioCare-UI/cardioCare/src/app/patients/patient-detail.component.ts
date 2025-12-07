import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router'; // Agregamos RouterModule para el botón 'Volver'
import { CommonModule } from '@angular/common';
import { PatientService } from './patient.service';
import { Patient } from "../models/patient";
import { switchMap } from 'rxjs/operators';
import { of } from 'rxjs';

// Importamos librerías para PDF
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

@Component({
  selector: 'app-patient-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './patient-detail.component.html',
  styleUrls: ['./patient-detail.component.css']
})
export class PatientDetailComponent implements OnInit {
  patient?: Patient;
  loading: boolean = true;
  errorMessage: string = '';

  // Datos Falsos Simulados (Mock Data)
  clinicalData = {
    grupoSanguineo: 'O+',
    altura: '1.75 m',
    peso: '78 kg',
    alergias: 'Penicilina, Polvo',
    presionArterial: '120/80 mmHg',
    frecuenciaCardiaca: '72 bpm',
    diagnostico: 'Hipertensión Arterial Sistémica (HTA) grado 1.',
    tratamiento: 'Losartán 50mg cada 12 horas. Dieta baja en sodio y actividad física moderada 30 min diarios.',
    observaciones: 'Paciente estable, responde bien al tratamiento actual. Próximo control en 3 meses.'
  };

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
            this.patient = data;
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

  // --- GENERACIÓN DE PDF COMPLETO ---
  downloadClinicalPDF() {
    if (!this.patient) return;

    const doc = new jsPDF();
    const p = this.patient;
    const c = this.clinicalData;

    // 1. Encabezado
    doc.setFillColor(0, 119, 182); // Azul Médico
    doc.rect(0, 0, 210, 40, 'F');

    doc.setTextColor(255, 255, 255);
    doc.setFontSize(22);
    doc.text('Ficha Clínica Digital', 14, 25);
    doc.setFontSize(10);
    doc.text('CardioCare System - Informe Confidencial', 14, 32);

    // 2. Datos del Paciente (Real)
    doc.setTextColor(0, 0, 0);
    doc.setFontSize(14);
    doc.text('Información del Paciente', 14, 50);

    autoTable(doc, {
      startY: 55,
      head: [['Campo', 'Detalle']],
      body: [
        ['Nombre Completo', `${p.nombre} ${p.apellido}`],
        ['RUT', p.rut],
        ['Edad', `${p.edad} años`],
        ['Prioridad Asignada', p.prioridad],
        ['ID Sistema', p.id.toString()]
      ],
      theme: 'grid',
      headStyles: { fillColor: [72, 202, 228] } // Celeste claro
    });

    // 3. Datos Clínicos (Falsos/Simulados)
    const finalY = (doc as any).lastAutoTable.finalY + 15;
    doc.setFontSize(14);
    doc.text('Evaluación Médica Actual', 14, finalY);

    autoTable(doc, {
      startY: finalY + 5,
      body: [
        ['Signos Vitales', `PA: ${c.presionArterial} | FC: ${c.frecuenciaCardiaca} | Peso: ${c.peso}`],
        ['Grupo Sanguíneo', c.grupoSanguineo],
        ['Alergias', c.alergias],
        ['Diagnóstico', c.diagnostico],
        ['Tratamiento', c.tratamiento],
        ['Observaciones', c.observaciones]
      ],
      theme: 'striped',
      styles: { cellPadding: 4 },
      columnStyles: { 0: { fontStyle: 'bold', cellWidth: 50 } }
    });

    // 4. Pie de página
    doc.setFontSize(8);
    doc.setTextColor(150);
    doc.text(`Documento generado el ${new Date().toLocaleString()}`, 14, 280);

    doc.save(`Ficha_Clinica_${p.rut}.pdf`);
  }
}
