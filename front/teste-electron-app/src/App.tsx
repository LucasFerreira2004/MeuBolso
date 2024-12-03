import styles from "./App.module.css"
import Login from "./pages/Login/login";
function App() {

  return (
    <>
    <div className={styles.app}>
      <Login/>
    </div>
    </>
  );
}

export default App;
