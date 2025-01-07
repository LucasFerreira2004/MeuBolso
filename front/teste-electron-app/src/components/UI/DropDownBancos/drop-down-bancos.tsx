import { useState, useEffect } from "react";
import axios from "axios"; 
import style from "./drop-down-bancos.module.css";
import InputBancos from "../InputBancos/input-bancos";

interface Banco {
  id: number;
  nome: string;
  iconeUrl: string; 
}

interface DropDownBancosProps {
  toggleDropdownBancos: () => void;
  setBanco: (id: number) => void;  // Função para atualizar o estado no componente pai
}

const DropDownBancos = ({ toggleDropdownBancos, setBanco }: DropDownBancosProps) => {
  const [isOpen, setIsOpen] = useState(true);
  const [bancos, setBancos] = useState<Banco[]>([]);

  // Função para alternar a visibilidade do dropdown
  const toggleDropdown = () => {
    setIsOpen(false); // Fecha o dropdown internamente
    toggleDropdownBancos(); // Chama a função do componente pai para fechar também
  };

  const fetchBancos = async () => {
    try {
      // Supondo que o token esteja salvo no localStorage
      const token = localStorage.getItem("authToken");
  
      // Se o token existir, inclui no cabeçalho da requisição
      const response = await axios.get("http://localhost:8080/bancos", {
        headers: {
          Authorization: `Bearer ${token}`,  // Envia o token no cabeçalho
        },
      });
  
      setBancos(response.data); // Atualiza o estado com os dados recebidos
    } catch (err) {
      console.error("Erro ao buscar os dados dos bancos:", err);
    }
  };

  useEffect(() => {
    fetchBancos(); 
  }, []);

  if (!isOpen) return null;

  // Função para lidar com a seleção do banco
  const handleSelectBanco = (id: number) => {
    setBanco(id);  // Passa o ID do banco para o componente pai
    toggleDropdown(); // Fecha o dropdown após a seleção
  };

  return (
    <div className={style.containerDropDownBancos}>
      <div className={style.header}>
        <h3>Opção</h3>
        <img
          src="/assets/iconsModal/iconX.svg"
          alt="Fechar"
          className={style.iconClose}
          onClick={toggleDropdown} 
        />
      </div>
      <ul className={style.listaBancos}>
        {bancos.map((banco) => (
          <InputBancos
            key={banco.id}
            id={banco.id}
            nome={banco.nome}
            iconeUrl={banco.iconeUrl}
            onClick={() => handleSelectBanco(banco.id)}  // Passa a função onClick corretamente
          />
        ))}
      </ul>
    </div>
  );
};

export default DropDownBancos;
