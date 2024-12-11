import { Outlet } from "react-router-dom";
import style from "./App.module.css"
import SideBar from "./components/SideBar/side-bar";
import Modal from "./components/ModalTransferencia/modal-transferencia";

function App() {
  return (
    <>
    <div className={style.app}>
      <SideBar/>
      <Modal/>
      <Outlet/>
      
    </div>
    </>
  );
}

export default App;
