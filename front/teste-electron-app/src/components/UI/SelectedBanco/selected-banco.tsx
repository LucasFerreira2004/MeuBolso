import { useEffect, useState } from "react";
import Select from "react-select";
import style from "./selected-banco.module.css";

interface Banco {
  id: number;
  nome: string;
  iconeUrl: string;
}

interface SelectedBancosProps {
  setBanco: (bancoId: number | null) => void; // Prop esperada
}

function SelectedBancos({ setBanco }: SelectedBancosProps) {
  const [bancos, setBancos] = useState<Banco[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const token = localStorage.getItem("authToken");

    if (!token) {
      setError("Token de autenticação não encontrado.");
      return;
    }

    const url = "http://localhost:8080/bancos";

    fetch(url, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Erro ao buscar bancos.");
        }
        return response.json();
      })
      .then((data: Banco[]) => {
        setBancos(data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Erro ao buscar bancos:", error);
        setError("Erro ao carregar bancos. Tente novamente mais tarde.");
        setLoading(false);
      });
  }, []);

  const options = bancos.map((banco) => ({
    value: banco.id,
    label: (
      <div style={{ display: "flex", alignItems: "center" }}>
        <img
          src={banco.iconeUrl}
          alt={banco.nome}
          style={{ width: "20px", height: "20px", marginRight: "10px" }}
        />
        {banco.nome}
      </div>
    ),
  }));

  const handleChange = (selectedOption: any) => {
    const bancoSelecionado = selectedOption ? selectedOption.value : null;
    setBanco(bancoSelecionado); // Passando o ID do banco
  };

  return (
    <div className={style.selectBoxContainer}>
      <div className={style.selectBoxWrapper}>
        <label className={style.selectBoxLabel}>Banco:</label>
        {loading ? (
          <p>Carregando bancos...</p>
        ) : error ? (
          <p className={style.error}>{error}</p>
        ) : (
          <Select
            options={options}
            onChange={handleChange}
            placeholder="Selecione um banco"
            className={style.selectBox}
            classNamePrefix="react-select"
          />
        )}
      </div>
    </div>
  );
}

export default SelectedBancos;