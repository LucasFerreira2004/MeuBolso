import { useEffect, useState } from "react";
import Select from "react-select";
import style from "./selected-tipo-conta.module.css";
import { baseUrl } from "../../../api/api";

interface TipoConta {
  id: number;
  tipoConta: string;
}

interface SelectedTipoContaProps {
  setTipoConta: (tipoContaId: number | null) => void;
}

interface OptionType {
  value: number; 
  label: string; 
}

function SelectedTipoConta({ setTipoConta }: SelectedTipoContaProps) {
  const [tiposConta, setTiposConta] = useState<TipoConta[]>([]);

  useEffect(() => {
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      return;
    }

    const url = `${baseUrl}/tipoConta`; 

    fetch(url, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Erro ao buscar tipos de conta.");
        }
        return response.json();
      })
      .then((data: TipoConta[]) => {
        console.log("Dados recebidos:", data);
        if (Array.isArray(data)) {
          setTiposConta(data);
        } else {
          console.error("Resposta inválida: Esperado um array de tipos de conta.");
        }
      })
      .catch((error) => {
        console.error("Erro ao buscar tipos de conta:", error);
      });
  }, []);

  const options = tiposConta.map((tipo) => ({
    value: tipo.id,
    label: tipo.tipoConta.replace("_", " "), 
  }));

  const handleChange = (selectedOption: OptionType | null) => {
    setTipoConta(selectedOption ? selectedOption.value : null); 
  };

  return (
    <div className={style.selectBoxContainer}>
      <div className={style.selectBoxWrapper}>
        <label className={style.selectBoxLabel}>Tipo de Conta:</label>
        <Select
          options={options}
          onChange={handleChange}
          placeholder="Selecione o tipo de conta"
          className={style.selectBox}
          classNamePrefix="react-select"
        />
      </div>
    </div>
  );
}

export default SelectedTipoConta;