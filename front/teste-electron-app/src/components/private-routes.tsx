import { ReactNode } from "react";
import { Navigate } from "react-router-dom";
import { getToken } from "../service/auth-service"; // ou onde estiver a função que verifica o token

interface PrivateRouteProps {
  children: ReactNode; // Aceita qualquer conteúdo dentro do componente PrivateRoute
}

const PrivateRoute = ({ children }: PrivateRouteProps) => {
  const token = getToken(); // Verifica se o token está presente

  if (!token) {
    // Se não houver token, redireciona para o login
    return <Navigate to="/" />;
  }

  return <>{children}</>; // Renderiza os filhos se o usuário estiver autenticado
};

export default PrivateRoute;
