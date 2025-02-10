import { useEffect, useState } from "react";
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./transacoes.module.css";
import ModalTipoTrans from "../../components/ModalTipoTransacao/modal-tipo-trans";
import DatePicker from "../../components/UI/Date/date";
import CardTransacoes from "../../components/UI/CardTransacoes/card-transacoes";

function Transacoes() {
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [saldoTotal, setSaldoTotal] = useState<number | null>(null);
  const [despesas, setDespesas] = useState<number | null>(null);
  const [receitas, setReceitas] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [mes, setMes] = useState<number>(new Date().getMonth() + 1);
  const [ano, setAno] = useState<number>(new Date().getFullYear());
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [, setTransacoes] = useState<any[]>([]);
  const [transacoesAgrupadas, setTransacoesAgrupadas] = useState<any[]>([]);

  const fetchSaldoTotal = async (mes: number) => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      setError("Você precisa estar logado para acessar esta funcionalidade.");
      setIsLoading(false);
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/contas/saldoTotal?ano=${ano}&mes=${mes}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) throw new Error("Erro ao carregar o saldo total.");

      const data = await response.json();
      setSaldoTotal(data.saldo);
    } catch (error) {
      setError("Erro ao carregar o saldo total.");
      console.error(error);
    }
  };

  const fetchDespesas = async (ano: number, mes: number) => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      setError("Você precisa estar logado para acessar esta funcionalidade.");
      setIsLoading(false);
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/transacoes/somatorioDespesas?ano=${ano}&mes=${mes}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) throw new Error("Erro ao carregar as despesas.");

      const data = await response.json();
      setDespesas(data.somatorio);
    } catch (error) {
      setError("Erro ao carregar as despesas.");
      console.error(error);
    }
  };

  const fetchReceitas = async (ano: number, mes: number) => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      setError("Você precisa estar logado para acessar esta funcionalidade.");
      setIsLoading(false);
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/transacoes/somatorioReceitas?ano=${ano}&mes=${mes}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) throw new Error("Erro ao carregar as receitas.");

      const data = await response.json();
      setReceitas(data.somatorio);
    } catch (error) {
      setError("Erro ao carregar as receitas.");
      console.error(error);
    }
  };

  const fetchTransacoes = async (ano: number, mes: number) => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      setError("Você precisa estar logado para acessar esta funcionalidade.");
      setIsLoading(false);
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/transacoes?ano=${ano}&mes=${mes}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) throw new Error("Erro ao carregar as transações.");

      const data = await response.json();
      setTransacoes(data); // Atualiza as transações
      agruparTransacoesPorData(data); // Agrupar transações por data
    } catch (error) {
      setError("Erro ao carregar as transações.");
      console.error(error);
    }
  };

  // Função para agrupar transações por data
  const agruparTransacoesPorData = (transacoes: any[]) => {
    const transacoesPorData = transacoes.reduce((acc: any, transacao) => {
      const data = transacao.data_transacao; // A data da transação

      if (!acc[data]) {
        acc[data] = [];
      }
      acc[data].push(transacao);

      return acc;
    }, {});

    setTransacoesAgrupadas(
      Object.entries(transacoesPorData).map(([data, transacoes]) => ({
        data,
        transacoes,
      }))
    );
  };

  const formatarSaldo = (valor: number | null | undefined): string => {
    return valor != null
      ? valor.toLocaleString("pt-BR", { style: "currency", currency: "BRL" })
      : "R$ 0,00";
  };

  const toggleModal = () => {
    setIsModalOpen((prev) => !prev);
  };

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      setError(null);

      try {
        await Promise.all([
          fetchSaldoTotal(mes),
          fetchDespesas(ano, mes),
          fetchReceitas(ano, mes),
          fetchTransacoes(ano, mes), // Chama a função para carregar as transações
        ]);
      } catch (error) {
        setError("Erro ao carregar os dados.");
        console.error(error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, [mes, ano]);

  return (
    <div className={style.containerTransacoes}>
      <h1>Transações</h1>
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
      <div className={style.headerTransacoes}>
        <h3>Estimativa do mês</h3>
        <div className={style.rowTransacoes}>
          <div>
            <img
              src="/assets/iconsTransacoes/money.svg"
              alt="iconMoney"
              className={style.iconT}
            />
            <p>
              <span className={style.spanM}>Estimativa de Saldo: </span>
              {isLoading ? "Carregando..." : formatarSaldo(saldoTotal)}
            </p>
          </div>
          <div>
            <img
              src="/assets/iconsTransacoes/arrowred.svg"
              alt="iconMoney"
              className={style.iconT}
            />
            <p>
              <span className={style.spanR}>Despesas: </span>
              {isLoading ? "Carregando..." : formatarSaldo(despesas)}
            </p>
          </div>
          <div>
            <img
              src="/assets/iconsTransacoes/arrowgreen.svg"
              alt="iconMoney"
              className={style.iconT}
            />
            <p>
              <span className={style.spanT}>Receitas: </span>
              {isLoading ? "Carregando..." : formatarSaldo(receitas)}
            </p>
          </div>
        </div>
      </div>

      {error && <div className={style.errorMessage}>{error}</div>}

      <div className={style.bodyTransacoes}>
        <div className={style.headerBodyT}>
          <div className={style.search}>
            <input
              className={style.input}
              type="text"
              placeholder="Buscar..."
            />
            <img
              className={style.icon}
              src="/assets/iconsTransacoes/lupa.svg"
              alt="Lupa"
            />
            <img
              className={style.icon}
              src="/assets/iconsTransacoes/filter.svg"
              alt="Filter"
            />
          </div>
          <div>
            <AddButton texto="Realizar Transação" onClick={toggleModal} />
          </div>
        </div>

        <div className={style.transacoesList}>
          {isLoading ? (
            <p>Carregando transações...</p>
          ) : transacoesAgrupadas.length === 0 ? (
            <p className={style.mensagemVazia}>
              Não há transações para este período.
            </p> // Mensagem caso não haja transações
          ) : (
            transacoesAgrupadas.map((grupo, index) => (
              <CardTransacoes
                key={index}
                transacoes={grupo.transacoes}
                dataTransacao={grupo.data}
              />
            ))
          )}
        </div>
      </div>

      {isModalOpen && (
        <ModalTipoTrans mes={mes} ano={ano} onClose={toggleModal} />
      )}
    </div>
  );
}

export default Transacoes;
