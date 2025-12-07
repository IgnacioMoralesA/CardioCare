import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // <--- IMPORTANTE
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms'; // <--- IMPORTANTE
import { Router, RouterModule } from '@angular/router'; // <--- IMPORTANTE
import { AuthService } from '../../security/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true, // Asegúrate de que esto esté en true
  imports: [CommonModule, ReactiveFormsModule, RouterModule], // <--- AQUI AGREGAMOS LOS MODULOS
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) return;

    this.authService.login(this.loginForm.value).subscribe({
      next: (res) => {
        // Guarda el token y redirige
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.errorMessage = 'Credenciales incorrectas o usuario no encontrado';
        console.error(err);
      }
    });
  }
}
