import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import style from "./modal-delete-normal.module.css";

interface ModalDeleteNormalProps {
    onClose: () => void;
    onConfirmDelete: (id: number) => void; // Aceita um argumento do tipo number
    transacaoId: number;
  }
const deleteTransacao = async (id: number) => {
  try {
    const token = localStorage.getItem("authToken");

    if (!token) {
      toast.error("Token não encontrado. O usuário não está autenticado.");
      return {
        success: false,
        error: { message: "Token não encontrado. O usuário não está autenticado." },
      };
    }

    const response = await fetch(`http://localhost:8080/transacoes/${id}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.ok) {
      toast.success("Transação excluída com sucesso!");
      return { success: true };
    } else {
      const errorData = await response.json();
      toast.error(errorData.message || "Erro ao excluir transação.");
      return { success: false, error: errorData };
    }
  } catch (error) {
    console.error("Erro na requisição:", error);
    toast.error("Erro na conexão com o servidor.");
    return { success: false, error: { message: "Erro na conexão com o servidor." } };
  }
};

function ModalDeleteNormal({ onClose, onConfirmDelete, transacaoId }: ModalDeleteNormalProps) {
    const handleConfirmDelete = async () => {
      if (!transacaoId || transacaoId <= 0) {
        toast.error("ID da transação não foi encontrado ou é inválido. Tente novamente.");
        return;
      }
  
      const result = await deleteTransacao(transacaoId);
  
      if (result.success) {
        onClose();
        onConfirmDelete(transacaoId); // Passa o transacaoId como argumento
      } else {
        toast.error(result.error?.message || "Não foi possível excluir a transação.");
      }
    };
  
    return (
      <>
        <div className={style.overlay} onClick={onClose}></div>
        <div className={style.containerDelete}>
          <h3>Deseja excluir esta transação?</h3>
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

export default ModalDeleteNormal;