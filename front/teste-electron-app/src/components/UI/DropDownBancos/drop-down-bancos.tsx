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
  setBanco: (nome: string) => void;  // Agora o setBanco recebe o nome do banco
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
      const token = localStorage.getItem("authToken");
  
      const response = await axios.get("http://localhost:8080/bancos", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
  
      setBancos(response.data); 
    } catch (err) {
      console.error("Erro ao buscar os dados dos bancos:", err);
    }
  };

  useEffect(() => {
    fetchBancos(); 
  }, []);

  if (!isOpen) return null;

  // Função para lidar com a seleção do banco
  const handleSelectBanco = (nome: string) => {
    setBanco(nome);  // Passa o nome do banco
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
            onClick={() => handleSelectBanco(banco.nome)}  // Passando nome do banco
          />
        ))}
      </ul>
    </div>
  );
};

export default DropDownBancos;
