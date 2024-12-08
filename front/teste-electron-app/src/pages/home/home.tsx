import styles from "./home.module.css";
import AddButton from "../../components/AddButton/add-button";
import CardMetas from "../../components/CardMetas/card-metas";

function Home() {
  return (
    <div className={styles.home}>
      
      {/* ==================== Header ==================== */}
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

      {/* ==================== Corpo Principal ==================== */}
      <main className={styles.body}>
        <div className={styles.cards}>
          
          {/* ==================== Cards de Saldo e Histórico ==================== */}
          <div className={styles.cards1}>
            {/* Card de Saldo Bancário */}
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

            {/* Card de Histórico de Transações */}
            <div className={styles.cardHistorico}>
              <div className={styles.titulotransacoes}>
                <h3>Visão geral de transações</h3>
                <p>Dez., 24</p>
              </div>
              
              {/* Transações do Dia e Mês */}
              <div className={styles.linesTransacoes}>
                <img
                  src="/assets/Hred.svg"
                  alt="Ícone Hred"
                  className={styles.iconH}
                />
                <p className={styles.spanRed}>
                  <span>Gastos do dia: </span> R$ 54,00
                </p>
                <hr />
              </div>

              <div className={styles.linesTransacoes}>
                <img
                  src="/assets/Hred.svg"
                  alt="Ícone Hred"
                  className={styles.iconH}
                />
                <p className={styles.spanRed}>
                  <span>Despesas mês: </span> R$ 1136,00
                </p>
              </div>

              <div className={styles.linesTransacoes}>
                <img
                  src="/assets/Hgreen.svg"
                  alt="Ícone Hgreen"
                  className={styles.iconH}
                />
                <p className={styles.spanGreen}>
                  <span>Receitas do mês: </span> R$ 2652,00
                </p>
              </div>
            </div>
          </div>

          {/* ==================== Card de Meta ==================== */}
          <div className={styles.cards2}>
            <CardMetas imagem="/assets/moto.svg" texto="Meta para moto"/>
          </div>
          
        </div>
      </main>
    </div>
  );
}

export default Home;
