import style from "./modal-delete-conta.module.css";

interface ModalDeleteContaProps {
  onClose: () => void;        // Função para fechar o modal
  onConfirmDelete: () => void; // Função para confirmar a exclusão
}

function ModalDeleteConta({ onClose, onConfirmDelete }: ModalDeleteContaProps) {
  return (
    <>
      {/* Overlay para escurecer o fundo */}
      <div className={style.overlay} onClick={onClose}></div>
      
      {/* Modal de exclusão */}
      <div className={style.containerDelete}>
        <h3>Deseja excluir esta conta?</h3>
        <div className={style.divButton}>
          <button className={style.buttonNao} onClick={onClose}>
            Não
          </button>
          <button className={style.buttonSim} onClick={onConfirmDelete}>
            Sim
          </button>
        </div>
      </div>
    </>
  );
}

export default ModalDeleteConta;
