import { useEffect, useState } from "react";
import Select from "react-select";
import style from "./selected-tipo-conta.module.css";

interface TipoConta {
  id: number;
  tipoConta: string;
}

interface SelectedTipoContaProps {
  setTipoConta: (tipoContaId: number | null) => void;
}

function SelectedTipoConta({ setTipoConta }: SelectedTipoContaProps) {
  const [tiposConta, setTiposConta] = useState<TipoConta[]>([]);

  useEffect(() => {
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      return;
    }

    const url = "http://localhost:8080/tipoConta/1"; // Ajuste o URL conforme necessário

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
        setTiposConta(data);
      })
      .catch((error) => console.error("Erro ao buscar tipos de conta:", error));
  }, []);

  const options = tiposConta.map((tipo) => ({
    value: tipo.id,
    label: tipo.tipoConta.replace("_", " "), // Ajuste o nome se vier com "_"
  }));

  const handleChange = (selectedOption: any) => {
    setTipoConta(selectedOption ? selectedOption.value : null);  // Passando o ID do tipo de conta
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
