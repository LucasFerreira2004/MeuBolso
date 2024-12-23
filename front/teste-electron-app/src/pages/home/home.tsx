import { useEffect, useState } from "react";
import style from "./home.module.css";
import AddButton from "../../components/UI/AddButton/add-button";
import CardMetas from "../../components/CardMetas/card-metas";
import Example from "../../components/UI/Mycharts/my-charts";

interface Banco {
  iconeUrl: string;
  nomeBanco: string;
  saldo: number;
}

function Home() {
  const [bancos, setBancos] = useState<Banco[]>([]);
  const [saldoTotal, setSaldoTotal] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);

  // Função para buscar os bancos
  const fetchBancos = async () => {
    try {
      const response = await fetch("http://localhost:8080/contas/min");
      if (!response.ok) {
        throw new Error("Erro ao carregar os dados dos bancos.");
      }
      const data = await response.json();
      setBancos(data);
    } catch (error) {
      setError("Erro ao buscar os dados dos bancos.");
      console.error(error);
    }
  };

  const fetchSaldoTotal = async () => {
    try {
      const response = await fetch("http://localhost:8080/contas/saldoTotal");
      if (!response.ok) {
        throw new Error("Erro ao carregar o saldo total.");
      }
      const data = await response.json();
      setSaldoTotal(data.saldo); // Atualiza o estado com o saldo total retornado
    } catch (error) {
      setError("Erro ao buscar o saldo total.");
      console.error(error);
    }
  };

  useEffect(() => {
    fetchBancos();
    fetchSaldoTotal();
  }, []);

  return (
    <div className={style.home}>
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
              {/* Exibe o saldo total ou uma mensagem de carregamento */}
              {saldoTotal !== null ? `R$ ${saldoTotal.toFixed(2)}` : "Carregando..."}
            </p>
          </div>
          <AddButton texto="Adicionar Transação" onClick={function (): void {}} />
        </div>
      </header>

      <main className={style.body}>
        <div className={style.cards}>
          <div className={style.cards1}>
            <div className={style.cardSaldo}>
              <h3>Saldo bancário</h3>
              {error && <p>{error}</p>}
              {bancos.length === 0 ? (
                <p>Carregando dados...</p>
              ) : (
                bancos.map((banco) => (
                  <div className={style.linebanks} key={banco.nomeBanco}>
                    <img
                      src={banco.iconeUrl}
                      alt={`Ícone ${banco.nomeBanco}`}
                      className={style.iconNubank}
                    />
                    <p>{banco.nomeBanco}: R$ {banco.saldo.toFixed(2)}</p>
                  </div>
                ))
              )}
            </div>

            <div className={style.cardHistorico}>
              <div className={style.titulotransacoes}>
                <h3>Visão geral de transações</h3>
                <p>Dez., 24</p>
              </div>

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

          <div className={style.cards2}>
            <h2>Metas</h2>
            <CardMetas imagem="/assets/moto.svg" texto="Meta para moto" />
          </div>

          <div className={style.cards3}>
            <div className={style.graphic}>
              <Example/>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}

export default Home;
