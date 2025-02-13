import { useEffect, useState } from "react";
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./transacoes.module.css";
import ModalTipoTrans from "../../components/ModalTipoTransacao/modal-tipo-trans";
import ModalEditDespesa from "../../components/ModalEditDespesas/moda-edit-despesa"; // Importar modal de despesa
import ModalEditReceita from "../../components/ModalEditReceita/modal-edit-receita"; // Importar modal de receita
import DatePicker from "../../components/UI/Date/date";
import CardTransacoes from "../../components/UI/CardTransacoes/card-transacoes";

function Transacoes() {
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [isModalDespesaOpen, setIsModalDespesaOpen] = useState<boolean>(false); // Estado para modal de despesa
  const [isModalReceitaOpen, setIsModalReceitaOpen] = useState<boolean>(false); // Estado para modal de receita
  const [selectedTransactionId, setSelectedTransactionId] = useState<
    number | null
  >(null);
  const [totalDespesas, setTotalDespesas] = useState<number | null>(null);
  const [totalReceitas, setTotalReceitas] = useState<number | null>(null);
  const [saldoTotal, setSaldoTotal] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [mes, setMes] = useState<number>(new Date().getMonth() + 1);
  const [ano, setAno] = useState<number>(new Date().getFullYear());
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [, setTransacoes] = useState<any[]>([]);
  const [transacoesAgrupadas, setTransacoesAgrupadas] = useState<any[]>([]);

  const fetchSaldoTotal = async (ano: number, mes: number) => {
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

      if (!response.ok) throw new Error("Erro ao carregar o saldo total.");

      const data = await response.json();
      setTotalDespesas(data.valor);
    } catch (error) {
      setError("Erro ao carregar o saldo total.");
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

      if (!response.ok) throw new Error("Erro ao carregar o saldo total.");

      const data = await response.json();
      setTotalReceitas(data.valor);
    } catch (error) {
      setError("Erro ao carregar o saldo total.");
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
      setTransacoes(data);
      agruparTransacoesPorData(data);
    } catch (error) {
      setError("Erro ao carregar as transações.");
      console.error(error);
    }
  };

  // Função para agrupar transações por data
  const agruparTransacoesPorData = (transacoes: any[]) => {
    const transacoesPorData = transacoes.reduce((acc: any, transacao) => {
      const data = transacao.data_transacao;

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

  const handleEditClick = (id: number, tipo: string) => {
    setSelectedTransactionId(id);

    if (tipo === "DESPESA") {
      setIsModalDespesaOpen(true); // Abrir modal de despesa
    } else if (tipo === "RECEITA") {
      setIsModalReceitaOpen(true); // Abrir modal de receita
    }
  };

  const handleCloseEditModal = () => {
    setIsModalDespesaOpen(false);
    setIsModalReceitaOpen(false);
    setSelectedTransactionId(null);
  };

  const handleUpdateTransaction = (updatedTransaction: any) => {
    setTransacoesAgrupadas((prevTransacoes) => {
      return prevTransacoes.map((grupo) => {
        const updatedTransacoes = grupo.transacoes.map((transacao: any) =>
          transacao.id === updatedTransaction.id
            ? updatedTransaction
            : transacao
        );
        return { ...grupo, transacoes: updatedTransacoes };
      });
    });

    handleCloseEditModal();
  };

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      setError(null);

      try {
        await Promise.all([
          fetchSaldoTotal(ano, mes),
          fetchDespesas(ano, mes),
          fetchReceitas(ano, mes),
          fetchTransacoes(ano, mes),
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
              <span className={style.spanM}>Saldo total: </span>
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
              {isLoading ? "Carregando..." : formatarSaldo(totalDespesas)}
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
              {isLoading ? "Carregando..." : formatarSaldo(totalReceitas)}
            </p>
          </div>
        </div>
      </div>

      {error && <div className={style.errorMessage}>{error}</div>}

      <div className={style.bodyTransacoes}>
        <div className={style.headerBodyT}>
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
            </p>
          ) : (
            transacoesAgrupadas.map((grupo, index) => (
              <CardTransacoes
                key={index}
                transacoes={grupo.transacoes}
                dataTransacao={grupo.data}
                onEditClick={handleEditClick}
              />
            ))
          )}
        </div>
      </div>

      {isModalOpen && (
        <ModalTipoTrans mes={mes} ano={ano} onClose={toggleModal} />
      )}

      {isModalDespesaOpen && selectedTransactionId && (
        <ModalEditDespesa
          mes={mes}
          ano={ano}
          transactionId={selectedTransactionId}
          onClose={handleCloseEditModal}
          onTransactionUpdate={handleUpdateTransaction}
        />
      )}

      {isModalReceitaOpen && selectedTransactionId && (
        <ModalEditReceita
          mes={mes}
          ano={ano}
          transactionId={selectedTransactionId}
          onClose={handleCloseEditModal}
          onTransactionUpdate={handleUpdateTransaction}
        />
      )}
    </div>
  );
}

export default Transacoes;
