import style from "./home.module.css";
import AddButton from "../../components/UI/AddButton/add-button";
import CardMetas from "../../components/CardMetas/card-metas";

function Home() {
  return (
    <div className={style.home}>
      
      {/* ==================== Header ==================== */}
      <header className={style.header}>
        <div className={style.headerContent}>
          <h1>
            Bem-vindo <span className={style.userName}>Antonio</span>
          </h1>
        </div>

        <div className={style.subHeader}>
          <div className={style.headerSaldo}>
            <img
              src="/assets/money-home.svg"
              alt="Ícone de Estimativa de Saldo"
              className={style.iconHeader}
            />
            <p className={style.pHeader}>
              <span className={style.sHeader}>Estimativa de Saldo: </span>
              R$ 1516,00
            </p>
          </div>
          <AddButton texto="Adicionar Transação" />
        </div>
      </header>

      {/* ==================== Corpo Principal ==================== */}
      <main className={style.body}>
        <div className={style.cards}>
          
          {/* ==================== Cards de Saldo e Histórico ==================== */}
          <div className={style.cards1}>
            {/* Card de Saldo Bancário */}
            <div className={style.cardSaldo}>
              <h3>Saldo bancário</h3>
              <div className={style.linebanks}>
                <img
                  src="/assets/nubank.svg"
                  alt="Ícone Nubank"
                  className={style.iconNubank}
                />
                <p>Nubank: R$ 1400,00</p>
              </div>
              <div className={style.linebanks}>
                <img
                  src="/assets/bradesco.svg"
                  alt="Ícone Bradesco"
                  className={style.iconBrades}
                />
                <p>Bradesco: R$ 116,00</p>
              </div>
            </div>

            {/* Card de Histórico de Transações */}
            <div className={style.cardHistorico}>
              <div className={style.titulotransacoes}>
                <h3>Visão geral de transações</h3>
                <p>Dez., 24</p>
              </div>
              
              {/* Transações do Dia e Mês */}
              <div className={style.linesTransacoes}>
                <img
                  src="/assets/Hred.svg"
                  alt="Ícone Hred"
                  className={style.iconH}
                />
                <p className={style.spanRed}>
                  <span>Gastos do dia: </span> R$ 54,00
                </p>
                <hr />
              </div>

              <div className={style.linesTransacoes}>
                <img
                  src="/assets/Hred.svg"
                  alt="Ícone Hred"
                  className={style.iconH}
                />
                <p className={style.spanRed}>
                  <span>Despesas mês: </span> R$ 1136,00
                </p>
              </div>

              <div className={style.linesTransacoes}>
                <img
                  src="/assets/Hgreen.svg"
                  alt="Ícone Hgreen"
                  className={style.iconH}
                />
                <p className={style.spanGreen}>
                  <span>Receitas do mês: </span> R$ 2652,00
                </p>
              </div>
            </div>
          </div>

          {/* ==================== Card de Meta ==================== */}
          <div className={style.cards2}>
            <h2>Metas</h2>
            <CardMetas imagem="/assets/moto.svg" texto="Meta para moto"/>
          </div>

          <div className={style.cards3}>
            <div className={style.graphic}>
              
            </div>
          </div>
          
        </div>
      </main>
    </div>
  );
}

export default Home;
