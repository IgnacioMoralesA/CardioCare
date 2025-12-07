package cl.ufro.dci.cardiocare.security.service;

import cl.ufro.dci.cardiocare.security.dto.*;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    UserResponse register(RegisterRequest request);
}
