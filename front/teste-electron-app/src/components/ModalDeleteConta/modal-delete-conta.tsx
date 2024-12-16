import style from "./modal-delete-conta.module.css";

interface ModalDeleteContaProps {
  onClose: () => void;       // Função para fechar o modal
  onConfirmDelete: () => void; // Função para confirmar a exclusão
}

function ModalDeleteConta({ onClose, onConfirmDelete }: ModalDeleteContaProps) {
  return (
    <div className={style.containerDelete}>
      <h3>Deseja excluir esses dados?</h3>
      <div className={style.divButton}>
        <button className={style.buttonNao} onClick={onClose}>
          Não
        </button>
        <button className={style.buttonSim} onClick={onConfirmDelete}>
          Sim
        </button>
      </div>
    </div>
  );
}

export default ModalDeleteConta;
