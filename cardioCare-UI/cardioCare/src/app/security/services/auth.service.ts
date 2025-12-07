import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { AuthRequest, AuthResponse, RegisterRequest, UserResponse } from '../models/auth.models';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // Ajusta esto a tu puerto local
  private URL_API = 'http://localhost:8080/api/v1/auth';

  // BehaviorSubject para notificar cambios de estado (si está logueado o no) a la UI
  private loggedIn = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient, private router: Router) { }

  // --- LOGIN ---
  login(credentials: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.URL_API}/login`, credentials).pipe(
      tap((response) => {
        this.saveToken(response.token);
        this.saveRole(response.role);
        localStorage.setItem('userId', response.userId.toString());
        this.loggedIn.next(true);
      })
    );
  }

  // --- REGISTRO ---
  register(data: RegisterRequest): Observable<UserResponse> {
    return this.http.post<UserResponse>(`${this.URL_API}/register`, data);
  }

  // --- LOGOUT ---
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    this.loggedIn.next(false);
    this.router.navigate(['/login']);
  }

  // --- GETTERS & UTILS ---

  private saveToken(token: string): void {
    localStorage.setItem('token', token);
  }

  private saveRole(role: string): void {
    localStorage.setItem('role', role);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  // Verifica si existe token (puedes mejorar esto validando expiración con jwt-decode)
  private hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

  // Observable para que el Navbar se oculte/muestre dinámicamente
  get isLoggedIn$(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }
}
