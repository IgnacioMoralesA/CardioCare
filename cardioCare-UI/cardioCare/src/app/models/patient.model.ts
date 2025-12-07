export interface PatientRequest {
  userId: number;           // ID del usuario registrado
  gender: string;
  birthDate: string;        // YYYY-MM-DD
  surgeryDate?: string;     // YYYY-MM-DD (Opcional)
  medicalCondition: string;
}

export interface PatientResponse {
  id: number;
  name: string;
  email: string;
  gender: string;
  birthDate: string;
  surgeryDate: string;
  medicalCondition: string;
}
