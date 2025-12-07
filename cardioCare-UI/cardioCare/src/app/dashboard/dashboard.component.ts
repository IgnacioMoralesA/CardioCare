import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DashboardService } from './services/dashboard.service';
import { DashboardItemResponse } from './models/dashboard.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  dashboardItems: DashboardItemResponse[] = [];
  widgetForm: FormGroup;
  loading = false;
  currentUserId: number = 1; // IMPORTANTE: Esto debe venir de tu AuthService o LocalStorage

  constructor(
    private dashboardService: DashboardService,
    private fb: FormBuilder
  ) {
    this.widgetForm = this.fb.group({
      widgetName: ['', Validators.required],
      // Validamos que sea un JSON válido o texto simple
      dataJson: ['{"heartRate": 80, "pressure": "120/80"}', Validators.required]
    });
  }

  ngOnInit(): void {
    // Intentar obtener el ID real del usuario (lógica temporal)
    const storedId = localStorage.getItem('userId');
    if (storedId) {
      this.currentUserId = Number(storedId);
    }

    this.loadDashboard();
  }

  loadDashboard(): void {
    this.loading = true;
    this.dashboardService.getByOwner(this.currentUserId).subscribe({
      next: (data) => {
        this.dashboardItems = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando dashboard:', err);
        this.loading = false;
      }
    });
  }

  createWidget(): void {
    if (this.widgetForm.invalid) return;

    const newItem = {
      widgetName: this.widgetForm.value.widgetName,
      dataJson: this.widgetForm.value.dataJson,
      ownerId: this.currentUserId
    };

    this.dashboardService.create(newItem).subscribe({
      next: (res) => {
        // Agregamos el nuevo item a la lista visualmente
        this.dashboardItems.push(res);
        this.widgetForm.reset({ dataJson: '{}' }); // Limpiar formulario
      },
      error: (err) => console.error('Error creando widget:', err)
    });
  }

  deleteWidget(id: number): void {
    if(!confirm('¿Estás seguro de eliminar este widget?')) return;

    this.dashboardService.delete(id).subscribe({
      next: () => {
        // Filtramos la lista para quitar el elemento eliminado
        this.dashboardItems = this.dashboardItems.filter(item => item.id !== id);
      },
      error: (err) => console.error('Error eliminando widget:', err)
    });
  }
}
