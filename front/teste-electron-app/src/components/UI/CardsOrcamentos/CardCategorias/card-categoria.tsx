import { useState, useEffect, useCallback } from "react";
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
  onOrcamentoAdded?: () => void;
}

function TotalCategorias({ mes, ano, onOrcamentoAdded }: TotalCategoriasProps) {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [orcamentos, setOrcamentos] = useState<Orcamento[]>([]);
  const [selectedOrcamento, setSelectedOrcamento] = useState<Orcamento | null>(null);
  const [valor, setValor] = useState<string>("");
  const [data, setData] = useState<string>("");
  const [, setCategoriaId] = useState<number | null>(null);

  const fetchOrcamentos = useCallback(async () => {
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
  }, [ano, mes]);

  useEffect(() => {
    fetchOrcamentos();
  }, [fetchOrcamentos]);

  useEffect(() => {
    if (onOrcamentoAdded) {
      fetchOrcamentos();
    }
  }, [onOrcamentoAdded, fetchOrcamentos]);

  useEffect(() => {
    fetchOrcamentos();
  }, [mes, ano, fetchOrcamentos]);

  useEffect(() => {
    if (onOrcamentoAdded) {
      fetchOrcamentos();
    }
  }, [onOrcamentoAdded, fetchOrcamentos]);

  const handleOpenModal = (orcamento: Orcamento) => {
    setSelectedOrcamento(orcamento);
    setValor(`R$ ${orcamento.valorEstimado.toLocaleString()}`);
    setData(`${orcamento.ano}-${String(orcamento.mes).padStart(2, "0")}-01`);
    setCategoriaId(orcamento.categoriaDTO.id);
    setIsModalOpen(true);
  };

  const handleOpenDeleteModal = (orcamento: Orcamento) => {
    setSelectedOrcamento(orcamento);
    setIsDeleteModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedOrcamento(null);
    setValor("");
    setData("");
    setCategoriaId(null);
  };

  const handleCloseDeleteModal = () => {
    setIsDeleteModalOpen(false);
    setSelectedOrcamento(null);
  };

  const handleDeleteSuccess = () => {
    setOrcamentos(orcamentos.filter((o) => o.id !== selectedOrcamento?.id));
    handleCloseDeleteModal();
  };

  const handleEditSuccess = () => {
    fetchOrcamentos();
    handleCloseModal();
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
          id={selectedOrcamento.id}
          valor={valor}
          data={data}
          onCloseAll={handleCloseModal}
          handleChangeValor={(e) => setValor(e.target.value)}
          setCategoria={(categoriaId) => setCategoriaId(categoriaId)}
          setData={(date) => setData(date)}
          onEditSuccess={handleEditSuccess}
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