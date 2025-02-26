import React, { useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-despesas.module.css";
import SelectBoxContas from "../UI/SelectedBoxContas/selected-box-contas";
import DatePicker from "../UI/DatePicker/date-picker";
import SelectedDespesas from "../UI/SelectedDespesa/selected-despesa";
import SelectedPeriodo from "../UI/SelectedPeriodo/selected-periodo";
import { baseUrl } from "../../api/api";

interface TransactionData {
  valor: number;
  data: string;
  tipoTransacao: "DESPESA";
  categoriaId: number | null;
  contaId: number | null;
  comentario: string | null;
  descricao: string;
  periodicidade?: "DIARIO" | "SEMANAL" | "MENSAL";
  qtdParcelas?: number | null; // Aceita `number` ou `null`
}

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

interface ModalDespesasProps {
  onCloseAll: () => void;
  mes: number;
  ano: number;
}

function ModalDespesas({ onCloseAll, mes, ano }: ModalDespesasProps) {
  const [valor, setValor] = useState<string>("");
  const [descricao, setDescricao] = useState<string>("");
  const [categoria, setCategoria] = useState<number | null>(null);
  const [conta, setConta] = useState<number | null>(null);
  const [data, setData] = useState<string>("");
  const [comentario, setComentario] = useState<string | null>(null);
  const [tipoTransacao, setTipoTransacao] = useState<"NORMAL" | "FIXA" | "PARCELADA">("NORMAL");
  const [periodicidade, setPeriodicidade] = useState<"SEMANAL" | "MENSAL" | "DIARIO">("MENSAL");
  const [qtdParcelas, setQtdParcelas] = useState<number | null>(null);

  const handleChangeValor = (e: React.ChangeEvent<HTMLInputElement>) => {
    const valorDigitado = e.target.value;
    const valorFormatado = formatarComoMoeda(valorDigitado);
    setValor(valorFormatado);
  };

  const handleSubmit = async () => {
    if (!valor || !descricao || !categoria || !data || !conta) {
      toast.error("Preencha todos os campos obrigatórios!");
      return;
    }

    const valorNumerico = removerFormatacaoMoeda(valor);
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      toast.error("Por favor, faça login novamente.");
      return;
    }

    // Dados comuns a todas as transações
    const transactionData: TransactionData = {
      valor: valorNumerico,
      data,
      tipoTransacao: "DESPESA", // Definido como "DESPESA" para todos os tipos
      categoriaId: categoria,
      contaId: conta,
      comentario,
      descricao,
    };

    // Adicionar dados específicos para transações "FIXA"
    if (tipoTransacao === "FIXA") {
      transactionData.periodicidade = periodicidade;
    }

    // Adicionar dados específicos para transações "PARCELADA"
    if (tipoTransacao === "PARCELADA") {
      transactionData.qtdParcelas = qtdParcelas;
      transactionData.periodicidade = periodicidade;
    }

    console.log("Dados da transação sendo enviados:", transactionData);

    try {
      let url = `${baseUrl}/transacoes`;

      if (tipoTransacao === "FIXA") {
        url = `${baseUrl}/transacoesRecorrentes/fixas`;
      } else if (tipoTransacao === "PARCELADA") {
        url = `${baseUrl}/transacoesRecorrentes/parceladas`;
      }

      console.log("URL da requisição:", url);

      const response = await axios.post(url, transactionData, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      console.log("Resposta do servidor:", response.data);

      if (response.status === 200 || response.status === 201) {
        toast.success("Transação adicionada com sucesso!");
        onCloseAll();
      } else {
        toast.error("Erro ao adicionar transação. Verifique os dados ou tente novamente.");
      }
    } catch (error) {
      console.error("Erro ao adicionar transação:", error);
      if (axios.isAxiosError(error)) {
        console.log("Detalhes do erro:", error.response?.data);
      }
      toast.error("Erro ao adicionar transação. Verifique os dados ou tente novamente.");
    }
  };

  return (
    <div
      className={style.modalOverlay}
      onClick={(e: React.MouseEvent<HTMLDivElement>) => e.stopPropagation()}
    >
      <div className={style.modalContent}>
        <div className={style.headerModal}>
          <h3>Despesas</h3>
          <button className={style.closeButton} onClick={onCloseAll}>
            <img src="assets/iconsModal/iconX.svg" alt="Fechar" />
          </button>
        </div>
        <InputWithIcon
          label="Valor: "
          type="text"
          iconSrc="assets/iconsModalDespesas/money.svg"
          placeholder="R$ 0,00"
          value={valor}
          onChange={handleChangeValor}
        />
        <InputWithIcon
          label="Descrição: "
          iconSrc="assets/iconsModalDespesas/descrip.svg"
          placeholder="Ex: Pagamento da fatura"
          value={descricao}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => setDescricao(e.target.value)}
        />
        <SelectedDespesas setCategoria={setCategoria} />
        <SelectBoxContas setConta={setConta} mes={mes} ano={ano} />
        <DatePicker label="Escolha uma data:" value={data} onChange={setData} iconsrc="assets/iconsModalDespesas/date.svg" />
        <InputWithIcon
          label="Comentário: "
          iconSrc="assets/iconsModalDespesas/comentario.svg"
          placeholder="Opcional"
          value={comentario || ""}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => setComentario(e.target.value || null)}
        />

        <div>
          <label>
            <input
              type="radio"
              value="NORMAL"
              checked={tipoTransacao === "NORMAL"}
              onChange={() => setTipoTransacao("NORMAL")}
            />
            Normal
          </label>
          <label>
            <input
              type="radio"
              value="FIXA"
              checked={tipoTransacao === "FIXA"}
              onChange={() => setTipoTransacao("FIXA")}
            />
            Fixa
          </label>
          <label>
            <input
              type="radio"
              value="PARCELADA"
              checked={tipoTransacao === "PARCELADA"}
              onChange={() => setTipoTransacao("PARCELADA")}
            />
            Parcelada
          </label>
        </div>

        {tipoTransacao !== "NORMAL" && (
          <>
            <SelectedPeriodo
              selectedValue={periodicidade}
              onChange={(e) => setPeriodicidade(e.target.value as "DIARIO" | "SEMANAL" | "MENSAL")}
            />
            {tipoTransacao === "PARCELADA" && (
              <InputWithIcon
                label="Quantidade de Parcelas:"
                type="number"
                placeholder="Ex: 12"
                value={qtdParcelas || ""}
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => setQtdParcelas(Number(e.target.value))}
              />
            )}
          </>
        )}

        <button onClick={handleSubmit} className={style.submitButton}>
          Adicionar Transação
        </button>

        {/* Adicione o ToastContainer aqui */}
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

export default ModalDespesas;