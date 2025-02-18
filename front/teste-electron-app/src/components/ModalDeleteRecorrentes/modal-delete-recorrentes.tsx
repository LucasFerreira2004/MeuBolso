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
  const deleteTransaction = async (url: string) => {
    try {
      onClose();
      const token = localStorage.getItem("authToken");
      if (!token) {
        console.error("Token não encontrado. O usuário não está autenticado.");
        return;
      }

      const response = await fetch(url, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.ok) {
        onDeleteSuccess();
        toast.success("Transação excluída com sucesso!");
      } else {
        console.error("Erro ao apagar transação");
      }
    } catch (error) {
      console.error("Erro na requisição:", error);
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <div className={styles.headerModal}>
          <h2>Excluir Transação Recorrente</h2>
          <button className={styles.closeButton} onClick={onClose}>
            <img src="assets/iconsModal/iconX.svg" alt="Fechar" />
          </button>
        </div>
        <p>Escolha uma opção:</p>
        <div className={styles.Buttons}>
          <button
            className={styles.deleteButton}
            onClick={() =>
              deleteTransaction(
                `http://localhost:8080/transacoesRecorrentes/${idTransacaoRecorrente}`
              )
            }
          >
            Apagar transação recorrente
          </button>
          <button
            className={styles.submitButton}
            onClick={() =>
              deleteTransaction(
                `http://localhost:8080/transacoesRecorrentes/futuras/${idTransacaoRecorrente}?data=${dataTransacao}`
              )
            }
          >
            Apagar essa e as próximas
          </button>
        </div>
      </div>
    </div>
  );
};

export default ModalDeleteRecorrentes;
