package cl.ufro.dci.cardiocare.security.service.impl;

import cl.ufro.dci.cardiocare.security.domain.Role;
import cl.ufro.dci.cardiocare.security.domain.User;
import cl.ufro.dci.cardiocare.security.dto.*;
import cl.ufro.dci.cardiocare.security.jwt.JwtService;
import cl.ufro.dci.cardiocare.security.repository.UserRepository;
import cl.ufro.dci.cardiocare.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new org.springframework.security.authentication.BadCredentialsException("Credenciales inválidas");
        }

        String token = jwtService.generateTokenForUser(
                user.getEmail(),
                user.getRole().name(),
                user.getId());

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setRole(user.getRole().name());
        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        return response;
    }

    @Override
    public UserResponse register(RegisterRequest request) {

        Role roleEnum;
        try {
            roleEnum = Role.valueOf(request.getRole());
        } catch (Exception e) {
            throw new IllegalArgumentException("Rol inválido. Usa: " + java.util.Arrays.toString(Role.values()));
        }

        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleEnum)
                .build();

        userRepository.save(user);

        UserResponse res = new UserResponse();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setName(user.getName());
        res.setRole(user.getRole().name());
        return res;
    }
}
