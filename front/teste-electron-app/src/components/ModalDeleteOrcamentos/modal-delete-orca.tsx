import { baseUrl } from "../../api/api";
import style from "./modal-delete-orca.module.css";

interface ModalDeleteOrcaProps {
  id: number;
  url: string;
  onDeleteSuccess: () => void;
  onClose: () => void;
}

function ModalDeleteOrca({ id, onDeleteSuccess, onClose }: ModalDeleteOrcaProps) {
  const handleDelete = async () => {
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      return;
    }

    try {
      const response = await fetch(`${baseUrl}/${id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error("Erro ao excluir orçamento");
      }

      onDeleteSuccess(); 
      onClose(); 
    } catch (error) {
      console.error("Erro ao excluir orçamento:", error);
    }
  };

  return (
    <>
      <div className={style.overlay}></div>
      <div className={style.containerDelete}>
        <h3>Deseja excluir este orçamento?</h3>
        <div className={style.divButton}>
          <button className={style.buttonNao} onClick={onClose}>
            Não
          </button>
          <button className={style.buttonSim} onClick={handleDelete}>
            Sim
          </button>
        </div>
      </div>
    </>
  );
}

export default ModalDeleteOrca;
