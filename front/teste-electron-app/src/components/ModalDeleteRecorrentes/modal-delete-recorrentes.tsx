import React from "react";
import styles from "./modal-delete-recorrentes.module.css";
import { toast } from "react-toastify";

interface ModalDeleteRecorrentesProps {
  idTransacaoRecorrente: number;
  dataTransacao: string;
  onClose: () => void;
  onDeleteSuccess: () => void;
}

const ModalDeleteRecorrentes: React.FC<ModalDeleteRecorrentesProps> = ({
  idTransacaoRecorrente,
  dataTransacao,
  onClose,
  onDeleteSuccess,
}) => {
  const handleDeleteRecorrente = async () => {
    try {
      const token = localStorage.getItem("authToken");

      if (!token) {
        console.error("Token não encontrado. O usuário não está autenticado.");
        return;
      }

      const response = await fetch(`http://localhost:8080/transacoesRecorrentes/${idTransacaoRecorrente}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.ok) {
        onDeleteSuccess();
        onClose();
        toast.success("Transação recorrente excluída com sucesso!");
      } else {
        console.error("Erro ao apagar transação recorrente");
      }
    } catch (error) {
      console.error("Erro na requisição:", error);
    }
  };

  const handleDeleteFuturas = async () => {
    const dataFutura = dataTransacao;

    try {
      const token = localStorage.getItem("authToken");

      if (!token) {
        console.error("Token não encontrado. O usuário não está autenticado.");
        return;
      }

      const response = await fetch(
        `http://localhost:8080/transacoesRecorrentes/futuras/${idTransacaoRecorrente}?data=${dataFutura}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.ok) {
        onDeleteSuccess();
        onClose();
        toast.success("Transações futuras excluídas com sucesso!");
      } else {
        console.error("Erro ao apagar transação e as futuras");
      }
    } catch (error) {
      console.error("Erro na requisição:", error);
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <h2>Excluir Transação Recorrente</h2>
        <p>Escolha uma opção:</p>
        <button className={styles.button} onClick={handleDeleteRecorrente}>
          Apagar transação recorrente
        </button>
        <button className={styles.button} onClick={handleDeleteFuturas}>
          Apagar essa e as próximas
        </button>
        <button className={styles.button} onClick={onClose}>
          Cancelar
        </button>
      </div>
    </div>
  );
};

export default ModalDeleteRecorrentes;
