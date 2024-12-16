import React, { useState } from "react";
import axios from "axios";
import style from "./input-bancos.module.css";

interface InputBancosProps {
  id: number;
  nome: string;
  iconeUrl: string;
  onClick: (id: number) => void;  // Atualizando para passar o id do banco
}

interface BancoDetails {
  additionalInfo?: string;
}

const InputBancos: React.FC<InputBancosProps> = ({ id, nome, iconeUrl, onClick }) => {
  const [details, setDetails] = useState<BancoDetails | null>(null);  // Tipagem do estado de detalhes
  const [showDetails, setShowDetails] = useState(false);
  const [loading, setLoading] = useState(false);  // Estado para controlar o carregamento

  // Função para buscar detalhes do banco
  const fetchBankDetails = async () => {
    setLoading(true);  // Começa o carregamento
    try {
      const response = await axios.get(
        `http://localhost:8080/bancos/${id}` // URL da API para obter detalhes
      );
      setDetails(response.data);
    } catch (err) {
      console.error(`Erro ao buscar detalhes para o banco ${id}:`, err);
    } finally {
      setLoading(false);  // Finaliza o carregamento
    }
  };

  // Carregar detalhes quando o usuário clicar
  const handleShowDetails = () => {
    if (!details) {
      fetchBankDetails();  // Carrega os detalhes caso ainda não tenha
    }
    setShowDetails(!showDetails);
  };

  // Chama a função onClick do componente pai passando o id do banco
  const handleSelectBanco = () => {
    onClick(id);  // Passa o id para o componente pai
  };

  return (
    <li className={style.item} onClick={handleSelectBanco}> {/* Evento de clique no item */}
      <div className={style.header} onClick={handleShowDetails}>
        <img src={iconeUrl} alt={nome} />
        <a>{nome}</a>
      </div>

      {/* Exibe o status de carregamento se estiver buscando detalhes */}
      {loading && <p>Carregando detalhes...</p>}

      {/* Renderiza detalhes adicionais, se disponíveis */}
      {showDetails && details?.additionalInfo && !loading && (
        <p className={style.details}>{details.additionalInfo}</p>
      )}
    </li>
  );
};

export default InputBancos;
