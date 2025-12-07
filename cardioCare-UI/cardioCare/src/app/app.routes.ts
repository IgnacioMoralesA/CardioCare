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
// Importamos el componente de Indicadores
import { IndicatorsComponent } from './indicators/indicators.component';

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
  {
    path: 'indicators', // <--- Nueva ruta agregada
    component: IndicatorsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'messages',
    component: MessageListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'medics',
    component: MedicListComponent,
    canActivate: [AuthGuard] // Nota: Asegúrate que el usuario sea ADMIN para ver esto
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

  // --- WILDCARD ---
  { path: '**', redirectTo: 'dashboard' }
];
