import React, { useState } from "react";
import axios from "axios";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-despesas.module.css";
import SelectBoxContas from "../UI/SelectedBoxContas/selected-box-contas";
import DatePicker from "../UI/DatePicker/date-picker"; 
import SelectedDespesas from "../UI/SelectedDespesa/selected-despesa";

interface ModalADespesasProps {
  onCloseAll: () => void; // Agora fecha tudo ao cadastrar
}

function ModalDespesas({ onCloseAll }: ModalADespesasProps) {
  const [valor, setValor] = useState<string>("");
  const [descricao, setDescricao] = useState<string>("");
  const [categoria, setCategoria] = useState<number | null>(null);
  const [conta, setConta] = useState<number | null>(null);
  const [data, setData] = useState<string>(""); 
  const [comentario, setComentario] = useState<string | null>(null);

  // Tipo de transação fixo como 'DESPESA'
  const tipoTransacao = "DESPESA";

  const formatarMoeda = (valor: string): string => {
    let valorNumerico = valor.replace(/\D/g, ""); 
    valorNumerico = (Number(valorNumerico) / 100).toFixed(2);
    valorNumerico = valorNumerico.replace(".", ",");
    valorNumerico = valorNumerico.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
    return `R$ ${valorNumerico}`;
  };

  const removerFormatacaoMoeda = (valorFormatado: string): number => {
    const valorNumerico = valorFormatado
      .replace("R$ ", "")
      .replace(/\./g, "")
      .replace(",", ".");
    return parseFloat(valorNumerico);
  };

  const handleChangeValor = (e: React.ChangeEvent<HTMLInputElement>) => {
    const valorDigitado = e.target.value;
    const valorFormatado = formatarMoeda(valorDigitado);
    setValor(valorFormatado);
  };

  const handleSubmit = async () => {
    if (!valor || !descricao || !categoria || !data || !conta) {
      alert("Preencha todos os campos obrigatórios!");
      return;
    }

    const valorNumerico = removerFormatacaoMoeda(valor);

    // Obtendo o token de autenticação
    const token = localStorage.getItem("authToken");
    if (!token) {
      console.error("Token de autenticação não encontrado.");
      alert("Por favor, faça login novamente.");
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/transacoes",
        {
          valor: valorNumerico,
          data,
          tipoTransacao,
          categoriaId: categoria,
          contaId: conta,
          comentario,
          descricao,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      console.log("Transação adicionada:", response.data);
      onCloseAll(); // Fecha os dois modais ao cadastrar
    } catch (error) {
      console.error("Erro ao adicionar transação:", error);
      alert("Erro ao adicionar transação. Verifique os dados ou tente novamente.");
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
        <SelectBoxContas setConta={setConta} />
        <DatePicker value={data} onChange={setData} iconsrc="/assets/iconsModalDespesas/date.svg"/>
        <InputWithIcon
          label="Comentário: "
          iconSrc="/assets/iconsModalDespesas/comentario.svg"
          placeholder="Opcional"
          value={comentario || ""}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => setComentario(e.target.value || null)}
        />
        <button onClick={handleSubmit} className={style.submitButton}>
          Adicionar Transação
        </button>
      </div>
    </div>
  );
}

export default ModalDespesas;
