import { Outlet } from "react-router-dom";
import style from "./App.module.css"
import SideBar from "./components/SideBar/side-bar";

function App() {
  return (
    <>
    <div className={style.app}>
      <SideBar/>
      <Outlet/>
      
    </div>
    </>
  );
}

export default App;
