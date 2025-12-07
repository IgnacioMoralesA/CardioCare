export interface AuthRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  role: string; // Ej: ROLE_ADMIN, ROLE_MEDIC
  userId: number;
}

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
  role: string; // Debe coincidir con el Enum: ROLE_ADMIN, ROLE_MEDIC, etc.
}

export interface UserResponse {
  id: number;
  name: string;
  email: string;
  role: string;
}
