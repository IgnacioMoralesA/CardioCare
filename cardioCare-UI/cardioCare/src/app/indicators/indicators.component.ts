import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common'; // Importante para fechas
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IndicatorService } from './services/indicator.service';
import { IndicatorResponse } from './models/indicator.model';

@Component({
  selector: 'app-indicators',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule], // No olvides importar CommonModule
  providers: [DatePipe],
  templateUrl: './indicators.component.html',
  styleUrls: ['./indicators.component.css']
})
export class IndicatorsComponent implements OnInit {

  indicatorForm: FormGroup;
  indicators: IndicatorResponse[] = [];
  loading = false;

  // Simulamos el ID del paciente actual (debería venir del Login o Ruta)
  currentPatientId = 1;

  // Tipos de indicadores predefinidos
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
    private indicatorService: IndicatorService
  ) {
    this.indicatorForm = this.fb.group({
      type: ['', Validators.required],
      value: ['', [Validators.required, Validators.min(0)]],
      unit: [{value: '', disabled: true}] // Solo lectura, se llena auto
    });
  }

  ngOnInit(): void {
    // Si tienes el ID guardado en localStorage úsalo:
    const storedId = localStorage.getItem('userId');
    if(storedId) this.currentPatientId = Number(storedId);

    this.loadIndicators();

    // Lógica para cambiar la unidad automáticamente al seleccionar tipo
    this.indicatorForm.get('type')?.valueChanges.subscribe(selectedType => {
      const typeObj = this.indicatorTypes.find(t => t.value === selectedType);
      if (typeObj) {
        // Usamos patchValue para actualizar la unidad visualmente
        this.indicatorForm.patchValue({ unit: typeObj.unit });
      }
    });
  }

  loadIndicators(): void {
    this.loading = true;
    this.indicatorService.getByPatient(this.currentPatientId).subscribe({
      next: (data) => {
        // Ordenamos por fecha descendente (más nuevo primero)
        this.indicators = data.sort((a, b) =>
          new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime()
        );
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando indicadores', err);
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.indicatorForm.invalid) return;

    // Buscamos la unidad correcta basada en el tipo seleccionado
    const selectedTypeVal = this.indicatorForm.get('type')?.value;
    const typeObj = this.indicatorTypes.find(t => t.value === selectedTypeVal);

    const newIndicator = {
      patientId: this.currentPatientId,
      type: selectedTypeVal,
      value: this.indicatorForm.get('value')?.value,
      unit: typeObj ? typeObj.unit : 'unit'
    };

    this.indicatorService.create(newIndicator).subscribe({
      next: (res) => {
        // Agregamos al inicio de la lista
        this.indicators.unshift(res);
        // Reset form pero mantenemos el tipo si se quiere agregar otro seguido
        this.indicatorForm.get('value')?.reset();
      },
      error: (err) => console.error('Error guardando indicador', err)
    });
  }

  // Helper para mostrar nombres bonitos en la tabla
  getTypeLabel(typeValue: string): string {
    const found = this.indicatorTypes.find(t => t.value === typeValue);
    return found ? found.label : typeValue;
  }
}
