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
  setCategoria: (categoriaId: number | null) => void;
  setData: (data: string) => void;
  onEditSuccess: () => void;
};

function ModalEditOrcamento({
  id,
  valor,
  data,
  onCloseAll,
  handleChangeValor,
  setCategoria,
  setData,
  onEditSuccess,
}: ModalEditOrcamentoProps) {
  const [isLoading, setIsLoading] = useState(false);

  const handleSave = async () => {
    setIsLoading(true);

    const token = localStorage.getItem("authToken");

    if (!token) {
      toast.error("Por favor, faça login novamente.");
      setIsLoading(false);
      return;
    }

    const payload = {
      valorEstimado: parseFloat(valor.replace("R$ ", "").replace(",", ".")),
      periodo: data,
      idCategoria: 24,
    };

    try {
      const response = await axios.put(
        `${baseUrl}/orcamentos/${id}`,
        payload,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

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
      }
      toast.error("Erro ao atualizar o orçamento. Verifique os dados.");
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
          <button className={styles.closeButton} onClick={onCloseAll}>
            <img src="/assets/iconsModal/iconX.svg" alt="Fechar" />
          </button>
        </div>

        {/* Input de valor */}
        <InputWithIcon
          label="Valor: "
          type="text"
          iconSrc="/assets/iconsModalOrcamentos/money.svg"
          placeholder="R$ 0,00"
          value={valor}
          onChange={handleChangeValor}
        />

        {/* Selecionar categoria */}
        <SelectedDespesas setCategoria={setCategoria} />

        {/* Data */}
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

        {/* Notificações */}
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
