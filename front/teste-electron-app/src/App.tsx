import { Outlet } from "react-router-dom";
import styles from "./App.module.css"
import SideBar from "./components/SideBar/side-bar";

function App() {
  return (
    <>
    <div className={styles.app}>
      <SideBar/>
      <Outlet/>

    </div>
    </>
  );
}

export default App;
