import { useState, useEffect } from "react";
import styles from "./modal-tipo-trans.module.css";
import ModalDespesas from "../ModalDespesas/modal-despesas";
import ModalReceitas from "../ModalReceitas/modal-receitas";

interface ModalTransacaoProps {
  onClose: () => void;
  mes: number; 
  ano: number; 
  onUpdate: () => void; // Certifique-se de que esta linha está presente
}

function ModalTipoTransacao({ onClose, mes, ano, onUpdate }: ModalTransacaoProps) {
  const [isDespesasModalOpen, setIsDespesasModalOpen] = useState(false);
  const [isReceitasModalOpen, setIsReceitasModalOpen] = useState(false);
  const [, setSelectedMes] = useState(mes); 
  const [, setSelectedAno] = useState(ano); 

  const closeAllModals = () => {
    setIsDespesasModalOpen(false);
    setIsReceitasModalOpen(false);
    onClose(); 
    onUpdate(); // Chama a função de atualização ao fechar o modal
  };

  useEffect(() => {
    setSelectedMes(mes);
    setSelectedAno(ano);
  }, [mes, ano]);

  return (
    <>
      <div className={styles.modalOverlay} onClick={onClose}>
        <div
          className={styles.modalContent}
          onClick={(e: React.MouseEvent<HTMLDivElement>) => e.stopPropagation()}
        >
          <div className={styles.header}>
            <h3>Selecione o tipo da transacao</h3>
            <button className={styles.closeButton} onClick={onClose}>
              <img
                src="assets/iconsModal/iconX.svg"
                alt="Fechar"
                className={styles.icon}
              />
            </button>
          </div>
          <div className={styles.buttonsContainer}>
            <button
              className={styles.transactionButton}
              onClick={() => setIsDespesasModalOpen(true)}
            >
              <img
                src="assets/iconsModal/setavermelha.svg"
                alt="Despesas"
                className={styles.icon}
              />
              <p className={styles.transactionText}>Despesas</p>
            </button>
            <button
              className={styles.transactionButton}
              onClick={() => setIsReceitasModalOpen(true)}
            >
              <img
                src="assets/iconsModal/setaverde.svg"
                alt="Receitas"
                className={styles.icon}
              />
              <p className={styles.transactionText}>Receitas</p>
            </button>
          </div>
        </div>
      </div>

      {isDespesasModalOpen && (
        <ModalDespesas mes={mes} ano={ano} onCloseAll={closeAllModals} />
      )}
      {isReceitasModalOpen && (
        <ModalReceitas mes={mes} ano={ano} onCloseAll={closeAllModals} />
      )}
    </>
  );
}

export default ModalTipoTransacao;