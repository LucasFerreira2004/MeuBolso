import style from "./modal-delete-conta.module.css";

interface ModalDeleteContaProps {
  onClose: () => void;
  onConfirmDelete: (id: number) => void; // Função que espera um id
  contaId: number; // ID da conta a ser excluída
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
    console.error("Erro na requisição", error);
    return { success: false, error: { message: "Erro na conexão com o servidor." } };
  }
};

function ModalDeleteConta({ onClose, onConfirmDelete, contaId }: ModalDeleteContaProps) {
  const handleConfirmDelete = async () => {
    if (!contaId) {
      console.error("Erro ao excluir conta: ID da conta é obrigatório.");
      alert("ID da conta não foi encontrado. Tente novamente.");
      return;
    }

    const result = await deleteConta(contaId);

    if (result.success) {
      onConfirmDelete(contaId);  // Passa o ID da conta para a função de confirmação
      onClose();
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
