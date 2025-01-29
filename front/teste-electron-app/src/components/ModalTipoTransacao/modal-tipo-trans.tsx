import { useState } from "react";
import styles from "./modal-tipo-trans.module.css";
import ModalDespesas from "../ModalDespesas/modal-despesas";

interface ModalTransacaoProps {
  onClose: () => void;
}

function ModalTipoTransacao({ onClose }: ModalTransacaoProps) {
  const [isSecondModalOpen, setIsSecondModalOpen] = useState(false);

  const openSecondModal = () => {
    setIsSecondModalOpen(true);
  };

  const closeSecondModal = () => {
    setIsSecondModalOpen(false);
  };

  return (
    <>
      <div className={styles.modalOverlay} onClick={onClose}>
        <div
          className={styles.modalContent}
          onClick={(e) => e.stopPropagation()} 
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
              onClick={openSecondModal}
            >
              <img
                src="/assets/iconsModal/setavermelha.svg"
                alt="Despesas"
                className={styles.icon}
              />
              <p className={styles.transactionText}>Despesas</p>
            </button>
            <button className={styles.transactionButton}>
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

      {isSecondModalOpen && <ModalDespesas onClose={closeSecondModal} />}
    </>
  );
}

export default ModalTipoTransacao;
