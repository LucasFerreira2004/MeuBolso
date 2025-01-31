import { useEffect, useState } from "react";
import AddButton from "../../components/UI/AddButton/add-button";
import Date from "../../components/UI/Date/date";
import style from "./transacoes.module.css";
import CardTransacoes from "../../components/UI/CardTransacoes/card-transacoes";
import ModalTipoTrans from "../../components/ModalTipoTransacao/modal-tipo-trans";

function Transacoes() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [saldoTotal, setSaldoTotal] = useState<number | null>(null);
  const [despesas, setDespesas] = useState<number | null>(null);
  const [receitas, setReceitas] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [mesSelecionado, setMesSelecionado] = useState<string>('01'); // Mês inicial fixo

  const fetchSaldoTotal = async (mes: string) => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      setError("Você precisa estar logado para acessar esta funcionalidade.");
      return;
    }

    const dataReferencia = `2600-${mes}-01`; // Data fixa, apenas o mês é dinâmico

    try {
      const response = await fetch(
        `http://localhost:8080/contas/saldoTotal?data=${dataReferencia}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) {
        throw new Error("Erro ao carregar o saldo total.");
      }

      const data = await response.json();
      setSaldoTotal(data.saldo);
    } catch (error) {
      setError("Erro ao carregar o saldo total.");
      console.error(error);
    }
  };

  // Função para buscar as despesas
  const fetchDespesas = async (mes: string) => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      setError("Você precisa estar logado para acessar esta funcionalidade.");
      return;
    }

    const dataReferencia = `2025-${mes}-31`; // Data para as despesas (ajustado para o final do mês)

    try {
      const response = await fetch(
        `http://localhost:8080/transacoes/somatorioDespesas?data=${dataReferencia}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) {
        throw new Error("Erro ao carregar as despesas.");
      }

      const data = await response.json();
      setDespesas(data.somatorio);
    } catch (error) {
      setError("Erro ao carregar as despesas.");
      console.error(error);
    }
  };

  // Função para buscar as receitas
  const fetchReceitas = async (mes: string) => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      setError("Você precisa estar logado para acessar esta funcionalidade.");
      return;
    }

    const dataReferencia = `2600-${mes}-31`; // Data para as receitas (ajustado para o final do mês)

    try {
      const response = await fetch(
        `http://localhost:8080/transacoes/somatorioReceitas?data=${dataReferencia}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) {
        throw new Error("Erro ao carregar as receitas.");
      }

      const data = await response.json();
      setReceitas(data.somatorio);
    } catch (error) {
      setError("Erro ao carregar as receitas.");
      console.error(error);
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

  const handleMonthChange = (month: string) => {
    console.log("Mês selecionado:", month);
    setMesSelecionado(month); 
    fetchDespesas(month); 
    fetchReceitas(month); 
  };

  const toggleModal = () => {
    setIsModalOpen((prev) => !prev);
  };

  useEffect(() => {
    fetchSaldoTotal(mesSelecionado); 
    fetchDespesas(mesSelecionado); 
    fetchReceitas(mesSelecionado); 
  }, [mesSelecionado]);

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
              <span className={style.spanM}>Estimativa de Saldo: </span>
              {saldoTotal !== null ? formatarSaldo(saldoTotal) : "Carregando..."}
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
              {despesas !== null ? formatarSaldo(despesas) : "Carregando..."}
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
              {receitas !== null ? formatarSaldo(receitas) : "Carregando..."}
            </p>
          </div>
        </div>
      </div>

      {error && <div className={style.errorMessage}>{error}</div>}

      <div className={style.bodyTransacoes}>
        <div className={style.headerBodyT}>
          <Date onMonthChange={handleMonthChange} />
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
          <CardTransacoes
            dia="28"
            mes={1}
            ano={2025}
            valor={1500.75}
            descricao="Pagamento de serviço"
            categoria="Serviços"
            conta="Conta Corrente"
            fixa="Não"
          />
        </div>
      </div>

      {/* Renderização condicional do modal */}
      {isModalOpen && (
          <ModalTipoTrans onClose={toggleModal} />
      )}
    </div>
  );
}

export default Transacoes;
