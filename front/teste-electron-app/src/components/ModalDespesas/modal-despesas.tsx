import React, { useState } from "react";
import axios from "axios";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-despesas.module.css";
import SelectBoxContas from "../UI/SelectedBoxContas/selected-box-contas";
import DatePicker from "../UI/DatePicker/date-picker"; 
import SelectedDespesas from "../UI/SelectedDespesa/selected-despesa";

interface ModalADespesasProps {
  onClose: () => void;
}

function ModalDespesas({ onClose }: ModalADespesasProps) {
  const [valor, setValor] = useState<string>("");
  const [descricao, setDescricao] = useState<string>("");
  const [categoria, setCategoria] = useState<number | null>(null); // Agora armazenando o ID da categoria
  const [conta, setConta] = useState<number | null>(null); // Alteração: Agora é um número (ID da conta)
  const [data, setData] = useState<string>(""); 
  const [comentario, setComentario] = useState<string | null>(null);
  const [tipoTransacao, setTipoTransacao] = useState<string>("DESPESA"); // Default como 'DESPESA'

  const formatarMoeda = (valor: string): string => {
    let valorNumerico = valor.replace(/\D/g, ''); 
    valorNumerico = (Number(valorNumerico) / 100).toFixed(2);
    valorNumerico = valorNumerico.replace('.', ',');
    valorNumerico = valorNumerico.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
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
    // Validação dos campos obrigatórios
    if (!valor || !descricao || !categoria || !data || !tipoTransacao || !conta) {
      alert("Preencha todos os campos obrigatórios!");
      return;
    }

    const valorNumerico = removerFormatacaoMoeda(valor);

    // Obtenção do token de autenticação
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
            Authorization: `Bearer ${token}`, // Enviando o token de autenticação no cabeçalho
            "Content-Type": "application/json",
          },
        }
      );

      console.log("Transação adicionada:", response.data);
      onClose(); 
    } catch (error) {
      console.error("Erro ao adicionar transação:", error);
      alert("Erro ao adicionar transação. Verifique os dados ou tente novamente.");
    }
  };

  const handleChangeDescricao = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDescricao(e.target.value);
  };

  const handleChangeComentario = (e: React.ChangeEvent<HTMLInputElement>) => {
    setComentario(e.target.value || null);
  };

  const handleChangeTipoTransacao = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTipoTransacao(e.target.value); // Atualiza o tipo de transação
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
          label="Valor: "
          type="text" 
          iconSrc="/assets/iconsModalDelete/money.svg"
          placeholder="R$ 0,00"
          value={valor}
          onChange={handleChangeValor} 
        />
        <InputWithIcon
          label="Descrição: "
          iconSrc="/assets/iconsModalDelete/descrip.svg"
          placeholder="Ex: Pagamento da fatura"
          value={descricao}
          onChange={handleChangeDescricao}
        />
        <SelectedDespesas setCategoria={setCategoria} /> {/* Passando a função de setCategoria para SelectedDespesas */}

        <SelectBoxContas setConta={setConta} /> {/* Passando a função de setConta */}

        <DatePicker value={data} onChange={setData} />

        <InputWithIcon
          label="Comentário: "
          iconSrc="/assets/iconsModalDelete/comentario.svg"
          placeholder="Opcional"
          value={comentario || ""}
          onChange={handleChangeComentario}
        />
        <div className={style.radioGroup}>
          <label>
            <input
              type="radio"
              name="tipoTransacao"
              value="DESPESA"
              checked={tipoTransacao === "DESPESA"} // Marca "DESPESA" se for o valor selecionado
              onChange={handleChangeTipoTransacao} // Atualiza o estado com o valor "DESPESA"
            />
            Despesa
          </label>
          <label>
            <input
              type="radio"
              name="tipoTransacao"
              value="RECEITA"
              checked={tipoTransacao === "RECEITA"} // Marca "RECEITA" se for o valor selecionado
              onChange={handleChangeTipoTransacao} // Atualiza o estado com o valor "RECEITA"
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
