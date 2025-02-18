import React, { useState, useMemo, useEffect } from "react";
import styles from "./card-transacoes.module.css";
import ModalDeleteNormal from "../../ModalDeleteNormal/modal-delete-normal";
import ModalDeleteRecorrentes from "../../ModalDeleteRecorrentes/modal-delete-recorrentes"; // Importe o novo modal
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

interface Transacao {
  id: number;
  valor: number;
  data_transacao: string;
  tipo: string;
  descricao: string;
  origem: string;
  idTransacaoRecorrente: number; // Mudança aqui, usando idTransacaoRecorrente
  conta: {
    descricao: string;
    banco: {
      nome: string;
    };
  };
  categoria: {
    nome: string;
    cor: string;
  };
  data_transacao_formatada?: string;
}

interface CardTransacoesProps {
  transacoes: Transacao[];
  dataTransacao: string;
  onEditClick: (id: number, tipo: string) => void;
  onDeleteSuccess: (id: number) => void;
  token: string; // Adicionado o token como prop
}

const CardTransacoes: React.FC<CardTransacoesProps> = ({
  transacoes: initialTransacoes,
  dataTransacao,
  onEditClick,
  onDeleteSuccess,
}) => {
  const [localTransacoes, setLocalTransacoes] = useState<Transacao[]>(initialTransacoes);
  const [modalAberto, setModalAberto] = useState<{
    tipo: "NORMAL" | "RECORRENTE" | null;
    transacaoId: number | null;
    dataTransacao: string | null; // Adicionado a data
  }>({ tipo: null, transacaoId: null, dataTransacao: null });

  useEffect(() => {
    setLocalTransacoes(initialTransacoes);
  }, [initialTransacoes]);

  const dataFormatada = useMemo(() => {
    if (!dataTransacao) return "";
    const [ano, mes, dia] = dataTransacao.split("-");
    const data = new Date(`${ano}-${mes}-${dia}T00:00:00`);
    return new Intl.DateTimeFormat("pt-BR", {
      day: "2-digit",
      month: "long",
      year: "numeric",
    }).format(data);
  }, [dataTransacao]);

  const formatarValor = (valor: number): string => {
    return valor.toLocaleString("pt-BR", {
      style: "currency",
      currency: "BRL",
    });
  };

  const handleDeleteClick = (event: React.MouseEvent, transacao: Transacao) => {
    event.stopPropagation();

    if (transacao.origem === "NORMAL") {
      setModalAberto({ tipo: "NORMAL", transacaoId: transacao.id, dataTransacao: transacao.data_transacao });
    } else if (transacao.origem === "FIXA" || transacao.origem === "PARCELADA") {
      setModalAberto({
        tipo: "RECORRENTE",
        transacaoId: transacao.idTransacaoRecorrente,
        dataTransacao: transacao.data_transacao, 
      });
    }
  };

  const handleCloseModal = () => {
    setModalAberto({ tipo: null, transacaoId: null, dataTransacao: null });
  };

  const handleDeleteSuccess = (id: number) => {
    setLocalTransacoes((prevTransacoes) =>
      prevTransacoes.filter((transacao) => transacao.id !== id)
    );
    onDeleteSuccess(id);
    toast.success("Transação excluída com sucesso!");
  };

  return (
    <div className={styles.card}>
      <ToastContainer />
      <header className={styles.time}>
        <div className={styles.bolinhaDate}></div>
        <time>{dataFormatada}</time>
      </header>
      <ul className={styles.detalhes}>
        {localTransacoes.length > 0 ? (
          localTransacoes.map((transacao) => {
            const valor = formatarValor(transacao.valor);
            const corTransacao =
              transacao.tipo === "DESPESA"
                ? "#C63A22"
                : transacao.tipo === "RECEITA"
                  ? "#2A9D8F"
                  : `#${transacao.categoria.cor}`;

            return (
              <li
                key={transacao.id}
                onClick={() => onEditClick(transacao.id, transacao.tipo)}
                className={styles.linha}
              >
                <div
                  className={styles.individualLine}
                  style={{ color: corTransacao }}
                >
                  <div
                    className={styles.bolinha}
                    style={{ backgroundColor: corTransacao }}
                  ></div>
                  {transacao.descricao} <br />
                </div>
                <div
                  className={styles.individualLine}
                  style={{ color: corTransacao }}
                >
                  {valor} <br />
                </div>
                <div className={styles.individualLine}>
                  <label className={styles.label}>Categoria:</label>{" "}
                  {transacao.categoria?.nome} <br />
                </div>
                <div className={styles.individualLine}>
                  <label className={styles.label}>Conta:</label>{" "}
                  {transacao.conta?.banco?.nome} <br />
                </div>
                <div className={styles.individualLine}>
                  <label className={styles.label}>Fixa:</label>{" "}
                  {transacao.origem === "FIXA" ? "Sim" : "Não"}
                </div>
                <button
                  className={styles.button}
                  onClick={(event) => handleDeleteClick(event, transacao)}
                >
                  <img src="/assets/iconsContas/excluir.svg" alt="Excluir" />
                </button>
              </li>
            );
          })
        ) : (
          <p>Nenhuma transação encontrada.</p>
        )}
      </ul>

      {modalAberto.tipo === "NORMAL" && modalAberto.transacaoId !== null && (
        <ModalDeleteNormal
          transacaoId={modalAberto.transacaoId}
          onClose={handleCloseModal}
          onConfirmDelete={handleDeleteSuccess}
        />
      )}
      {modalAberto.tipo === "RECORRENTE" && modalAberto.transacaoId !== null && modalAberto.dataTransacao && (
        <ModalDeleteRecorrentes
          idTransacaoRecorrente={modalAberto.transacaoId}
          dataTransacao={modalAberto.dataTransacao} 
          onClose={handleCloseModal}
          onDeleteSuccess={() => handleDeleteSuccess(modalAberto.transacaoId!)}
        />
      )}

    </div>
  );
};

export default CardTransacoes;
