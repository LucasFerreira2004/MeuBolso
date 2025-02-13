import React, { useState } from "react";
import axios from "axios";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-contas.module.css";
import SelectedBancos from "../UI/SelectedBanco/selected-banco";
import SelectedTipoConta from "../UI/SelectedTipoConta/selected-tipo-conta";
import DatePicker from "../UI/DatePicker/date-picker";

interface ModalContasProps {
  onCloseAll: () => void; // Função para fechar o modal
}

// Definindo a função getDataAtual antes de usá-la
const getDataAtual = () => {
  const data = new Date();
  const ano = data.getFullYear();
  const mes = String(data.getMonth() + 1).padStart(2, "0");
  const dia = String(data.getDate()).padStart(2, "0");
  return `${ano}-${mes}-${dia}`;
};

function ModalContas({ onCloseAll }: ModalContasProps) {
  const [saldo, setSaldo] = useState<string>(""); // Saldo da conta
  const [bancoId, setBancoId] = useState<number | null>(null); // ID do banco
  const [tipoContaId, setTipoContaId] = useState<number | null>(null); // ID do tipo de conta
  const [data, setData] = useState<string>(getDataAtual()); // Inicializa com a data atual
  const [descricao, setDescricao] = useState<string>(""); // Descrição da conta

  // Função para formatar o saldo como moeda
  const formatarMoeda = (valor: string): string => {
    let valorNumerico = valor.replace(/\D/g, "");
    valorNumerico = (Number(valorNumerico) / 100).toFixed(2);
    valorNumerico = valorNumerico.replace(".", ",");
    valorNumerico = valorNumerico.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
    return `R$ ${valorNumerico}`;
  };

  // Função para remover a formatação de moeda
  const removerFormatacaoMoeda = (valorFormatado: string): number => {
    const valorNumerico = valorFormatado
      .replace("R$ ", "")
      .replace(/\./g, "")
      .replace(",", ".");
    return parseFloat(valorNumerico);
  };

  // Função para atualizar o saldo
  const handleChangeSaldo = (e: React.ChangeEvent<HTMLInputElement>) => {
    const valorDigitado = e.target.value;
    const valorFormatado = formatarMoeda(valorDigitado);
    setSaldo(valorFormatado);
  };

  // Função para atualizar a descrição
  const handleChangeDescricao = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDescricao(e.target.value);
  };

  // Função para enviar os dados para o backend
  const handleSubmit = async () => {
    if (!saldo || !bancoId || !tipoContaId || !data || !descricao) {
      alert("Preencha todos os campos obrigatórios!");
      return;
    }
  
    // Se necessário, converta a data para o formato adequado
    const dataFormatada = new Date(data);
    const dataISO = dataFormatada.toISOString().split('T')[0]; // Formata como "YYYY-MM-DD"
  
    const saldoNumerico = removerFormatacaoMoeda(saldo);
  
    const token = localStorage.getItem("authToken");
    if (!token) {
      console.error("Token de autenticação não encontrado.");
      alert("Por favor, faça login novamente.");
      return;
    }
  
    try {
      const response = await axios.post(
        "http://localhost:8080/contas",
        {
          saldo: saldoNumerico,
          id_banco: bancoId,
          id_tipo_conta: tipoContaId,
          data: dataISO, // Envia a data no formato correto
          descricao,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );
  
      console.log("Conta cadastrada:", response.data);
      onCloseAll(); // Fecha o modal após o cadastro
    } catch (error) {
      console.error("Erro ao cadastrar conta:", error);
      alert("Erro ao cadastrar conta. Verifique os dados ou tente novamente.");
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
            <img src="/assets/iconsModal/iconX.svg" alt="Fechar" />
          </button>
        </div>

        <InputWithIcon
          label="Saldo: "
          type="text"
          iconSrc="/assets/iconsModalContas/money.svg"
          placeholder="R$ 0,00"
          value={saldo}
          onChange={handleChangeSaldo}
        />

        <SelectedBancos setBanco={setBancoId} />

        <SelectedTipoConta setTipoConta={setTipoContaId} />

        <DatePicker
          value={data} // Agora a data é inicializada corretamente
          onChange={setData} // Atualiza o estado com a data selecionada
          iconsrc="/assets/iconsModalContas/date.svg"
        />

        <InputWithIcon
          label="Descrição: "
          iconSrc="/assets/iconsModalContas/descricao.svg"
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
