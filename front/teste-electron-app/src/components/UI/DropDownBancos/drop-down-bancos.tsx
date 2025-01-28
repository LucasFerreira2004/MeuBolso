import { useState, useEffect } from "react";
import axios from "axios";
import style from "./drop-down-bancos.module.css";
import InputBancos from "../InputBancos/input-bancos";

interface Banco {
  nome: string;
  iconeUrl: string;
}

interface DropDownBancosProps {
  toggleDropdownBancos: () => void;
  setBanco: (nome: string, iconeUrl: string) => void; // Atualizado para receber nome e iconeUrl
}

const DropDownBancos = ({
  toggleDropdownBancos,
  setBanco,
}: DropDownBancosProps) => {
  const [isOpen, setIsOpen] = useState(true);
  const [bancos, setBancos] = useState<Banco[]>([]);

  const toggleDropdown = () => {
    setIsOpen(false);
    toggleDropdownBancos();
  };

  const fetchBancos = async () => {
    try {
      const token = localStorage.getItem("authToken");
      const response = await axios.get("http://localhost:8080/bancos", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      // Mapeia a resposta para incluir apenas nome e iconeUrl
      const bancosFormatados = response.data.map((banco: any) => ({
        nome: banco.nome,
        iconeUrl: banco.iconeUrl,
      }));
      setBancos(bancosFormatados);
    } catch (err) {
      console.error("Erro ao buscar os dados dos bancos:", err);
    }
  };

  useEffect(() => {
    fetchBancos();
  }, []);

  const handleSelectBanco = (nome: string, iconeUrl: string) => {
    setBanco(nome, iconeUrl); // Passa nome e iconeUrl
    toggleDropdown();
  };

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
        {bancos.map((banco, index) => (
          <InputBancos
            key={index} // Usando o índice como a key
            id={index} // Passando o índice como id
            nome={banco.nome}
            iconeUrl={banco.iconeUrl}
            onClick={() => handleSelectBanco(banco.nome, banco.iconeUrl)}
          />
        ))}
      </ul>
    </div>
  );
};

export default DropDownBancos;
