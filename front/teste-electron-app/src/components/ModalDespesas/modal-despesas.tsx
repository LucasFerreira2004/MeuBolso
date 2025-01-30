// src/components/ModalDespesas/ModalDespesas.tsx
import React, { useState } from "react";
import axios from "axios";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-despesas.module.css";
import SelectBoxDespesas from "../UI/SelectedBoxContas/selected-box-contas";
import DatePicker from "../UI/DatePicker/date-picker"; // Componente atualizado

interface ModalADespesasProps {
  onClose: () => void;
}

function ModalDespesas({ onClose }: ModalADespesasProps) {
  const [valor, setValor] = useState<number>(0); // Adicionei um estado para o valor
  const [descricao, setDescricao] = useState<string>("");
  const [categoria, setCategoria] = useState<string>("");
  const [conta] = useState<string>(""); // Valor da conta
  const [data, setData] = useState<string>(""); // Estado da data
  const [comentario, setComentario] = useState<string | null>(null);
  const [tipoTransacao, setTipoTransacao] = useState<string>("");

  const handleSubmit = async () => {
    try {
      const response = await axios.post("http://localhost:8080/transacoes", {
        valor,
        data, // A data será enviada com a transação
        tipoTransacao,
        categoriaId: parseInt(categoria), // Aqui você pode mudar para usar o ID correto
        contaId: parseInt(conta), // Aqui você pode mudar para usar o ID correto
        comentario,
        descricao,
      });

      console.log("Transação adicionada:", response.data);
      onClose(); // Fecha o modal de Despesas após a transação ser adicionada
    } catch (error) {
      console.error("Erro ao adicionar transação:", error);
    }
  };

  const handleChangeValor = (e: React.ChangeEvent<HTMLInputElement>) => {
    setValor(parseFloat(e.target.value)); // Atualiza o estado do valor
  };

  const handleChangeDescricao = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDescricao(e.target.value);
  };

  const handleChangeCategoria = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCategoria(e.target.value);
  };

  const handleChangeComentario = (e: React.ChangeEvent<HTMLInputElement>) => {
    setComentario(e.target.value || null);
  };

  const handleChangeTipoTransacao = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTipoTransacao(e.target.value);
  };

  return (
    <div className={style.modalOverlay} onClick={(e) => e.stopPropagation()}>
      <div className={style.modalContent}>
        <div className={style.headerModal}>
          <h3>Despesas</h3>
          <button className={style.closeButton} onClick={onClose}>
            <img src="/assets/iconsModal/iconX.svg" alt="Fechar" />
          </button>
        </div>
        <InputWithIcon
          label="Saldo: "
          type="number"
          iconSrc="/assets/iconsModalDelete/money.svg"
          placeholder="R$ 0,00"
          value={valor.toString()} // Use o estado `valor`
          onChange={handleChangeValor} // Atualiza o estado `valor`
        />
        <InputWithIcon
          label="Descrição: "
          iconSrc="/assets/iconsModalDelete/descrip.svg"
          placeholder="Descrição"
          value={descricao}
          onChange={handleChangeDescricao}
        />
        <InputWithIcon
          label="Categoria: "
          iconSrc="/assets/iconsModalDelete/hashtag.svg"
          placeholder="Categoria"
          value={categoria}
          onChange={handleChangeCategoria}
        />
        <SelectBoxDespesas />

        <DatePicker value={data} onChange={setData} />

        <InputWithIcon
          label="Comentário: "
          iconSrc="/assets/iconsModalDelete/descrip.svg"
          placeholder="Comentário"
          value={comentario || ""}
          onChange={handleChangeComentario}
        />
        <div className={style.radioGroup}>
          <label>
            <input
              type="radio"
              name="tipoTransacao"
              value="DESPESA"
              onChange={handleChangeTipoTransacao}
            />
            Despesa
          </label>
          <label>
            <input
              type="radio"
              name="tipoTransacao"
              value="RECEITA"
              onChange={handleChangeTipoTransacao}
            />
            Receita
          </label>
        </div>
        <button onClick={handleSubmit} className={style.submitButton}>
          Adicionar Transação
        </button>
      </div>
    </div>
  );
}

export default ModalDespesas;
