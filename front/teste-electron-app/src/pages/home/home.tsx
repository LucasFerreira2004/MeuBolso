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

      <main className={styles.body}>
        <div className={styles.cards1}>
          <div className={styles.cardSaldo}>
            <h3>Saldo bancário</h3>
            <div className={styles.linebanks}>
              <img
                src="/assets/nubank.svg"
                alt="Ícone Nubank"
                className={styles.iconNubank}
              />
              <p>Nubank: R$ 1400,00</p>
            </div>
            <div className={styles.linebanks}>
              <img
                src="/assets/bradesco.svg"
                alt="Ícone Bradesco"
                className={styles.iconBrades}
              />
              <p>Bradesco: R$ 116,00</p>
            </div>
          </div>

          <div className={styles.cardHistorico}>
            <h3>Histórico de Últimas Transações</h3>
            {/* parte 1 */}
            <div className={styles.hTitulo}>
              <img
                src="/assets/Hblue.svg"
                alt="Ícone Hblue"
                className={styles.iconH}
              />
              <p>Dom, 10 de Novembro</p>
            </div>
            <div className={styles.linesHistorico}>
              <img
                src="/assets/Hred.svg"
                alt="Ícone Hred"
                className={styles.iconH}
              />
              <p className={styles.pred}>Mototaxi/ o trabalho</p>
            </div>
            <div className={styles.linesHistorico}>
              <img
                src="/assets/Hgreen.svg"
                alt="Ícone Hgreen"
                className={styles.iconH}
              />
              <p className={styles.pgreen}>Salário mensal</p>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}

export default Home;
