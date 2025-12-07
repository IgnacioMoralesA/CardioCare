import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { NotificationService } from './services/notification.service';
import { NotificationResponse } from '../models/notification.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [CommonModule],
  providers: [DatePipe],
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit {

  notifications: NotificationResponse[] = [];
  unreadCount = 0;
  isOpen = false; // Controla si el menú está abierto o cerrado

  // ID del usuario actual (tomado de localStorage)
  currentUserId: number = 0;

  constructor(
    private notificationService: NotificationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const storedId = localStorage.getItem('userId');

    // VALIDACIÓN: Si no hay ID o es 'undefined', no hacemos nada
    if (!storedId || storedId === 'undefined') {
      console.warn('No hay usuario logueado, omitiendo carga de notificaciones.');
      this.notifications = [];
      return;
    }

    this.currentUserId = Number(storedId);
    this.loadNotifications();
  }

  loadNotifications(): void {
    this.notificationService.getByUser(this.currentUserId).subscribe({
      next: (data) => {
        // Ordenamos: más recientes primero
        this.notifications = data.sort((a, b) =>
          new Date(b.sentAt).getTime() - new Date(a.sentAt).getTime()
        );
        this.updateUnreadCount();
      },
      error: (err) => console.error('Error cargando notificaciones', err)
    });
  }

  toggleDropdown(): void {
    this.isOpen = !this.isOpen;
  }

  // Acción al hacer clic en una notificación
  onNotificationClick(notification: NotificationResponse): void {
    // 1. Si no está leída, marcarla en el backend
    if (!notification.readFlag) {
      this.notificationService.markAsRead(notification.id).subscribe({
        next: () => {
          notification.readFlag = true; // Actualizar localmente
          this.updateUnreadCount();
        }
      });
    }

    // 2. Si tiene referencia, navegar (Ej: ir al detalle de la cita)
    if (notification.referenceType === 'APPOINTMENT' && notification.referenceId) {
      // this.router.navigate(['/appointments', notification.referenceId]);
      console.log('Navegando a cita:', notification.referenceId);
    }

    // Opcional: Cerrar el menú
    // this.isOpen = false;
  }

  private updateUnreadCount(): void {
    this.unreadCount = this.notifications.filter(n => !n.readFlag).length;
  }

  // Helper para iconos según tipo
  getIconClass(type: string): string {
    switch (type) {
      case 'ALERT': return 'icon-alert';
      case 'SUCCESS': return 'icon-success';
      default: return 'icon-info';
    }
  }
}
