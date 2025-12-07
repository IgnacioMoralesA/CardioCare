export interface AppointmentRequest {
  patientId: number;
  medicId: number;
  dateTime: string; // ISO 8601 (YYYY-MM-DDTHH:mm:ss)
  modality: string; // PRESENTIAL, VIRTUAL
  notes: string;
}

export interface AppointmentResponse {
  id: number;
  patientId: number;
  medicId: number;
  dateTime: string;
  status: string; // PENDING, CONFIRMED, CANCELLED, COMPLETED
  modality: string;
  notes: string;
}

export interface AppointmentUpdateStatusRequest {
  status: string;
}
