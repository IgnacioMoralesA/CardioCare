import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, NavigationEnd } from '@angular/router'; // Importar Router
import { NotificationComponent } from './notification/notification.component';
import { AuthService } from './security/services/auth.service';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule, NotificationComponent],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isLoggedIn = false;
  showBackButton = false; // Controla la visibilidad del botón volver
  userRole = '';

  constructor(
    private authService: AuthService,
    private router: Router // Inyectamos Router
  ) {}

  ngOnInit(): void {
    // 1. Suscripción al estado del Login
    this.authService.isLoggedIn$.subscribe(status => {
      this.isLoggedIn = status;
      this.userRole = localStorage.getItem('role') || '';
    });

    // 2. Detectar cambios en la URL para el botón "Volver"
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      // Si la URL NO es '/dashboard', mostramos el botón
      this.showBackButton = event.urlAfterRedirects !== '/dashboard' && event.urlAfterRedirects !== '/';
    });
  }

  logout(): void {
    this.authService.logout();
  }

  goHome(): void {
    this.router.navigate(['/dashboard']);
  }
}
