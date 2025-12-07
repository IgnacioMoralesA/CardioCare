export interface MedicalRecordRequest {
  patientId: number;
  recordDate: string; // Formato YYYY-MM-DD
  description: string;
  recommendations: string;
  createdBy: string; // Email o ID del doctor
}

export interface MedicalRecordResponse {
  id: number;
  patientId: number;
  recordDate: string;
  description: string;
  recommendations: string;
  createdBy: string;
  createdAt: string;
}
