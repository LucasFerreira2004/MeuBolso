import React, { useState, useEffect } from "react";
import axios from "axios";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-edit-contas.module.css";
import SelectedBancos from "../UI/SelectedBanco/selected-banco";
import SelectedTipoConta from "../UI/SelectedTipoConta/selected-tipo-conta";
import DatePicker from "../UI/DatePicker/date-picker";

interface ModalEditContasProps {
  onCloseAll: () => void; // Função para fechar o modal
  contaId: number; // ID da conta que será editada
  initialData: {
    saldo: number;
    id_banco: number;
    id_tipo_conta: number;
    data: string;
    descricao: string;
  }; // Dados iniciais da conta
}

const getDataAtual = () => {
  const data = new Date();
  const ano = data.getFullYear();
  const mes = String(data.getMonth() + 1).padStart(2, "0");
  const dia = String(data.getDate()).padStart(2, "0");
  return `${ano}-${mes}-${dia}`;
};

function ModalEditContas({ onCloseAll, contaId, initialData }: ModalEditContasProps) {
  const [saldo, setSaldo] = useState<string>(""); 
  const [bancoId, setBancoId] = useState<number | null>(null); 
  const [tipoContaId, setTipoContaId] = useState<number | null>(null); 
  const [data, setData] = useState<string>(getDataAtual()); 
  const [descricao, setDescricao] = useState<string>(""); 


  useEffect(() => {
    if (initialData) {
      setSaldo(`R$ ${initialData.saldo.toFixed(2).replace(".", ",")}`);
      setBancoId(initialData.id_banco);
      setTipoContaId(initialData.id_tipo_conta);
      setData(initialData.data);
      setDescricao(initialData.descricao);
    }
  }, [initialData]);

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
    if (!saldo || !bancoId || !tipoContaId || !data || !descricao) {
      alert("Preencha todos os campos obrigatórios!");
      return;
    }

    const saldoNumerico = removerFormatacaoMoeda(saldo);

    const token = localStorage.getItem("authToken");
    if (!token) {
      console.error("Token de autenticação não encontrado.");
      alert("Por favor, faça login novamente.");
      return;
    }

    try {
      const response = await axios.put(
        `http://localhost:8080/contas/${contaId}`,
        {
          saldo: saldoNumerico,
          id_banco: bancoId,
          id_tipo_conta: tipoContaId,
          data,
          descricao,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      console.log("Conta atualizada:", response.data);
      onCloseAll();
    } catch (error) {
      console.error("Erro ao atualizar conta:", error);
      alert("Erro ao atualizar conta. Verifique os dados ou tente novamente.");
    }
  };

  return (
    <div
      className={style.modalOverlay}
      onClick={(e: React.MouseEvent<HTMLDivElement>) => e.stopPropagation()}
    >
      <div className={style.modalContent}>
        <div className={style.headerModal}>
          <h3>Editar Conta</h3>
          <button className={style.closeButton} onClick={onCloseAll}>
            <img src="/assets/iconsModal/iconX.svg" alt="Fechar" />
          </button>
        </div>

        <InputWithIcon
          label="Saldo: "
          type="text"
          iconSrc="/assets/iconsModalConta/money.svg"
          placeholder="R$ 0,00"
          value={saldo}
          onChange={handleChangeSaldo}
        />

        <SelectedBancos setBanco={setBancoId} />

        <SelectedTipoConta setTipoConta={setTipoContaId} />

        <DatePicker
          value={data}
          onChange={setData}
          iconsrc="/assets/iconsModalConta/date.svg"
        />

        <InputWithIcon
          label="Descrição: "
          iconSrc="/assets/iconsModalConta/descricao.svg"
          placeholder="Ex: Conta Corrente"
          value={descricao}
          onChange={handleChangeDescricao}
        />

        <button onClick={handleSubmit} className={style.submitButton}>
          Atualizar Conta
        </button>
      </div>
    </div>
  );
}

export default ModalEditContas;