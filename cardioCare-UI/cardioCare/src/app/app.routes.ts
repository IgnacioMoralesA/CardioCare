import { Routes } from '@angular/router';

// 1. Importamos el Guard y los componentes de Autenticación
import { AuthGuard } from './security/guards/auth.guard';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';

// Componentes de la aplicación
import { DashboardComponent } from './dashboard/dashboard.component';
import { MessageListComponent } from './messages/message-list/message-list.component';
import { MedicListComponent } from './medics/medic-list/medic-list.component';
import { MedicalRecordComponent } from './medical-record/medical-record.component';
import { PatientListComponent } from './patients/patient-list/patient-list.component';
import { PatientDetailComponent } from './patients/patient-detail/patient-detail.component';
import { AppointmentListComponent } from './appointments/appointment-list/appointment-list.component';
import { IndicatorsComponent } from './indicators/indicators.component';

// --- NUEVO: Importamos el componente de Consultas ---
import { ConsultationListComponent } from './consultations/consultation-list/consultation-list.component';

export const routes: Routes = [
  // --- RUTAS PÚBLICAS (No requieren token) ---
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // --- RUTAS PROTEGIDAS (Requieren Token) ---
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard]
  },
  // NUEVA RUTA: Dashboard de un paciente específico (Para médicos)
  {
    path: 'dashboard/:patientId',
    component: DashboardComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'indicators',
    component: IndicatorsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'messages',
    component: MessageListComponent,
    canActivate: [AuthGuard]
  },
  // --- NUEVA RUTA DE CONSULTAS MÉDICAS ---
  {
    path: 'consultations',
    component: ConsultationListComponent,
    canActivate: [AuthGuard]
  },
  // ----------------------------------------
  {
    path: 'medics',
    component: MedicListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'medical-records',
    component: MedicalRecordComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'patients',
    component: PatientListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'patients/:id',
    component: PatientDetailComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'appointments',
    component: AppointmentListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'medical-records/:recordId/indicators',
    component: IndicatorsComponent,
    canActivate: [AuthGuard]
  },

  // --- WILDCARD ---
  { path: '**', redirectTo: 'dashboard' }
];
