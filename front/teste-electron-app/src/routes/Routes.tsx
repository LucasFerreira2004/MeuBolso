import { createBrowserRouter } from 'react-router-dom';
import Login from '../pages/Login/login';
import Cadastro from '../pages/Cadastro/cadastro';
import Home from '../pages/Home/home';
import ContasBancarias from '../pages/ContasBancarias/contas-bancarias';
import Categorias from '../pages/Categorias/categorias';
import PageTeste from '../pages/PageTeste/page-teste';
import App from '../App';

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
        element: <Home />
      },
      {
        path: "/perfil",
        element: <PageTeste />
      },
      {
        path: "/contas",
        element: <ContasBancarias />
      },
      {
        path: "/categorias",
        element: <Categorias />
      },
      {
        path: "/transacoes",
        element: <PageTeste />
      },
      {
        path: "/metas",
        element: <PageTeste />
      },
      {
        path: "/relatorios",
        element: <PageTeste />
      },
      {
        path: "/profile",
        element: <PageTeste />
      },
    ],
  },
]);
