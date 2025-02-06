import { useState, useEffect } from "react";
import styles from "./modal-tipo-trans.module.css";
import ModalDespesas from "../ModalDespesas/modal-despesas";
import ModalReceitas from "../ModalReceitas/modal-receitas";

interface ModalTransacaoProps {
  onClose: () => void;
  mes: number; // Adicionando mes como prop
  ano: number; // Adicionando ano como prop
}

function ModalTipoTransacao({ onClose, mes, ano }: ModalTransacaoProps) {
  const [isDespesasModalOpen, setIsDespesasModalOpen] = useState(false);
  const [isReceitasModalOpen, setIsReceitasModalOpen] = useState(false);
  const [, setSelectedMes] = useState(mes); // Armazenando mes
  const [, setSelectedAno] = useState(ano); // Armazenando ano

  // Função para fechar tudo (modal de despesas/receitas + modal de transação)
  const closeAllModals = () => {
    setIsDespesasModalOpen(false);
    setIsReceitasModalOpen(false);
    onClose(); // Fecha também o modal principal
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
            <button className={styles.closeButton} onClick={onClose}>
              <img
                src="/assets/iconsModal/iconX.svg"
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
                src="/assets/iconsModal/setavermelha.svg"
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
                src="/assets/iconsModal/setaverde.svg"
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
      {isReceitasModalOpen && <ModalReceitas mes={mes} ano={ano} onCloseAll={closeAllModals} />}
    </>
  );
}

export default ModalTipoTransacao;
