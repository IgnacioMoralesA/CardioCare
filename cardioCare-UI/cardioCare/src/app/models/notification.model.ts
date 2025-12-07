export interface NotificationRequest {
  userId: number;
  type: string; // INFO, ALERT, WARNING, SUCCESS
  message: string;
  channel: string; // IN_APP
  referenceType?: string; // Ej: APPOINTMENT
  referenceId?: number;
}

export interface NotificationResponse {
  id: number;
  userId: number;
  type: string;
  message: string;
  sentAt: string; // ISO Date
  readFlag: boolean;
  channel: string;
  referenceType?: string;
  referenceId?: number;
}
