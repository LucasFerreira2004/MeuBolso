import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import style from "./home.module.css";
import AddButton from "../../components/UI/AddButton/add-button";
import CardMetas from "../../components/UI/CardMetas/card-metas";
import Example from "../../components/UI/Mycharts/my-charts";
import DatePicker from "../../components/UI/Date/date";

interface Banco {
  iconeUrl: string;
  nomeBanco: string;
  saldo: number;
}

function Home() {
  const location = useLocation();
  const { state } = location;

  const [bancos, setBancos] = useState<Banco[]>([]);
  const [saldoTotal, setSaldoTotal] = useState<number | null>(null);
  const [error] = useState<string | null>(null);

  const [mes, setMes] = useState(new Date().getMonth() + 1);
  const [ano, setAno] = useState(new Date().getFullYear());
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (state?.successMessage) {
      toast.success(state.successMessage);
      window.history.replaceState({}, document.title);
    }
  }, [state]);

  const fetchData = async (
    url: string,
    errorMessage: string,
    setData: (data: any) => void
  ) => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      toast.error(
        "Você precisa estar logado para acessar esta funcionalidade."
      );
      return;
    }

    try {
      setIsLoading(true);
      const response = await fetch(url, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error(errorMessage);
      }

      const data = await response.json();
      setData(data);
    } catch (error) {
      toast.error(errorMessage);
      console.error(error);
    } finally {
      setIsLoading(false);
    }
  };

  const formatarSaldo = (valor: number | null | undefined) => {
    if (valor == null) {
      return "R$ 0,00";
    }
    return valor.toLocaleString("pt-BR", {
      style: "currency",
      currency: "BRL",
    });
  };

  useEffect(() => {
    fetchData(
      `http://localhost:8080/contas/min?ano=${ano}&mes=${mes}`,
      "Erro ao carregar os dados dos bancos.",
      setBancos
    );

    fetchData(
      `http://localhost:8080/contas/saldoTotal?ano=${ano}&mes=${mes}`,
      "Erro ao carregar o saldo total.",
      (data) => setSaldoTotal(data.saldo)
    );
  }, [ano, mes]);

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
              {isLoading ? "Carregando..." : formatarSaldo(saldoTotal)}
            </p>
          </div>
          <AddButton texto="Adicionar Transação" onClick={() => {}} />
        </div>
      </header>

      <DatePicker
        mes={mes}
        ano={ano}
        onChange={(novoMes, novoAno) => {
          if (novoMes !== mes || novoAno !== ano) {
            setMes(novoMes);
            setAno(novoAno);
          }
        }}
      />

      <main className={style.body}>
        <div className={style.cards}>
          <div className={style.cards1}>
            <div className={style.cardSaldo}>
              <h3>Saldo bancário</h3>
              {error && <p>{error}</p>}
              {isLoading ? (
                <p>Carregando dados...</p>
              ) : bancos.length === 0 ? (
                <p>Nenhum dado encontrado.</p>
              ) : (
                bancos.map((banco) => (
                  <div className={style.linebanks} key={banco.nomeBanco}>
                    <img
                      src={banco.iconeUrl}
                      alt={`Ícone ${banco.nomeBanco}`}
                      className={style.iconNubank}
                    />
                    <p>{`${banco.nomeBanco}: ${formatarSaldo(banco.saldo)}`}</p>
                  </div>
                ))
              )}
            </div>

            <div className={style.cardHistorico}>
              <div className={style.titulotransacoes}>
                <h3>Visão geral de transações</h3>
                <p>{`Mês: ${mes}, Ano: ${ano}`}</p>
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
            </div>
          </div>

          <div className={style.cards2}>
            <h2>Metas</h2>
            <CardMetas imagem="/assets/moto.svg" texto="Meta para moto" />
          </div>

          <div className={style.cards3}>
            <div className={style.graphic}>
              <Example />
            </div>
          </div>
        </div>
      </main>

      <ToastContainer
        position="top-right"
        autoClose={3000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
      />
    </div>
  );
}

export default Home;
