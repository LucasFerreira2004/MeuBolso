import { useState, useEffect } from "react";
import axios from "axios"; 
import style from "./drop-down-bancos.module.css";
import InputBancos from "../InputBancos/input-bancos";

// Interface para tipar o banco
interface Banco {
  id: number;
  nome: string;
  iconeUrl: string;  // Alteração de 'iconUrl' para 'iconeUrl'
}

const DropDownBancos = () => {
  const [isOpen, setIsOpen] = useState(true); // Estado que controla a visibilidade do dropdown
  const [bancos, setBancos] = useState<Banco[]>([]); // Estado para armazenar os bancos, tipado com a interface Banco

  // Função para alternar a visibilidade do dropdown
  const toggleDropdown = () => {
    setIsOpen(false); // Define como false para fechar o dropdown
  };

  // Função para buscar dados da API
  const fetchBancos = async () => {
    try {
      const response = await axios.get("http://localhost:8080/bancos"); // URL da API
      setBancos(response.data); // Atualiza o estado com os dados da API
    } catch (err) {
      console.error("Erro ao buscar os dados dos bancos:", err);
    }
  };

  // Carrega os dados dos bancos ao montar o componente
  useEffect(() => {
    fetchBancos();
  }, []); // O array vazio [] faz a chamada apenas uma vez após o componente ser montado

  // Se o dropdown estiver fechado, retorna null (não renderiza nada)
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
        {/* Mapeia os bancos e renderiza cada InputBancos */}
        {bancos.map((banco) => (
          <InputBancos
            key={banco.id} // Cada item precisa de uma key única
            id={banco.id}
            nome={banco.nome}
            iconeUrl={banco.iconeUrl}  // Alteração de 'iconUrl' para 'iconeUrl'
          />
        ))}
      </ul>
    </div>
  );
};

export default DropDownBancos;
