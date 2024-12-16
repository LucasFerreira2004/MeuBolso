import { useState, useEffect } from "react";
import axios from "axios"; 
import style from "./drop-down-bancos.module.css";
import InputBancos from "../InputBancos/input-bancos";

interface Banco {
  id: number;
  nome: string;
  iconeUrl: string; 
}

const DropDownBancos = ({ toggleDropdownBancos }: { toggleDropdownBancos: () => void }) => {
  const [isOpen, setIsOpen] = useState(true);
  const [bancos, setBancos] = useState<Banco[]>([]);

  // Função para alternar a visibilidade do dropdown
  const toggleDropdown = () => {
    setIsOpen(false); // Fecha o dropdown internamente
    toggleDropdownBancos(); // Chama a função do componente pai para fechar também
  };

  // Função para buscar dados da API
  const fetchBancos = async () => {
    try {
      const response = await axios.get("http://localhost:8080/bancos"); 
      setBancos(response.data); 
    } catch (err) {
      console.error("Erro ao buscar os dados dos bancos:", err);
    }
  };

  useEffect(() => {
    fetchBancos(); 
  }, []);

  if (!isOpen) return null;

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
          />
        ))}
      </ul>
    </div>
  );
};

export default DropDownBancos;
