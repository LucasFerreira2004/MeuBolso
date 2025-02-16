import { useState, useEffect } from "react";
import { ProgressBar } from "../../ProgressBar/progress-bar";
import ModalEditOrcamento from "../../../ModalEditOrcamento/modal-edit-orcamento";
import style from "./card-categoria.module.css";

interface CategoriaDTO {
  id: number;
  nome: string;
  tipo: string;
  cor: string;
}

interface Notificacao {
  threshold: number;
  notificado: boolean;
}

interface Orcamento {
  id: number;
  descricao: string;
  valorEstimado: number;
  valorGasto: number;
  valorRestante: number;
  progresso: number;
  mes: number;
  ano: number;
  categoriaDTO: CategoriaDTO;
  notificacao: Notificacao;
}

interface TotalCategoriasProps {
  mes: number;
  ano: number;
}

function TotalCategorias({ mes, ano }: TotalCategoriasProps) {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [orcamentos, setOrcamentos] = useState<Orcamento[]>([]);
  const [selectedOrcamento, setSelectedOrcamento] = useState<Orcamento | null>(
    null
  );
  useEffect(() => {
    const fetchOrcamento = async () => {
      const token = localStorage.getItem("authToken");

      if (!token) {
        console.error("Token de autenticação não encontrado.");
        return;
      }

      try {
        const response = await fetch(
          `http://localhost:8080/orcamentos?ano=${ano}&mes=${mes}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );

        if (!response.ok) {
          throw new Error("Erro ao buscar orçamentos");
        }

        const data = await response.json();
        setOrcamentos(data);
      } catch (error) {
        console.error("Erro ao buscar orçamento:", error);
      }
    };

    fetchOrcamento();
  }, [mes, ano]); // Adicione mes e ano como dependências do useEffect
  const handleOpenModal = (orcamento: Orcamento) => {
    setSelectedOrcamento(orcamento);
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedOrcamento(null);
  };

  if (orcamentos.length === 0) {
    return <div>Não há orçamentos definidos para este mês</div>;
  }

  return (
    <div>
      {orcamentos.map((orcamento) => (
        <div key={orcamento.id} className={style.container}>
          <header className={style.header}>
            <h3 className={style.title}>{orcamento.categoriaDTO.nome}</h3>
            <div className={style.iconsEdit}>
              <button onClick={() => handleOpenModal(orcamento)}>
                <img src="/assets/iconsContas/editar.svg" alt="Editar" />
              </button>
              <button>
                <img src="/assets/iconsContas/excluir.svg" alt="Excluir" />
              </button>
            </div>
          </header>
          <main className={style.main}>
            <li className={style.item}>
              <p className={style.label}>Planejado:</p>
              <p className={style.value}>
                R$ {orcamento.valorEstimado.toLocaleString()}
              </p>
            </li>
            <li className={style.item}>
              <p className={style.label}>Gasto:</p>
              <p className={style.value}>
                R$ {orcamento.valorGasto.toLocaleString()}
              </p>
            </li>
            <li className={style.item}>
              <p className={style.label}>Progresso:</p>
              <ProgressBar value={orcamento.progresso * 100} />
            </li>
            <li className={style.item}>
              <p className={style.label}>Restante:</p>
              <p className={style.value}>
                R$ {orcamento.valorRestante.toLocaleString()}
              </p>
            </li>
          </main>
        </div>
      ))}

      {isModalOpen && selectedOrcamento && (
        <ModalEditOrcamento
          valor={selectedOrcamento.valorEstimado.toString()}
          data={`${selectedOrcamento.ano}-${String(
            selectedOrcamento.mes
          ).padStart(2, "0")}-01`}
          onCloseAll={handleCloseModal}
          handleChangeValor={(e) => console.log(e.target.value)}
          setCategoria={(categoriaId) => console.log(categoriaId)}
          setData={(date) => console.log(date)}
        />
      )}
    </div>
  );
}

export default TotalCategorias;
