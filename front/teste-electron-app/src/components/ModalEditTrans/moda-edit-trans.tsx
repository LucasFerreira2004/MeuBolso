import React, { useState, useEffect } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./moda-edit-trans.module.css";
import SelectBoxContas from "../UI/SelectedBoxContas/selected-box-contas";
import DatePicker from "../UI/DatePicker/date-picker";
import SelectedDespesas from "../UI/SelectedDespesa/selected-despesa";
import SelectedPeriodo from "../UI/SelectedPeriodo/selected-periodo";

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

interface ModalEditTransProps {
  onCloseAll: () => void;
  mes: number;
  ano: number;
  transactionId: number;
  onTransactionUpdate: (updatedTransaction: any) => void;  // Aceita um parâmetro
}

function ModalEditTrans({ onCloseAll, mes, ano, transactionId, onTransactionUpdate }: ModalEditTransProps) {
  const [valor, setValor] = useState<string>("");
  const [descricao, setDescricao] = useState<string>("");
  const [categoria, setCategoria] = useState<number | null>(null);
  const [conta, setConta] = useState<number | null>(null);
  const [data, setData] = useState<string>("");
  const [comentario, setComentario] = useState<string | null>(null);
  const [tipoTransacao, setTipoTransacao] = useState<"NORMAL" | "FIXA" | "PARCELADA">("NORMAL");
  const [periodicidade, setPeriodicidade] = useState<"SEMANAL" | "MENSAL" | "DIARIO">("MENSAL");
  const [qtdParcelas, setQtdParcelas] = useState<number | null>(null);

  useEffect(() => {
    const fetchTransaction = async () => {
      const token = localStorage.getItem("authToken");
  
      if (!token) {
        console.error("Token de autenticação não encontrado.");
        toast.error("Por favor, faça login novamente.");
        return;
      }
  
      try {
        const response = await axios.get(`http://localhost:8080/transacoes/${transactionId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        const data = response.data;
  
        setValor(formatarComoMoeda(data.valor.toString()));
        setDescricao(data.descricao);
        setCategoria(data.categoriaId);
        setConta(data.contaId);
        setData(data.data);
        setComentario(data.comentario);
        
        // Ajustando tipoTransacao ao valor recebido da API
        setTipoTransacao(data.tipoTransacao || "NORMAL");
        
        if (data.tipoTransacao === "FIXA" || data.tipoTransacao === "PARCELADA") {
          setPeriodicidade(data.periodicidade);
        }
        if (data.tipoTransacao === "PARCELADA") {
          setQtdParcelas(data.qtdParcelas);
        }
      } catch (error) {
        toast.error("Erro ao carregar os dados da transação.");
        console.error(error);
      }
    };

    fetchTransaction();
  }, [transactionId]);
  
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

    const transactionData: any = {
      valor: valorNumerico,
      data,
      tipoTransacao: tipoTransacao === "NORMAL" ? "DESPESA" : tipoTransacao === "FIXA" ? "DESPESA" : "DESPESA",
      categoriaId: categoria,
      contaId: conta,
      comentario,
      descricao,
    };

    if (tipoTransacao === "FIXA") {
      transactionData.periodicidade = periodicidade;
    }

    if (tipoTransacao === "PARCELADA") {
      transactionData.qtdParcelas = qtdParcelas;
      transactionData.periodicidade = periodicidade;
    }

    try {
      const url = `http://localhost:8080/transacoes/${transactionId}`; 

      const response = await axios.put(url, transactionData, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (response.status === 200) {
        toast.success("Transação atualizada com sucesso!");
        onTransactionUpdate(response.data);  // Passando o valor atualizado
        onCloseAll();
      } else {
        toast.error("Erro ao atualizar transação. Verifique os dados ou tente novamente.");
      }
    } catch (error) {
      console.error("Erro ao atualizar transação:", error);
      toast.error("Erro ao atualizar transação. Verifique os dados ou tente novamente.");
    }
  };

  return (
    <div
      className={style.modalOverlay}
      onClick={(e: React.MouseEvent<HTMLDivElement>) => e.stopPropagation()}
    >
      <div className={style.modalContent}>
        <div className={style.headerModal}>
          <h3>Editar Transação</h3>
          <button className={style.closeButton} onClick={onCloseAll}>
            <img src="/assets/iconsModal/iconX.svg" alt="Fechar" />
          </button>
        </div>
        <InputWithIcon
          label="Valor: "
          type="text"
          iconSrc="/assets/iconsModalDespesas/money.svg"
          placeholder="R$ 0,00"
          value={valor}
          onChange={handleChangeValor}
        />
        <InputWithIcon
          label="Descrição: "
          iconSrc="/assets/iconsModalDespesas/descrip.svg"
          placeholder="Ex: Pagamento da fatura"
          value={descricao}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => setDescricao(e.target.value)}
        />
        <SelectedDespesas setCategoria={setCategoria} />
        <SelectBoxContas setConta={setConta} mes={mes} ano={ano} />
        <DatePicker value={data} onChange={setData} iconsrc="/assets/iconsModalDespesas/date.svg" />
        <InputWithIcon
          label="Comentário: "
          iconSrc="/assets/iconsModalDespesas/comentario.svg"
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
                iconSrc="/assets/iconsModalDespesas/parcelas.svg"
                type="number"
                placeholder="Ex: 12"
                value={qtdParcelas || ""}
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => setQtdParcelas(Number(e.target.value))}
              />
            )}
          </>
        )}

        <button onClick={handleSubmit} className={style.submitButton}>
          Atualizar Transação
        </button>

        <ToastContainer
          position="top-right"
          autoClose={3000}
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

export default ModalEditTrans;
