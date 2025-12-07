export interface IndicatorRequest {
  patientId: number;
  type: string; // Ej: HEART_RATE, BLOOD_PRESSURE
  value: number;
  unit: string; // Ej: bpm, mmHg
}

export interface IndicatorResponse {
  id: number;
  patientId: number;
  type: string;
  value: number;
  timestamp: string; // Formato ISO fecha
  unit: string;
}
