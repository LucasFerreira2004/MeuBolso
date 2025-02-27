import { jwtDecode } from 'jwt-decode';

// Define a interface para o payload do token JWT
interface JwtPayload {
  iss: string; // Emissor do token
  sub: string; // Identificação do usuário
  exp: number; // Timestamp de expiração
}

// Salva o token
export const saveToken = (token: string) => {
  // Salva o token no localStorage
  localStorage.setItem('authToken', token);
};

// Recupera o token
export const getToken = (): string | null => {
  // Recupera o token do localStorage
  return localStorage.getItem('authToken');
};

// Verifica se o token é válido
export const isTokenValid = (): boolean => {
  const token = getToken();
  if (!token) return false;

  // Decodifica o token e usa a tipagem definida
  const decoded: JwtPayload = jwtDecode<JwtPayload>(token);
  const currentTime = Math.floor(Date.now() / 1000); // Timestamp atual em segundos
  return decoded.exp > currentTime; // Verifica se o token ainda é válido
};

// Remove o token (logout)
export const removeToken = () => {
  // Remove o token do localStorage
  localStorage.removeItem('authToken');
};
