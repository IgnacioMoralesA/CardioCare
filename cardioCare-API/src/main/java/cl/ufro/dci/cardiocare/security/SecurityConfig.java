package cl.ufro.dci.cardiocare.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // DESACTIVAR CSRF SOLO EN DESARROLLO
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/pacientes",
                                "/api/medicos",
                                "/api/mensajes",
                                "/api/dashboard/",
                                "/api/**",     // ← Permite todos tus endpoints
                                "/h2-console/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {})  // POR SI LO QUIERES
                .formLogin(form -> form.disable()); // ← DESACTIVA LOGIN HTML

        return http.build();
    }
}
