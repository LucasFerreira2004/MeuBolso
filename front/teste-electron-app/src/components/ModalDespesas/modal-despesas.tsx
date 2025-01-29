import React, { useState } from "react";
import axios from "axios";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-despesas.module.css";

interface ModalADespesasProps {
  onClose: () => void; // Função para fechar o modal
}

function ModalDespesas({ onClose }: ModalADespesasProps) {
  const [valor, setValor] = useState<number>(0);
  const [descricao, setDescricao] = useState<string>("");
  const [categoria, setCategoria] = useState<string>("");
  const [conta, setConta] = useState<string>("");
  const [data, setData] = useState<string>("");
  const [comentario, setComentario] = useState<string | null>(null);
  const [tipoTransacao, setTipoTransacao] = useState<string>("");

  const handleSubmit = async () => {
    try {
      const response = await axios.post("http://localhost:8080/transacoes", {
        valor,
        data,
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
    setValor(Number(e.target.value));
  };

  const handleChangeDescricao = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDescricao(e.target.value);
  };

  const handleChangeCategoria = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCategoria(e.target.value);
  };

  const handleChangeConta = (e: React.ChangeEvent<HTMLInputElement>) => {
    setConta(e.target.value);
  };

  const handleChangeData = (e: React.ChangeEvent<HTMLInputElement>) => {
    setData(e.target.value);
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
        <div className={style.saldo}>
          <label>Saldo:</label>
          <input
            type="number"
            placeholder="R$ 0,00"
            className={style.inputSaldo}
            value={valor}
            onChange={handleChangeValor}
          />
        </div>
        <InputWithIcon
          label="Descrição: "
          iconSrc="/assets/iconsModal/icondescription.svg"
          placeholder="Descrição"
          value={descricao}
          onChange={handleChangeDescricao}
        />
        <InputWithIcon
          label="Categoria: "
          iconSrc="/assets/iconsModal/icontag.svg"
          placeholder="Categoria"
          value={categoria}
          onChange={handleChangeCategoria}
        />
        <InputWithIcon
          label="Conta: "
          iconSrc="/assets/iconsModal/iconbankmodal.svg"
          placeholder="Conta"
          value={conta}
          onChange={handleChangeConta}
        />
        <InputWithIcon
          label="Data: "
          iconSrc="/assets/iconsModal/iconcalendario.svg"
          placeholder="Data"
          type="date"
          value={data}
          onChange={handleChangeData}
        />
        <InputWithIcon
          label="Comentário: "
          iconSrc="/assets/iconsModal/icondescriptionplus.svg"
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
