import React, { useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import styles from "./modal-add-orcamento.module.css";
import InputWithIcon from "../UI/InputsModal/input-modal";
import DatePicker from "../UI/DatePicker/date-picker";
import SelectedDespesas from "../UI/SelectedDespesa/selected-despesa";
import { baseUrl } from "../../api/api";

const removerFormatacaoMoeda = (valorFormatado: string): number => {
  const valorNumerico = valorFormatado
    .replace("R$ ", "")
    .replace(/\./g, "")
    .replace(",", ".");
  return parseFloat(valorNumerico);
};

const formatarComoMoeda = (valor: string): string => {
  let valorNumerico = valor.replace(/\D/g, "");
  valorNumerico = (parseInt(valorNumerico) / 100).toFixed(2);
  return `R$ ${valorNumerico.replace(".", ",")}`;
};

interface ModalAddOrcamentoProps {
  onCloseAll: () => void;
  onAddOrcamentoSuccess: () => void; 
}

const ModalAddOrcamento: React.FC<ModalAddOrcamentoProps> = ({ onCloseAll, onAddOrcamentoSuccess }) => {
  const [valor, setValor] = useState<string>("");
  const [data, setData] = useState<string>("");
  const [categoria, setCategoria] = useState<number | null>(null);

  const handleChangeValor = (e: React.ChangeEvent<HTMLInputElement>) => {
    const valorDigitado = e.target.value;
    const valorFormatado = formatarComoMoeda(valorDigitado);
    setValor(valorFormatado);
  };

  const handleSubmit = async () => {
    if (!valor || !data || !categoria) {
      toast.error("Preencha todos os campos obrigatórios!");
      return;
    }

    const valorNumerico = removerFormatacaoMoeda(valor);
    const token = localStorage.getItem("authToken");

    if (!token) {
      toast.error("Por favor, faça login novamente.");
      return;
    }

    const payload = {
      valorEstimado: valorNumerico,
      periodo: data,
      idCategoria: categoria,
    };

    try {
      const response = await axios.post(`${baseUrl}/orcamentos`, payload, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (response.status === 200 || response.status === 201) {
        toast.success("Orçamento adicionado com sucesso!");
        onCloseAll();
        onAddOrcamentoSuccess(); // Chama a função de callback para atualizar a lista
      } else {
        toast.error("Erro ao adicionar o orçamento. Tente novamente.");
      }
    } catch (error) {
      console.error("Erro na requisição:", error);
      if (axios.isAxiosError(error)) {
        console.log("Detalhes do erro:", error.response?.data);
      }
      toast.error("Erro ao adicionar o orçamento. Verifique os dados.");
    }
  };

  return (
    <div
      className={styles.modalOverlay}
      onClick={(e: React.MouseEvent<HTMLDivElement>) => e.stopPropagation()}
    >
      <div className={styles.modalContent}>
        <div className={styles.headerModal}>
          <h3>Novo Orçamento</h3>
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
          onChange={setData}
          iconsrc="/assets/iconsModalOrcamentos/date.svg"
        />

        <div className={styles.footerModal}>
          <button className={styles.saveButton} onClick={handleSubmit}>
            Salvar
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
};

export default ModalAddOrcamento;
