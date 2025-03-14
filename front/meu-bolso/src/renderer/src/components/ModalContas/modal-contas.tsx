import React, { useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-contas.module.css";
import SelectedBancos from "../UI/SelectedBanco/selected-banco";
import SelectedTipoConta from "../UI/SelectedTipoConta/selected-tipo-conta";
import { baseUrl } from "../../api/api";

interface ModalContasProps {
  onCloseAll: () => void;
  showToast: (message: string, type: "success" | "error") => void;
}

function ModalContas({ onCloseAll, showToast }: ModalContasProps) {
  const [saldo, setSaldo] = useState<string>("");
  const [bancoId, setBancoId] = useState<number | null>(null);
  const [tipoContaId, setTipoContaId] = useState<number | null>(null);
  const [descricao, setDescricao] = useState<string>("");

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

  const handleChangeSaldo = (e: React.ChangeEvent<HTMLInputElement>) => {
    const valorDigitado = e.target.value;
    const valorFormatado = formatarMoeda(valorDigitado);
    setSaldo(valorFormatado);
  };

  const handleChangeDescricao = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDescricao(e.target.value);
  };

  const handleSubmit = async () => {
    if (!saldo || !bancoId || !tipoContaId  || !descricao) {
      toast.error("Preencha todos os campos obrigatórios!");
      return;
    }

    const saldoNumerico = removerFormatacaoMoeda(saldo);
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      toast.error("Por favor, faça login novamente.");
      return;
    }

    try {
      const response = await axios.post(
        `${baseUrl}/contas`,
        {
          saldo: saldoNumerico,
          id_banco: bancoId,
          id_tipo_conta: tipoContaId,
          descricao,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      showToast("Conta cadastrada com sucesso!", "success");
      onCloseAll();

      console.log("Conta cadastrada:", response.data);
    } catch (error) {
      console.error("Erro ao cadastrar conta:", error);
      toast.error("Erro ao cadastrar conta. Verifique os dados ou tente novamente.");
    }
  };

  return (
    <div
      className={style.modalOverlay}
      onClick={(e: React.MouseEvent<HTMLDivElement>) => e.stopPropagation()}
    >
      <div className={style.modalContent}>
        <div className={style.headerModal}>
          <h3>Cadastrar Conta</h3>
          <button className={style.closeButton} onClick={onCloseAll}>
            <img src="assets/iconsModal/iconX.svg" alt="Fechar" />
          </button>
        </div>

        <InputWithIcon
          label="Saldo: "
          type="text"
          iconSrc="assets/iconsModalConta/money.svg"
          placeholder="R$ 0,00"
          value={saldo}
          onChange={handleChangeSaldo}
        />

        <SelectedBancos setBanco={setBancoId} />

        <SelectedTipoConta setTipoConta={setTipoContaId} />

        <InputWithIcon
          label="Descrição: "
          iconSrc="assets/iconsModalConta/descricao.svg"
          placeholder="Ex: Conta Corrente"
          value={descricao}
          onChange={handleChangeDescricao}
        />

        <button onClick={handleSubmit} className={style.submitButton}>
          Cadastrar Conta
        </button>
      </div>
    </div>
  );
}

export default ModalContas;