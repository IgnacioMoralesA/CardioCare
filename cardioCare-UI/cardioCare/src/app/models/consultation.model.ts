export interface ConsultationRequest {
  patientId: number;
  medicId?: number; // Opcional
  message: string;
  priority: 'LOW' | 'MEDIUM' | 'HIGH';
}

export interface ConsultationReplyRequest {
  response: string;
}

export interface ConsultationResponse {
  id: number;
  patientId: number;
  medicId: number;
  message: string;
  priority: string;
  status: 'OPEN' | 'RESPONDED' | 'CLOSED';
  medicResponse?: string;
  sentAt: string;     // ISO Date
  respondedAt?: string; // ISO Date
}
