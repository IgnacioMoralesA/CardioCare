// Coincide con GraphDataPoint
export interface GraphDataPoint {
  date: string; // LocalDate llega como string ISO
  value: number;
}

// Coincide con IndicatorTrendDTO
export interface IndicatorTrendDTO {
  indicatorName: string;
  unit: string;
  history: GraphDataPoint[];
}

// Coincide con PatientProgressDTO
export interface PatientProgressDTO {
  patientId: number;
  totalActivities: number;
  completedActivities: number;
  adherencePercentage: number;
  nextAppointment: string; // LocalDateTime llega como string ISO
  vitalSigns: IndicatorTrendDTO[];
}
export interface WidgetConfiguration {
  type: 'STAT_CARD' | 'BAR_CHART' | 'PROGRESS_CIRCLE';
  metric: 'HEART_RATE' | 'BLOOD_PRESSURE' | 'ACTIVITY' | 'WEIGHT';
  title: string;
  color?: string;
  // --- NUEVOS CAMPOS ---
  targetPatientId?: number;
  targetPatientName?: string;
}

// ... Resto de interfaces igual
// Coincide con DashboardItemRequest
export interface DashboardItemRequest {
  widgetName: string;
  dataJson: string;
  ownerId: number;
}

export interface DashboardItemResponse {
  id: number;
  widgetName: string;
  dataJson: string;
  ownerId: number;

  // --- AGREGAR ESTA LÍNEA (Con el signo de interrogación) ---
  parsedConfig?: WidgetConfiguration;
}
