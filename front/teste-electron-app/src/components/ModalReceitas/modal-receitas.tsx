import React, { useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-receitas.module.css";
import SelectBoxContas from "../UI/SelectedBoxContas/selected-box-contas";
import DatePicker from "../UI/DatePicker/date-picker";
import SelectedReceita from "../UI/SelectedReceitas/selected-receita";

interface ModalReceitasProps {
  onCloseAll: () => void;
}

function ModalReceitas({ onCloseAll }: ModalReceitasProps) {
  const [valor, setValor] = useState<string>("");
  const [descricao, setDescricao] = useState<string>("");
  const [categoria, setCategoria] = useState<number | null>(null);
  const [conta, setConta] = useState<number | null>(null);
  const [data, setData] = useState<string>("");
  const [comentario, setComentario] = useState<string | null>(null);

  // Tipo de transação fixo como 'RECEITA'
  const tipoTransacao = "RECEITA";

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

      console.log("Receita adicionada:", response.data);
      toast.success("Receita adicionada com sucesso!");
      onCloseAll(); // Fecha os modais ao cadastrar
    } catch (error) {
      console.error("Erro ao adicionar receita:", error);
      toast.error("Erro ao adicionar receita. Verifique os dados ou tente novamente.");
    }
  };

  return (
    <div
      className={style.modalOverlay}
      onClick={(e: React.MouseEvent<HTMLDivElement>) => e.stopPropagation()}
    >
      <div className={style.modalContent}>
        <div className={style.headerModal}>
          <h3>Receitas</h3>
          <button className={style.closeButton} onClick={onCloseAll}>
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
          placeholder="Ex: Salário"
          value={descricao}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => setDescricao(e.target.value)}
        />
        <SelectedReceita setCategoria={setCategoria} />
        <SelectBoxContas setConta={setConta} />
        <DatePicker value={data} onChange={setData} iconsrc="/assets/iconsModalReceitas/date.svg" />
        <InputWithIcon
          label="Comentário: "
          iconSrc="/assets/iconsModalReceitas/comentario.svg"
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

export default ModalReceitas;
