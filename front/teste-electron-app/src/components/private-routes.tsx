import { ReactNode } from "react";
import { Navigate } from "react-router-dom";
import { getToken } from "../service/auth-service";
interface PrivateRouteProps {
  children: ReactNode;
}

const PrivateRoute = ({ children }: PrivateRouteProps) => {
  const token = getToken();
  if (!token) {
    return <Navigate to="/" />;
  }

  return <>{children}</>; 
};

export default PrivateRoute;
//Mudar a pasta desse componente