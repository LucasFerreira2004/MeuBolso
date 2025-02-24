import React, { useState, useEffect } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-edit-receita.module.css";
import SelectBoxContas from "../UI/SelectedBoxContas/selected-box-contas";
import DatePicker from "../UI/DatePicker/date-picker";
import SelectedPeriodo from "../UI/SelectedPeriodo/selected-periodo";
import SelectedReceita from "../UI/SelectedReceitas/selected-receita";

const removerFormatacaoMoeda = (valorFormatado: string): number => {
  const valorNumerico = valorFormatado
    .replace("R$ ", "")
    .replace(/\./g, "")
    .replace(",", ".");
  return parseFloat(valorNumerico);
};
interface TransactionData {
  id: number;
  valor: number;
  data: string;
  tipoTransacao: "NORMAL" | "FIXA" | "PARCELADA" | "RECEITA";
  categoriaId: number | null;
  contaId: number | null;
  comentario?: string | null;
  descricao: string;
  periodicidade?: "DIARIO" | "SEMANAL" | "MENSAL";
  qtdParcelas?: number | null;
}


const formatarValor = (valor: string): string => {
  let valorNumerico = valor.replace(/\D/g, "");
  valorNumerico = (Number(valorNumerico) / 100).toFixed(2);
  valorNumerico = valorNumerico.replace(".", ",");
  valorNumerico = valorNumerico.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
  return `R$ ${valorNumerico}`;
};

interface ModalEditReceitaProps {
  onClose: () => void;
  mes: number;
  ano: number;
  transactionId: number;
  onTransactionUpdate: (updatedTransaction: TransactionData | { id: number; deleted: boolean }) => void;
}


const ModalEditReceita: React.FC<ModalEditReceitaProps> = ({
  onClose,
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

    const transactionData: TransactionData = {
      valor: valorNumerico,
      data,
      tipoTransacao: "RECEITA",
      categoriaId: categoria,
      contaId: conta,
      comentario,
      descricao,
      periodicidade: tipoTransacao !== "NORMAL" ? periodicidade : undefined,
      qtdParcelas: tipoTransacao === "PARCELADA" ? qtdParcelas ?? undefined : undefined,
      id: 0
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
        onClose();
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
        onTransactionUpdate({ id: transactionId, deleted: true });
        onClose();
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
          <button className={style.closeButton} onClick={onClose}>
            <img src="/assets/iconsModal/iconX.svg" alt="Fechar" />
          </button>
        </div>

        <InputWithIcon
          label="Valor: "
          type="text"
          iconSrc="/assets/iconsModalReceitas/money.svg"
          placeholder="R$ 0,00"
          value={valor}
          onChange={handleChangeValor}
        />

        <InputWithIcon
          label="Descrição: "
          iconSrc="/assets/iconsModalReceitas/descrip.svg"
          placeholder="Ex: Pagamento da fatura"
          value={descricao}
          onChange={(e: { target: { value: React.SetStateAction<string> } }) =>
            setDescricao(e.target.value)
          }
        />

        <SelectedReceita setCategoria={setCategoria} />
        <SelectBoxContas setConta={setConta} mes={mes} ano={ano} />
        <DatePicker
          label="Escolha uma data:"
          value={data}
          onChange={setData}
          iconsrc="/assets/iconsModalReceitas/date.svg"
        />

        <InputWithIcon
          label="Comentário: "
          iconSrc="/assets/iconsModalReceitas/comentario.svg"
          placeholder="Opcional"
          value={comentario || ""}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
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
                iconSrc="/assets/iconsModalReceitas/parcelas.svg"
                type="number"
                placeholder="Ex: 12"
                value={qtdParcelas || ""}
                onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                  setQtdParcelas(e.target.value ? Number(e.target.value) : null)
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

export default ModalEditReceita;
