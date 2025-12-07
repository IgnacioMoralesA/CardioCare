import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
// 1. AGREGAMOS: withInterceptorsFromDi y HTTP_INTERCEPTORS
import { provideHttpClient, withInterceptorsFromDi, HTTP_INTERCEPTORS } from '@angular/common/http';

// 2. AGREGAMOS: La importación de tu interceptor
import { JwtInterceptor } from './security/interceptors/jwt.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),

    // 3. MODIFICAMOS: Habilitamos el soporte para interceptores basados en DI (Dependency Injection)
    provideHttpClient(withInterceptorsFromDi()),

    // 4. AGREGAMOS: Registramos el interceptor en la lista de proveedores
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true // Permite que haya varios interceptores si fuera necesario
    }
  ]
};
