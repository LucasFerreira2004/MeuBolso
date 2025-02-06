import { useEffect, useState } from "react";
import Select from "react-select";
import style from "./selected-box-contas.module.css";

interface Conta {
  id: number;
  saldo: number;
  iconeUrl: string;  // Agora iconeUrl é diretamente uma propriedade de Conta
  nomeBanco: string; // Nome do banco
}

interface SelectBoxContasProps {
  setConta: React.Dispatch<React.SetStateAction<number | null>>;
  mes: number;
  ano: number;
}

function SelectBoxContas({ setConta, mes, ano }: SelectBoxContasProps) {
  const [contas, setContas] = useState<Conta[]>([]);

  useEffect(() => {
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      return;
    }

    const url = `http://localhost:8080/contas/min?ano=${ano}&mes=${mes}`;

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
        setConta(data.length > 0 ? data[0].id : null); // Define a conta inicial
      })
      .catch((error) => console.error("Erro ao buscar contas:", error));
  }, [mes, ano, setConta]);

  const options = contas.map((conta) => ({
    value: conta.id,
    label: (
      <div style={{ display: "flex", alignItems: "center" }}>
        {conta.iconeUrl ? (
          <img
            src={conta.iconeUrl}
            alt={conta.nomeBanco}
            width={20}
            height={20}
            style={{ marginRight: "10px" }}
          />
        ) : (
          <span style={{ marginRight: "10px" }}>🛑</span> // Placeholder se não houver ícone
        )}
        <span>
          {conta.nomeBanco} - Saldo: R$ {conta.saldo.toFixed(2)}
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
