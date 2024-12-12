import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import Login from './pages/Login/login.tsx'
import Home from './pages/Home/home.tsx'
import Cadastro from './pages/Cadastro/cadastro.tsx'
import ContasBancarias from './pages/ContasBancarias/contas-bancarias.tsx'

const router = createBrowserRouter([
  {
  path: "/",
  element:<Login/>
  },
  {
    path:"/cadastro",
    element:<Cadastro/>
  },
  {
    path: "/",
    element: <App/>,
    children:[
      {
        path: "/home",
        element: <Home/>
      },
      {
        path: "/contas",
        element: <ContasBancarias/>
      },
      {
        path: "/home",
        element: <Home/>
      },
      {
        path: "/home",
        element: <Home/>
      },

    ],
  },
])

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>,
)

// Use contextBridge
window.ipcRenderer.on('main-process-message', (_event, message) => {
  console.log(message)
})
