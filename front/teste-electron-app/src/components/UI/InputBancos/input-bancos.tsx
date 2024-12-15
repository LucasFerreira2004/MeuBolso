import React, { useState } from "react";
import axios from "axios";
import style from "./input-bancos.module.css";

interface InputBancosProps {
  id: number;
  nome: string;
  iconeUrl: string;  // Alteração de 'iconUrl' para 'iconeUrl'
}

const InputBancos: React.FC<InputBancosProps> = ({ id, nome, iconeUrl }) => {
  const [details, setDetails] = useState<{ additionalInfo?: string } | null>(null);
  const [showDetails, setShowDetails] = useState(false);

  // Função para buscar detalhes do banco
  const fetchBankDetails = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/bancos/${id}` // URL da API para obter detalhes
      );
      setDetails(response.data);
    } catch (err) {
      console.error(`Erro ao buscar detalhes para o banco ${id}:`, err);
    }
  };

  // Carregar detalhes quando o usuário clicar
  const handleShowDetails = () => {
    if (!details) {
      fetchBankDetails();
    }
    setShowDetails(!showDetails);
  };

  return (
    <li className={style.item}>
      <div className={style.header} onClick={handleShowDetails}>
        <img src={iconeUrl} alt={nome} />  {/* Alteração de 'iconUrl' para 'iconeUrl' */}
        <a>{nome}</a>
      </div>
      {/* Renderiza detalhes adicionais, se disponíveis */}
      {showDetails && details && details.additionalInfo && (
        <p className={style.details}>{details.additionalInfo}</p>
      )}
    </li>
  );
};

export default InputBancos;
