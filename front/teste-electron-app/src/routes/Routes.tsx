import { createBrowserRouter } from "react-router-dom";
import Login from "../pages/Login/login";
import Cadastro from "../pages/Cadastro/cadastro";
import Home from "../pages/Home/home";
import ContasBancarias from "../pages/ContasBancarias/contas-bancarias";
import Categorias from "../pages/Categorias/categorias";
import PageTeste from "../pages/PageTeste/page-teste";
import App from "../App";
import Transacoes from "../pages/Transacoes/transacoes";
import Metas from "../pages/Metas/metas";
import PrivateRoute from "../components/private-routes"; // Importa o PrivateRoute
<<<<<<< HEAD
import Orcamentos from "../pages/Orcamentos/orcamentos";
=======
import Relatorios from "../pages/Relatorios/relatorios";
>>>>>>> c038c091d104bbeffc99ffbb9199930353728ec6

export const router = createBrowserRouter([
  {
    path: "/",
    element: <Login />
  },
  {
    path: "/cadastro",
    element: <Cadastro />
  },
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "/home",
        element: (
          <PrivateRoute>
            <Home />
          </PrivateRoute>
        ),
      },
      {
        path: "/perfil",
        element: (
          <PrivateRoute>
            <PageTeste />
          </PrivateRoute>
        ),
      },
      {
        path: "/contas",
        element: (
          <PrivateRoute>
            <ContasBancarias />
          </PrivateRoute>
        ),
      },
      {
        path: "/categorias",
        element: (
          <PrivateRoute>
            <Categorias />
          </PrivateRoute>
        ),
      },
      {
        path: "/transacoes",
        element: (
          <PrivateRoute>
            <Transacoes />
          </PrivateRoute>
        ),
      },
      {
        path: "/metas",
        element: (
          <PrivateRoute>
            <Metas />
          </PrivateRoute>
        ),
      },
      {
        path: "/orcamentos",
        element: (
          <PrivateRoute>
            <Orcamentos />
          </PrivateRoute>
        ),
      },
      {
        path: "/relatorios",
        element: (
          <PrivateRoute>
            <Relatorios />
          </PrivateRoute>
        ),
      },
      {
        path: "/profile",
        element: (
          <PrivateRoute>
            <PageTeste />
          </PrivateRoute>
        ),
      },
    ],
  },
]);
