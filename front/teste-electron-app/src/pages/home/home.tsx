import SideBar from "../../components/SideBar/side-bar";
import styles from "./home.module.css";
import AddButton from "../../components/AddButton/add-button";

function Home() {
  return (
    <div className={styles.home}>
      <SideBar />
      <header className={styles.header}>
        <div className={styles.headerContent}>
          <h1>
            Bem-vindo <span className={styles.userName}>Antonio</span>
          </h1>
        </div>

        <div className={styles.subHeader}>
          <div className={styles.headerSaldo}>
            <img
              src="/assets/money-home.svg"
              alt="Ícone de Estimativa de Saldo"
              className={styles.iconHeader}
            />
            <p className={styles.pHeader}>
              <span className={styles.sHeader}>Estimativa de Saldo: </span>
              R$ 1516,00
            </p>
          </div>
          <AddButton texto="Adicionar Transação" />
        </div>
      </header>
    </div>
  );
}

export default Home;
