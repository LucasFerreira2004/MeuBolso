import { createHashRouter } from "react-router-dom"; // Alterado para createHashRouter
import App from "../App";
import PrivateRoute from "../components/private-routes";

// Páginas Públicas
import Login from "../pages/Login/login";
import Cadastro from "../pages/Cadastro/cadastro";

// Páginas Privadas
import Home from "../pages/Home/home";
import ContasBancarias from "../pages/ContasBancarias/contas-bancarias";
import Categorias from "../pages/Categorias/categorias";
import Transacoes from "../pages/Transacoes/transacoes";
import Metas from "../pages/Metas/metas";
import Orcamentos from "../pages/Orcamentos/orcamentos";
import Relatorios from "../pages/Relatorios/relatorios";
import Perfil from "../pages/Perfil/perfil";

// Rotas Públicas
const publicRoutes = [
  {
    path: "/",
    element: <Login />,
  },
  {
    path: "/cadastro",
    element: <Cadastro />,
  },
];

// Rotas Privadas
const privateRoutes = [
  {
    path: "/home",
    element: <Home />,
  },
  {
    path: "/contas",
    element: <ContasBancarias />,
  },
  {
    path: "/categorias",
    element: <Categorias />,
  },
  {
    path: "/transacoes",
    element: <Transacoes />,
  },
  {
    path: "/metas",
    element: <Metas />,
  },
  {
    path: "/orcamentos",
    element: <Orcamentos />,
  },
  {
    path: "/relatorios",
    element: <Relatorios />,
  },
  {
    path: "/perfil",
    element: <Perfil />, // Renomeado de PageTeste para Profile
  },
];

// Aplicar PrivateRoute às rotas privadas
const protectedRoutes = privateRoutes.map((route) => ({
  ...route,
  element: <PrivateRoute>{route.element}</PrivateRoute>,
}));

// Combinar todas as rotas
export const router = createHashRouter([
  ...publicRoutes,
  {
    path: "/",
    element: <App />,
    children: protectedRoutes,
  },
]);