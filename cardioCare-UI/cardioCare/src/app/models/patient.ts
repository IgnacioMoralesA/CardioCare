import { MedicalRecord } from './medical-record.model';

export interface Patient {
  id: number;
  rut: string;
  nombre: string;
  apellido: string;
  edad: number;
  prioridad: 'Alta' | 'Media' | 'Baja';
  historial: MedicalRecord[];
}

// AGREGA ESTO AL FINAL DEL ARCHIVO
export interface PageResponse<T> {
  content: T[];          // Aquí vienen tus pacientes
  totalPages: number;    // Total de páginas
  totalElements: number; // Total de registros
  size: number;
  number: number;
}

