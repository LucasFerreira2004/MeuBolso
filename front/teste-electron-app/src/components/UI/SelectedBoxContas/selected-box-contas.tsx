import { useEffect, useState } from "react";
import Select from "react-select";
import style from "./selected-box-contas.module.css";

interface Banco {
  nome: string;
  iconeUrl: string;
}

interface Conta {
  id: number;
  saldo: number;
  banco: Banco;
  tipo_conta: {
    tipoConta: string;
  };
}

interface SelectBoxContasProps {
  setConta: React.Dispatch<React.SetStateAction<number | null>>; // Adicionando a prop setConta
}

function SelectBoxContas({ setConta }: SelectBoxContasProps) { // Recebendo a prop setConta
  const [contas, setContas] = useState<Conta[]>([]);

  const getDataAtual = (): string => {
    const data = new Date();
    const ano = data.getFullYear();
    const mes = String(data.getMonth() + 1).padStart(2, "0");
    const dia = String(data.getDate()).padStart(2, "0");
    return `${ano}-${mes}-${dia}`;
  };

  useEffect(() => {
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      return;
    }

    const dataReferencia = getDataAtual();
    const url = `http://localhost:8080/contas?data=${dataReferencia}`;

    fetch(url, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Erro ao buscar contas.");
        }
        return response.json();
      })
      .then((data: Conta[]) => {
        setContas(data);
        setConta(data.length > 0 ? data[0].id : null); // Inicializa o setConta com o ID da primeira conta
      })
      .catch((error) => console.error("Erro ao buscar contas:", error));
  }, [setConta]);

  const options = contas.map((conta) => ({
    value: conta.id,
    label: (
      <div style={{ display: "flex", alignItems: "center" }}>
        <img
          src={conta.banco.iconeUrl}
          alt={conta.banco.nome}
          width={20}
          height={20}
          style={{ marginRight: "10px" }}
        />
        <span>
          {conta.banco.nome} - Saldo: R$ {conta.saldo.toFixed(2)}
        </span>
      </div>
    ),
  }));

  const handleChange = (selectedOption: any) => {
    setConta(selectedOption.value); // Atualiza a conta selecionada
  };

  return (
    <div className={style.selectBoxContainer}>
      <div className={style.selectBoxWrapper}>
        <label className={style.selectBoxLabel}>Conta:</label>
        <Select
          options={options}
          onChange={handleChange}
          placeholder="Selecione uma conta"
          className={style.selectBox}
          classNamePrefix="react-select"
        />
      </div>
    </div>
  );
}

export default SelectBoxContas;
