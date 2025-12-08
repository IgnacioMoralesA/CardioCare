import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IndicatorService } from './services/indicator.service';
import { IndicatorResponse } from './models/indicator.model';
// 1. IMPORTAR ActivatedRoute para leer la URL
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-indicators',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  providers: [DatePipe],
  templateUrl: './indicators.component.html',
  styleUrls: ['./indicators.component.css']
})
export class IndicatorsComponent implements OnInit {

  indicatorForm: FormGroup;
  indicators: IndicatorResponse[] = [];
  loading = false;

  currentPatientId = 1;

  // 2. NUEVA VARIABLE: Para guardar el ID de la consulta si existe
  currentRecordId: number | null = null;

  indicatorTypes = [
    { label: 'Ritmo Cardíaco', value: 'HEART_RATE', unit: 'bpm' },
    { label: 'Presión Sistólica', value: 'BLOOD_PRESSURE_SYS', unit: 'mmHg' },
    { label: 'Presión Diastólica', value: 'BLOOD_PRESSURE_DIA', unit: 'mmHg' },
    { label: 'Saturación Oxígeno', value: 'SPO2', unit: '%' },
    { label: 'Temperatura', value: 'TEMPERATURE', unit: '°C' },
    { label: 'Peso', value: 'WEIGHT', unit: 'kg' }
  ];

  constructor(
    private fb: FormBuilder,
    private indicatorService: IndicatorService,
    // 3. INYECTAR ActivatedRoute
    private route: ActivatedRoute
  ) {
    this.indicatorForm = this.fb.group({
      type: ['', Validators.required],
      value: ['', [Validators.required, Validators.min(0)]],
      unit: [{value: '', disabled: true}]
    });
  }

  ngOnInit(): void {
    const storedId = localStorage.getItem('userId');
    if(storedId) this.currentPatientId = Number(storedId);

    // 4. LÓGICA DE DETECCIÓN: ¿Venimos de una ficha médica?
    const recordIdParam = this.route.snapshot.paramMap.get('recordId');

    if (recordIdParam) {
      // MODO FILTRADO: Cargamos solo los de esta consulta
      this.currentRecordId = Number(recordIdParam);
      this.loadIndicatorsByRecord(this.currentRecordId);
    } else {
      // MODO NORMAL: Cargamos todo el historial del paciente
      this.loadIndicators();
    }

    this.indicatorForm.get('type')?.valueChanges.subscribe(selectedType => {
      const typeObj = this.indicatorTypes.find(t => t.value === selectedType);
      if (typeObj) {
        this.indicatorForm.patchValue({ unit: typeObj.unit });
      }
    });
  }

  // Carga normal (Todos los del paciente)
  loadIndicators(): void {
    this.loading = true;
    this.indicatorService.getByPatient(this.currentPatientId).subscribe({
      next: (data) => {
        this.indicators = this.sortData(data);
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando indicadores', err);
        this.loading = false;
      }
    });
  }

  // 5. NUEVO MÉTODO: Carga filtrada por Ficha Médica
  loadIndicatorsByRecord(recordId: number): void {
    this.loading = true;
    // Asegúrate de que tu IndicatorService tenga el método 'getByMedicalRecord'
    this.indicatorService.getByMedicalRecord(recordId).subscribe({
      next: (data) => {
        this.indicators = this.sortData(data); // Reutilizamos la lógica de orden
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando indicadores de la consulta', err);
        this.loading = false;
      }
    });
  }

  // Helper para ordenar fechas (reutilizable)
  private sortData(data: IndicatorResponse[]): IndicatorResponse[] {
    return data.sort((a, b) =>
      new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime()
    );
  }

  onSubmit(): void {
    if (this.indicatorForm.invalid) return;

    const selectedTypeVal = this.indicatorForm.get('type')?.value;
    const typeObj = this.indicatorTypes.find(t => t.value === selectedTypeVal);

    const newIndicator = {
      patientId: this.currentPatientId,
      // 6. ENVIAR ID DE CONSULTA: Si existe, lo asociamos
      medicalRecordId: this.currentRecordId ? this.currentRecordId : null,
      type: selectedTypeVal,
      value: this.indicatorForm.get('value')?.value,
      unit: typeObj ? typeObj.unit : 'unit'
    };

    this.indicatorService.create(newIndicator).subscribe({
      next: (res) => {
        this.indicators.unshift(res);
        this.indicatorForm.get('value')?.reset();
      },
      error: (err) => console.error('Error guardando indicador', err)
    });
  }

  getTypeLabel(typeValue: string): string {
    const found = this.indicatorTypes.find(t => t.value === typeValue);
    return found ? found.label : typeValue;
  }
}
