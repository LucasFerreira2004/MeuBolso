import styles from "./modal-tipo-trans.module.css";

interface ModalTransacaoProps {
    onClose: () => void;
}

function ModalTransacao({ onClose }: ModalTransacaoProps) {
    return (
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
                    <button className={styles.transactionButton}>
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
    );
}

export default ModalTransacao;
