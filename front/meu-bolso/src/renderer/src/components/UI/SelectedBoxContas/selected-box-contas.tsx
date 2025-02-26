import { useEffect, useState } from "react";
import Select from "react-select";
import style from "./selected-box-contas.module.css";
import { baseUrl } from "../../../api/api";

interface Conta {
  id: number;
  saldo: number;
  iconeUrl: string;
  nomeBanco: string;
}

interface ApiConta {
  id: number;
  saldo: number;
  banco: {
    iconeUrl: string;
    nome: string;
  };
}

interface SelectBoxContasProps {
  setConta: React.Dispatch<React.SetStateAction<number | null>>;
  mes: number;
  ano: number;
}

interface OptionType {
  value: number;
  label: JSX.Element;
}

function SelectBoxContas({ setConta, mes, ano }: SelectBoxContasProps) {
  const [contas, setContas] = useState<Conta[]>([]);

  useEffect(() => {
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      return;
    }

    const url = `${baseUrl}/contas?ano=${ano}&mes=${mes}`;

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
      .then((data: ApiConta[]) => {
        const contasMapeadas = data.map((conta) => ({
          id: conta.id,
          saldo: conta.saldo,
          iconeUrl: conta.banco.iconeUrl,
          nomeBanco: conta.banco.nome,
        }));
        setContas(contasMapeadas);
        setConta(contasMapeadas.length > 0 ? contasMapeadas[0].id : null);
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

  const handleChange = (selectedOption: OptionType | null) => {
    if (selectedOption) {
      setConta(selectedOption.value);
    }
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