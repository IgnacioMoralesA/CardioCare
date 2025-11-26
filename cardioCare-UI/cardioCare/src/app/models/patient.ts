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


