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
    .replace(/[^\d,.-]/g, "")
    .replace(",", ".");
  return parseFloat(valorNumerico);
};

const formatarValor = (valor: string): string => {
  const valorNumerico = parseFloat(valor.replace(/[^\d.-]/g, ""));
  if (isNaN(valorNumerico)) return "R$ 0,00";
  return valorNumerico.toLocaleString("pt-BR", {
    style: "currency",
    currency: "BRL",
  });
};

interface ModalEditTransProps {
  onCloseAll: () => void;
  mes: number;
  ano: number;
  transactionId: number;
  onTransactionUpdate: (updatedTransaction: any) => void;
}

const ModalEditTrans: React.FC<ModalEditTransProps> = ({
  onCloseAll,
  mes,
  ano,
  transactionId,
  onTransactionUpdate,
}) => {
  const [valor, setValor] = useState<string>("");
  const [descricao, setDescricao] = useState<string>("");
  const [categoria, setCategoria] = useState<number | null>(null);
  const [conta, setConta] = useState<number | null>(null);
  const [data, setData] = useState<string>("");
  const [comentario, setComentario] = useState<string | null>(null);
  const [tipoTransacao, setTipoTransacao] = useState<
    "NORMAL" | "FIXA" | "PARCELADA"
  >("NORMAL");
  const [periodicidade, setPeriodicidade] = useState<
    "SEMANAL" | "MENSAL" | "DIARIO"
  >("MENSAL");
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
        const response = await axios.get(
          `http://localhost:8080/transacoes/${transactionId}`,
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );

        const data = response.data;
        setValor(formatarValor(data.valor.toString()));
        setDescricao(data.descricao || "");
        setCategoria(data.categoriaId || null);
        setConta(data.contaId || null);
        setData(data.data || "");
        setComentario(data.comentario || null);
        setTipoTransacao(data.tipoTransacao || "NORMAL");

        if (
          data.tipoTransacao === "FIXA" ||
          data.tipoTransacao === "PARCELADA"
        ) {
          setPeriodicidade(data.periodicidade || "MENSAL");
        }
        if (data.tipoTransacao === "PARCELADA") {
          setQtdParcelas(data.qtdParcelas || null);
        }
      } catch (error) {
        toast.error("Erro ao carregar os dados da transação.");
        console.error("Erro ao buscar transação:", error);
      }
    };

    fetchTransaction();
  }, [transactionId]);

  const handleChangeValor = (e: React.ChangeEvent<HTMLInputElement>) => {
    const valorDigitado = e.target.value;
    const valorFormatado = formatarValor(valorDigitado);
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
      toast.error("Por favor, faça login novamente.");
      return;
    }

    const transactionData: any = {
      valor: valorNumerico,
      data,
      tipoTransacao: "DESPESA",
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
      const response = await axios.put(
        `http://localhost:8080/transacoes/${transactionId}`,
        transactionData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      if (response.status === 200) {
        toast.success("Transação atualizada com sucesso!");
        onTransactionUpdate(response.data);
        onCloseAll();
      } else {
        toast.error("Erro ao atualizar a transação.");
      }
    } catch (error) {
      toast.error("Erro ao atualizar a transação. Tente novamente.");
      console.error("Erro ao enviar atualização:", error);
    }
  };

  const handleDelete = async () => {
    const token = localStorage.getItem("authToken");

    if (!token) {
      toast.error("Por favor, faça login novamente.");
      return;
    }

    try {
      const response = await axios.delete(
        `http://localhost:8080/transacoes/${transactionId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );

      if (response.status === 200) {
        toast.success("Transação excluída com sucesso!");
        onCloseAll();
      } else {
        toast.error("Erro ao excluir a transação.");
      }
    } catch (error) {
      toast.error("Erro ao excluir a transação. Tente novamente.");
      console.error("Erro ao excluir transação:", error);
    }
  };

  return (
    <div className={style.modalOverlay} onClick={(e) => e.stopPropagation()}>
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
          onChange={(e: { target: { value: React.SetStateAction<string> } }) =>
            setDescricao(e.target.value)
          }
        />

        <SelectedDespesas setCategoria={setCategoria} />
        <SelectBoxContas setConta={setConta} mes={mes} ano={ano} />
        <DatePicker
          label="Escolha uma data:"
          value={data}
          onChange={setData}
          iconsrc="/assets/iconsModalDespesas/date.svg"
        />

        <InputWithIcon
          label="Comentário: "
          iconSrc="/assets/iconsModalDespesas/comentario.svg"
          placeholder="Opcional"
          value={comentario || ""}
          onChange={(e: { target: { value: any } }) =>
            setComentario(e.target.value || null)
          }
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
              onChange={(e) =>
                setPeriodicidade(
                  e.target.value as "DIARIO" | "SEMANAL" | "MENSAL"
                )
              }
            />
            {tipoTransacao === "PARCELADA" && (
              <InputWithIcon
                label="Quantidade de Parcelas:"
                iconSrc="/assets/iconsModalDespesas/parcelas.svg"
                type="number"
                placeholder="Ex: 12"
                value={qtdParcelas || ""}
                onChange={(e: { target: { value: any } }) =>
                  setQtdParcelas(Number(e.target.value))
                }
              />
            )}
          </>
        )}

        <div className={style.Buttons}>
          <button onClick={handleDelete} className={style.deleteButton}>
            Excluir Transação
          </button>
          <button onClick={handleSubmit} className={style.submitButton}>
            Atualizar Transação
          </button>
        </div>

        <ToastContainer
          position="top-right"
          autoClose={3000}
          hideProgressBar={false}
          closeOnClick
          pauseOnHover
        />
      </div>
    </div>
  );
};

export default ModalEditTrans;
