import { useEffect, useState } from "react"; 
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./transacoes.module.css";
import ModalTipoTrans from "../../components/ModalTipoTransacao/modal-tipo-trans";

function Transacoes() {
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [saldoTotal, setSaldoTotal] = useState<number | null>(null);
  const [despesas, setDespesas] = useState<number | null>(null);
  const [receitas, setReceitas] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [mesSelecionado] = useState<string>('01');
  const [anoSelecionado] = useState<string>('2025');
  const [isLoading, setIsLoading] = useState<boolean>(true);

  const fetchSaldoTotal = async (mes: string) => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      setError("Você precisa estar logado para acessar esta funcionalidade.");
      setIsLoading(false);
      return;
    }

    const dataReferencia = `2600-${mes}-01`;

    try {
      const response = await fetch(
        `http://localhost:8080/contas/saldoTotal?data=${dataReferencia}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) throw new Error("Erro ao carregar o saldo total.");

      const data = await response.json();
      console.log("Saldo Total:", data);
      setSaldoTotal(data.saldo); 
    } catch (error) {
      setError("Erro ao carregar o saldo total.");
      console.error(error);
    }
  };

  const fetchDespesas = async (ano: string, mes: string) => {
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
      console.log("Despesas:", data);
      setDespesas(data.somatorio);
    } catch (error) {
      setError("Erro ao carregar as despesas.");
      console.error(error);
    }
  };

  const fetchReceitas = async (ano: string, mes: string) => {
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
      console.log("Receitas:", data);
      setReceitas(data.somatorio);
    } catch (error) {
      setError("Erro ao carregar as receitas.");
      console.error(error);
    }
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
          fetchSaldoTotal(mesSelecionado),
          fetchDespesas(anoSelecionado, mesSelecionado),
          fetchReceitas(anoSelecionado, mesSelecionado),
        ]);
      } catch (error) {
        setError("Erro ao carregar os dados.");
        console.error(error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, [mesSelecionado, anoSelecionado]);

  return (
    <div className={style.containerTransacoes}>
      <h1>Transações</h1>
      <div className={style.headerTransacoes}>
        <h3>Estimativa do mês</h3>
        <div className={style.rowTransacoes}>
          <div>
            <img src="/assets/iconsTransacoes/money.svg" alt="iconMoney" className={style.iconT} />
            <p>
              <span className={style.spanM}>Estimativa de Saldo: </span>
              {isLoading ? "Carregando..." : formatarSaldo(saldoTotal)}
            </p>
          </div>
          <div>
            <img src="/assets/iconsTransacoes/arrowred.svg" alt="iconMoney" className={style.iconT} />
            <p>
              <span className={style.spanR}>Despesas: </span>
              {isLoading ? "Carregando..." : formatarSaldo(despesas)}
            </p>
          </div>
          <div>
            <img src="/assets/iconsTransacoes/arrowgreen.svg" alt="iconMoney" className={style.iconT} />
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
            <input className={style.input} type="text" placeholder="Buscar..." />
            <img className={style.icon} src="/assets/iconsTransacoes/lupa.svg" alt="Lupa" />
            <img className={style.icon} src="/assets/iconsTransacoes/filter.svg" alt="Filter" />
          </div>
          <div>
            <AddButton texto="Realizar Transação" onClick={toggleModal} />
          </div>
        </div>

        <div className={style.transacoesList}>
          {/* Lista de transações */}  
        </div>
      </div>
      {isModalOpen && <ModalTipoTrans onClose={toggleModal} />}
    </div>
  );
}

export default Transacoes;
