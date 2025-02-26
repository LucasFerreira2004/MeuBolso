import { useEffect, useState, useCallback } from "react";
import { useLocation } from "react-router-dom";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import style from "./home.module.css";
import AddButton from "../../components/UI/AddButton/add-button";
import DatePicker from "../../components/UI/Date/date";
import { meses } from "../../components/UI/Date/consts";
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";
import TotalBalanco from "../../components/UI/ChartsRelatorios/TotalBalanco/total-balanco";
import ModalTipoTrans from "../../components/ModalTipoTransacao/modal-tipo-trans";
import { baseUrl } from "../../api/api";

interface Banco {
  iconeUrl: string;
  nomeBanco: string;
  saldo: number;
}

interface SaldoTotal {
  saldo: number;
}

interface Somatorio {
  valor: number;
}

function Home() {
  const location = useLocation();
  const { state } = location;
  const [bancos, setBancos] = useState<Banco[]>([]);
  const [saldoTotal, setSaldoTotal] = useState<number | null>(null);
  const [totalDespesas, setTotalDespesas] = useState<number | null>(null);
  const [totalReceitas, setTotalReceitas] = useState<number | null>(null);
  const [mes, setMes] = useState(new Date().getMonth() + 1);
  const [ano, setAno] = useState(new Date().getFullYear());
  const [isLoading, setIsLoading] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);

  useEffect(() => {
    if (state?.successMessage) {
      toast.success(state.successMessage);
      window.history.replaceState({}, document.title);
    }
  }, [state]);

  const fetchData = async <T,>(
    url: string,
    errorMessage: string,
    setData: (data: T) => void
  ) => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      toast.error("Você precisa estar logado para acessar esta funcionalidade.");
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

      const data: T = await response.json();
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

  const toggleModal = () => {
    setIsModalOpen((prev) => !prev);
  };

  const handleUpdate = useCallback(() => {
    fetchData<Banco[]>(
      `${baseUrl}/contas/min?ano=${ano}&mes=${mes}`,
      "Erro ao carregar os dados dos bancos.",
      setBancos
    );

    fetchData<SaldoTotal>(
      `${baseUrl}/contas/saldoTotal?ano=${ano}&mes=${mes}`,
      "Erro ao carregar o saldo total.",
      (data) => setSaldoTotal(data.saldo)
    );

    fetchData<Somatorio>(
      `${baseUrl}/transacoes/somatorioDespesas?ano=${ano}&mes=${mes}`,
      "Erro ao carregar o total de despesas.",
      (data) => setTotalDespesas(data.valor)
    );

    fetchData<Somatorio>(
      `${baseUrl}/transacoes/somatorioReceitas?ano=${ano}&mes=${mes}`,
      "Erro ao carregar o total de receitas.",
      (data) => setTotalReceitas(data.valor)
    );

    console.log("Dados atualizados após fechar o modal.");
  }, [ano, mes]);

  useEffect(() => {
    handleUpdate();
  }, [handleUpdate, ano, mes]);

  return (
    <div className={style.home}>
      <header className={style.header}>
        <div className={style.headerContent}>
          <h1>
            Bem-vindo <span className={style.userName}>Antonio</span>
          </h1>
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
        </div>

        <div className={style.subHeader}>
          <div className={style.headerSaldo}>
            <img
              src="assets/money-home.svg"
              alt="Ícone de Estimativa de Saldo"
              className={style.iconHeader}
            />
            <p className={style.pHeader}>
              <span className={style.sHeader}>Saldo total: </span>
              {isLoading ? (
                <Skeleton width={150} height={20} />
              ) : (
                formatarSaldo(saldoTotal)
              )}
            </p>
          </div>
          <div className={style.cardButton}>
            <AddButton texto="Adicionar Transação" onClick={toggleModal} />
            {isModalOpen && (
              <ModalTipoTrans
                mes={mes}
                ano={ano}
                onClose={toggleModal}
                onUpdate={handleUpdate} 
              />
            )}
          </div>
        </div>
      </header>

      <main className={style.body}>
        <div className={style.cards}>
          <div className={style.cards1}>
            <div className={style.cardSaldo}>
              <div className={style.saldoFixo}>
                <h3>Saldo bancário</h3>
              </div>
              <div className={style.bancosScroll}>
                {isLoading ? (
                  <Skeleton height={30} count={3} />
                ) : bancos.length === 0 ? (
                  <p>Nenhuma conta Criada.</p>
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
            </div>

            <div className={style.cardHistorico}>
              <div className={style.titulotransacoes}>
                <h3>Visão geral de transações</h3>
                <p>{`${meses[mes - 1]} de ${ano}`}</p>
              </div>
              <div className={style.CLineTransacoes}>
                <div className={style.linesTransacoes}>
                  <img
                    src="assets/Hred.svg"
                    alt="Ícone Hred"
                    className={style.iconH}
                  />
                  <p className={style.spanRed}>
                    <span>Total Despesas: </span>
                    {isLoading ? (
                      <Skeleton width={80} />
                    ) : (
                      formatarSaldo(totalDespesas)
                    )}
                  </p>
                </div>
                <div className={style.linesTransacoes}>
                  <img
                    src="assets/Hgreen.svg"
                    alt="Ícone Hgreen"
                    className={style.iconH}
                  />
                  <p className={style.spanGreen}>
                    <span>Total Receitas: </span>
                    {isLoading ? (
                      <Skeleton width={80} />
                    ) : (
                      formatarSaldo(totalReceitas)
                    )}
                  </p>
                </div>
              </div>
            </div>
          </div>

          <div className={style.cards3}>
            <div className={style.graphic}>
              <TotalBalanco />
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