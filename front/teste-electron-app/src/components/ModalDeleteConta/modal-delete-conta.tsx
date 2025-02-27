import style from "./modal-delete-conta.module.css";

interface ModalDeleteContaProps {
  onClose: () => void;
  onConfirmDelete: () => void; // Agora não precisa do ID
  contaId: number;
}

const deleteConta = async (id: number) => {
  try {
    const token = localStorage.getItem("authToken");

    if (!token) {
      return {
        success: false,
        error: { message: "Token não encontrado. O usuário não está autenticado." },
      };
    }

    const response = await fetch(`http://localhost:8080/contas/${id}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.ok) {
      return { success: true };
    } else {
      const errorData = await response.json();
      return { success: false, error: errorData };
    }
  } catch (error) {
    console.error("Erro na requisição:", error);
    return { success: false, error: { message: "Erro na conexão com o servidor." } };
  }
};

function ModalDeleteConta({ onClose, onConfirmDelete, contaId }: ModalDeleteContaProps) {
  const handleConfirmDelete = async () => {
    if (!contaId || contaId <= 0) {
      console.error("Erro ao excluir conta: ID da conta inválido.");
      alert("ID da conta não foi encontrado ou é inválido. Tente novamente.");
      return;
    }

    const result = await deleteConta(contaId);

    if (result.success) {
      onClose(); // Fecha o modal
      onConfirmDelete(); // Apenas avisa que deletou
    } else {
      console.error("Erro ao excluir conta:", result.error?.message || "Erro desconhecido.");
      alert(result.error?.message || "Não foi possível excluir a conta.");
    }
  };

  return (
    <>
      <div className={style.overlay} onClick={onClose}></div>
      <div className={style.containerDelete}>
        <h3>Deseja excluir esta conta?</h3>
        <span className={style.delete}>
          <img src="/assets/iconsModalConta/delete.svg"/>
          Ao deletar esta conta, todas as transaões relacionadas a ela sumirão
          </span>
        <div className={style.divButton}>
          <button className={style.buttonNao} onClick={onClose}>
            Não
          </button>
          <button className={style.buttonSim} onClick={handleConfirmDelete}>
            Sim
          </button>
        </div>
      </div>
    </>
  );
}

export default ModalDeleteConta;
