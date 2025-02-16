import { useState, useEffect } from "react";
import { ProgressBar } from "../../ProgressBar/progress-bar";
import ModalEditOrcamento from "../../../ModalEditOrcamento/modal-edit-orcamento";
import ModalDeleteOrca from "../../../ModalDeleteOrcamentos/modal-delete-orca";
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
  onOrcamentoAdded?: () => void; // Callback para quando um orçamento é adicionado
}

function TotalCategorias({ mes, ano, onOrcamentoAdded }: TotalCategoriasProps) {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [orcamentos, setOrcamentos] = useState<Orcamento[]>([]); // Estado local para orçamentos
  const [selectedOrcamento, setSelectedOrcamento] = useState<Orcamento | null>(
    null
  );

  // Função para buscar orçamentos
  const fetchOrcamentos = async () => {
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
      setOrcamentos(data); // Atualiza o estado local com os orçamentos buscados
    } catch (error) {
      console.error("Erro ao buscar orçamento:", error);
    }
  };

  // Busca os orçamentos quando o componente é montado ou quando mes/ano mudam
  useEffect(() => {
    fetchOrcamentos();
  }, [mes, ano]);

  // Se a prop onOrcamentoAdded mudar, busca os orçamentos novamente
  useEffect(() => {
    if (onOrcamentoAdded) {
      fetchOrcamentos();
    }
  }, [onOrcamentoAdded]);

  const handleOpenModal = (orcamento: Orcamento) => {
    setSelectedOrcamento(orcamento);
    setIsModalOpen(true);
  };

  const handleOpenDeleteModal = (orcamento: Orcamento) => {
    setSelectedOrcamento(orcamento);
    setIsDeleteModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedOrcamento(null);
  };

  const handleCloseDeleteModal = () => {
    setIsDeleteModalOpen(false);
    setSelectedOrcamento(null);
  };

  const handleDeleteSuccess = () => {
    // Filtra a lista de orçamentos para remover o orçamento excluído
    setOrcamentos(orcamentos.filter((o) => o.id !== selectedOrcamento?.id));
    handleCloseDeleteModal();
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
              <button
                onClick={() => handleOpenModal(orcamento)}
                className={style.buttons}
              >
                <img
                  src="/assets/iconsContas/editar.svg"
                  alt="Editar"
                  className={style.imgEdit}
                />
              </button>
              <button
                className={style.buttons}
                onClick={() => handleOpenDeleteModal(orcamento)}
              >
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

      {isDeleteModalOpen && selectedOrcamento && (
        <ModalDeleteOrca
          url={`http://localhost:8080/orcamentos`}
          id={selectedOrcamento.id}
          onDeleteSuccess={handleDeleteSuccess}
          onClose={handleCloseDeleteModal}
        />
      )}
    </div>
  );
}

export default TotalCategorias;