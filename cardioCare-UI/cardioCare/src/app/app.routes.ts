import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PatientListComponent } from './patients/patient-list.component';
import { PatientDetailComponent } from './patients/patient-detail.component';
import { MessageListComponent } from './messages/message-list.component';
import { ReportComponent } from './reports/report-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },

  { path: 'patients', component: PatientListComponent },
  { path: 'patients/:id', component: PatientDetailComponent },

  { path: 'messages', component: MessageListComponent },

  { path: 'reports', component: ReportComponent },

  { path: '**', redirectTo: 'dashboard' }
];
