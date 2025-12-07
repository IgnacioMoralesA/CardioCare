export interface MedicRequest {
  userId: number;       // ID del usuario registrado previamente
  specialty: string;
  licenseNumber: string;
  scheduleJson: string; // Disponibilidad (texto o JSON)
}

export interface MedicResponse {
  id: number;
  name: string;
  email: string;
  specialty: string;
  licenseNumber: string;
  scheduleJson: string;
}
