import React, { useState } from "react";
import axios from "axios";
import style from "./input-bancos.module.css";

interface InputBancosProps {
  id: number;
  nome: string;
  iconeUrl: string;
  onClick: (id: number) => void;
}

interface BancoDetails {
  additionalInfo?: string;
}

const InputBancos: React.FC<InputBancosProps> = ({ id, nome, iconeUrl, onClick }) => {
  const [details, setDetails] = useState<BancoDetails | null>(null);
  const [showDetails, setShowDetails] = useState(false);
  const [loading, setLoading] = useState(false);

  const fetchBankDetails = async () => {
    setLoading(true);
    const token = localStorage.getItem("authToken");

    try {
      const response = await axios.get(
        `http://localhost:8080/bancos/${id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setDetails(response.data);
    } catch (err) {
      console.error(`Erro ao buscar detalhes para o banco ${id}:`, err);
    } finally {
      setLoading(false);
    }
  };

  const handleShowDetails = () => {
    if (!details) {
      fetchBankDetails();
    }
    setShowDetails(!showDetails);
  };

  const handleSelectBanco = () => {
    onClick(id);
  };

  return (
    <li className={style.item} onClick={handleSelectBanco}>
      <div className={style.header} onClick={handleShowDetails}>
        <img src={iconeUrl} alt={nome} />
        <a>{nome}</a>
      </div>

      {loading && <p>Carregando detalhes...</p>}

      {showDetails && details?.additionalInfo && !loading && (
        <p className={style.details}>{details.additionalInfo}</p>
      )}
    </li>
  );
};

export default InputBancos;
