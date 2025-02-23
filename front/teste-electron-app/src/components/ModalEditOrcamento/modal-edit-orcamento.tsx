import React, { useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import styles from "./modal-edit-orcamento.module.css";
import DatePicker from "../UI/DatePicker/date-picker";
import InputWithIcon from "../UI/InputsModal/input-modal";
import SelectedDespesas from "../UI/SelectedDespesa/selected-despesa";
import { baseUrl } from "../../api/api";

type ModalEditOrcamentoProps = {
  id: number;
  valor: string;
  data: string;
  onCloseAll: () => void;
  handleChangeValor: (e: React.ChangeEvent<HTMLInputElement>) => void;
  setCategoria: (categoriaId: number | null) => void; // Adicione esta linha
  setData: (data: string) => void;
  onEditSuccess: () => void;
};

function ModalEditOrcamento({
  id,
  valor,
  data,
  onCloseAll,
  handleChangeValor,
  setData,
  onEditSuccess,
}: ModalEditOrcamentoProps) {
  const [isLoading, setIsLoading] = useState(false);
  const [idCategoria, setIdCategoria] = useState<number | null>(null); // Estado para armazenar o idCategoria selecionado

  const handleSave = async () => {
    setIsLoading(true);

    const token = localStorage.getItem("authToken");

    if (!token) {
      toast.error("Por favor, faça login novamente.");
      setIsLoading(false);
      return;
    }

    // Verifica se uma categoria foi selecionada
    if (idCategoria === null) {
      toast.error("Por favor, selecione uma categoria.");
      setIsLoading(false);
      return;
    }

    // Debugging: Log the ID being edited
    console.log("Editing orcamento with ID:", id);

    const payload = {
      valorEstimado: parseFloat(valor.replace("R$ ", "").replace(",", ".")),
      periodo: data,
      idCategoria: idCategoria, // Usa o idCategoria do estado
    };

    // Debugging: Log the payload being sent to the API
    console.log("Payload sendo enviado:", payload);

    try {
      const response = await axios.put(`${baseUrl}/orcamentos/${id}`, payload, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      // Debugging: Log the response from the API
      console.log("Resposta da API:", response.data);

      if (response.status === 200) {
        toast.success("Orçamento atualizado com sucesso!");
        onEditSuccess();
        onCloseAll();
      } else {
        toast.error("Erro ao atualizar o orçamento. Tente novamente.");
      }
    } catch (error) {
      console.error("Erro na requisição:", error);
      if (axios.isAxiosError(error)) {
        console.log("Detalhes do erro:", error.response?.data);

        // Handle 403 Forbidden error specifically
        if (error.response?.status === 403) {
          toast.error("Acesso negado: Este orçamento não pertence ao usuário logado.");
        } else {
          toast.error(`Erro: ${error.response?.data.message || "Verifique os dados."}`);
        }
      } else {
        toast.error("Erro ao atualizar o orçamento. Verifique os dados.");
      }
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div
      className={styles.modalOverlay}
      onClick={(e: React.MouseEvent<HTMLDivElement>) => e.stopPropagation()}
    >
      <div className={styles.modalContent}>
        <div className={styles.headerModal}>
          <h3>Editar orçamento</h3>
          <button
            className={styles.closeButton}
            onClick={onCloseAll}
            aria-label="Fechar"
          >
            <img src="/assets/iconsModal/iconX.svg" alt="Fechar" />
          </button>
        </div>

        <InputWithIcon
          label="Valor: "
          type="text"
          iconSrc="/assets/iconsModalOrcamentos/money.svg"
          placeholder="R$ 0,00"
          value={valor}
          onChange={handleChangeValor}
        />

        {/* Passa a função setIdCategoria para o componente SelectedDespesas */}
        <SelectedDespesas setCategoria={setIdCategoria} />

        <DatePicker
          label="Escolha uma data:"
          value={data}
          onChange={(date: string) => setData(date)}
          iconsrc="/assets/iconsModalOrcamentos/date.svg"
        />

        <div className={styles.footerModal}>
          <button
            className={styles.saveButton}
            onClick={handleSave}
            disabled={isLoading}
          >
            {isLoading ? "Salvando..." : "Salvar"}
          </button>
        </div>

        <ToastContainer
          position="top-right"
          autoClose={5000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
        />
      </div>
    </div>
  );
}

export default ModalEditOrcamento;