import React, { useState, useEffect } from "react";
import axios from "axios";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-edit-contas.module.css";
import SelectedBancos from "../UI/SelectedBanco/selected-banco";
import SelectedTipoConta from "../UI/SelectedTipoConta/selected-tipo-conta";
import DatePicker from "../UI/DatePicker/date-picker";

interface ModalEditContasProps {
  onCloseAll: () => void; // Função para fechar o modal
  contaId: number; // ID da conta para editar
}

function ModalEditContas({ onCloseAll, contaId }: ModalEditContasProps) {
  const [saldo, setSaldo] = useState<string>("");
  const [bancoId, setBancoId] = useState<number | null>(null);
  const [tipoContaId, setTipoContaId] = useState<number | null>(null);
  const [data, setData] = useState<string>("");
  const [descricao, setDescricao] = useState<string>("");

  useEffect(() => {
    // Carregar os dados da conta para edição
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      return;
    }

    axios
      .get(`http://localhost:8080/contas/${contaId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        const conta = response.data;
        setSaldo(conta.saldo.toFixed(2));
        setBancoId(conta.id_banco);
        setTipoContaId(conta.id_tipo_conta);
        setData(conta.data);
        setDescricao(conta.descricao);
      })
      .catch((error) => {
        console.error("Erro ao carregar dados da conta:", error);
      });
  }, [contaId]);

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
      const payload = {
        saldo: saldoNumerico,
        id_banco: bancoId,
        id_tipo_conta: tipoContaId,
        data,
        descricao,
      };

      const response = await axios.put(`http://localhost:8080/contas/${contaId}`, payload, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
      console.log("Conta editada:", response.data);
      onCloseAll(); // Fecha o modal após a edição
    } catch (error) {
      console.error("Erro ao editar conta:", error);
      alert("Erro ao editar conta. Verifique os dados ou tente novamente.");
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
          iconSrc="/assets/iconsModalContas/money.svg"
          placeholder="R$ 0,00"
          value={saldo}
          onChange={handleChangeSaldo}
        />

        <SelectedBancos setBanco={setBancoId} />

        <SelectedTipoConta setTipoConta={setTipoContaId} />

        <DatePicker
          value={data}
          onChange={setData}
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
          Atualizar Conta
        </button>
      </div>
    </div>
  );
}

export default ModalEditContas;
