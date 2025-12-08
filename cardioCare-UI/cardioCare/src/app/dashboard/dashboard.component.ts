import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { DashboardService } from './services/dashboard.service';
import { PatientService } from '../patients/services/patient.service'; // <--- NECESITAMOS ESTO
import {
  PatientProgressDTO,
  DashboardItemResponse,
  WidgetConfiguration
} from './models/dashboard.model';
import { PatientResponse } from '../models/patient.model'; // Asegúrate de tener este modelo

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  providers: [DatePipe],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  // Almacén de datos MULTI-PACIENTE
  // Clave: ID Paciente -> Valor: Sus datos clínicos
  dataCache: Map<number, PatientProgressDTO> = new Map();

  widgets: DashboardItemResponse[] = [];
  patientsList: PatientResponse[] = []; // Para el dropdown

  currentUserId = 0;

  builderForm: FormGroup;
  showBuilder = false;
  loading = true;

  availableMetrics = [
    { label: 'Ritmo Cardíaco', value: 'HEART_RATE' },
    { label: 'Presión Arterial', value: 'BLOOD_PRESSURE' },
    { label: 'Actividad Física', value: 'ACTIVITY' },
    { label: 'Peso', value: 'WEIGHT' }
  ];

  availableTypes = [
    { label: 'Tarjeta Dato Único', value: 'STAT_CARD' },
    { label: 'Gráfico de Barras', value: 'BAR_CHART' },
    { label: 'Progreso Circular', value: 'PROGRESS_CIRCLE' }
  ];

  constructor(
    private dashboardService: DashboardService,
    private patientService: PatientService, // Inyectamos servicio de pacientes
    private fb: FormBuilder,
    private route: ActivatedRoute
  ) {
    this.builderForm = this.fb.group({
      title: ['', Validators.required],
      metric: ['HEART_RATE', Validators.required],
      type: ['STAT_CARD', Validators.required],
      patientId: ['', Validators.required], // <--- Nuevo campo obligatorio
      color: ['#3498db']
    });
  }

  ngOnInit(): void {
    const storedId = localStorage.getItem('userId');
    if (storedId) this.currentUserId = Number(storedId);

    // 1. Cargar lista de pacientes para el select
    this.loadPatientsList();

    // 2. Cargar los widgets configurados por este médico
    this.loadWidgets();
  }

  loadPatientsList(): void {
    // Asumimos que existe un método getAll() o similar
    this.patientService.getAll().subscribe({
      next: (data) => this.patientsList = data
    });
  }

  loadWidgets(): void {
    this.dashboardService.getItemsByOwner(this.currentUserId).subscribe({
      next: (data) => {
        this.widgets = data.map(w => {
          try {
            w.parsedConfig = JSON.parse(w.dataJson);
          } catch (e) {
            w.parsedConfig = { type: 'STAT_CARD', metric: 'HEART_RATE', title: 'Error' };
          }
          return w;
        });

        // 3. INTELIGENCIA DE CARGA:
        // Recorremos los widgets y descargamos los datos de los pacientes que falten
        this.fetchMissingPatientData();

        this.loading = false;
      }
    });
  }

  fetchMissingPatientData(): void {
    // Identificar IDs únicos de pacientes en los widgets
    const uniquePatientIds = new Set<number>();
    this.widgets.forEach(w => {
      if (w.parsedConfig?.targetPatientId) {
        uniquePatientIds.add(w.parsedConfig.targetPatientId);
      }
    });

    // Descargar datos para cada ID si no está en caché
    uniquePatientIds.forEach(pid => {
      if (!this.dataCache.has(pid)) {
        this.dashboardService.getPatientProgress(pid).subscribe({
          next: (data) => this.dataCache.set(pid, data)
        });
      }
    });
  }

  addWidget(): void {
    if (this.builderForm.invalid) return;

    const formVal = this.builderForm.value;

    // Encontrar el nombre del paciente seleccionado para guardarlo también
    const selectedPatient = this.patientsList.find(p => p.id == formVal.patientId);
    const patientName = selectedPatient ? selectedPatient.name : 'Desconocido';

    const config: WidgetConfiguration = {
      title: formVal.title,
      metric: formVal.metric,
      type: formVal.type,
      color: formVal.color,
      targetPatientId: Number(formVal.patientId), // Guardamos ID
      targetPatientName: patientName              // Guardamos Nombre (para mostrar fácil)
    };

    const req = {
      widgetName: config.title,
      dataJson: JSON.stringify(config),
      ownerId: this.currentUserId
    };

    this.dashboardService.createItem(req).subscribe({
      next: (res) => {
        res.parsedConfig = config;
        this.widgets.push(res);
        this.fetchMissingPatientData(); // Cargar datos del nuevo paciente si falta
        this.showBuilder = false;
        this.builderForm.reset({ type: 'STAT_CARD', metric: 'HEART_RATE', color: '#3498db' });
      }
    });
  }

  deleteWidget(id: number): void {
    if(!confirm('¿Eliminar este widget?')) return;
    this.dashboardService.deleteItem(id).subscribe({
      next: () => this.widgets = this.widgets.filter(w => w.id !== id)
    });
  }

  // --- LÓGICA DE VISUALIZACIÓN MULTI-PACIENTE ---

  // Helper privado para buscar datos en la caché correcta
  private getDataForWidget(config?: WidgetConfiguration): PatientProgressDTO | undefined {
    if (!config || !config.targetPatientId) return undefined;
    return this.dataCache.get(config.targetPatientId);
  }

  getMetricValue(config?: WidgetConfiguration): string | number {
    const data = this.getDataForWidget(config);
    if (!data || !config) return 'Cargando...';

    if (config.metric === 'ACTIVITY') {
      return `${data.completedActivities}/${data.totalActivities}`;
    }

    const trend = data.vitalSigns.find(v => v.indicatorName.includes(config.metric));
    if (trend && trend.history.length > 0) {
      const last = trend.history[trend.history.length - 1];
      return `${last.value} ${trend.unit}`;
    }
    return 'N/A';
  }

  getMetricHistory(config?: WidgetConfiguration): any[] {
    const data = this.getDataForWidget(config);
    if (!data || !config || config.metric === 'ACTIVITY') return [];

    const trend = data.vitalSigns.find(v => v.indicatorName.includes(config.metric));
    return trend ? trend.history : [];
  }

  getAdherence(config?: WidgetConfiguration): number {
    const data = this.getDataForWidget(config);
    return data ? data.adherencePercentage : 0;
  }
}
