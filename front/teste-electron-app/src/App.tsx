import { Outlet } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css"; // Importando os estilos necessários
import style from "./App.module.css";
import SideBar from "./components/SideBar/side-bar";

function App() {
  return (
    <>
      <div className={style.app}>
        <SideBar />
        <Outlet />
      </div>
      <ToastContainer position="top-right" autoClose={3000} />
    </>
  );
}

export default App;
