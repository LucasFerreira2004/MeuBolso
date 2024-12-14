import { useState } from "react";
import style from "./drop-down-colors.module.css";

const DropDownColors = () => {
  const [isOpen, setIsOpen] = useState(true); // Estado que controla a visibilidade do dropdown

  // Função para alternar a visibilidade do dropdown
  const toggleDropdown = () => {
    setIsOpen(false); // Define como false para fechar o dropdown
  };

  // Se o dropdown estiver fechado, retorna null (não renderiza nada)
  if (!isOpen) return null;

  return (
    <div className={style.containerDropDownCategories}>
      <div className={style.header}>
        <h3>Cores</h3> 
        <img
          src="/assets/iconsModal/iconX.svg"
          alt="Fechar modal"
          className={style.closeIcon}
          onClick={toggleDropdown} // Chama a função para fechar o dropdown
        />
      </div>
      <div className={style.linhacor}>
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
      </div>
      <div className={style.linhacor}>
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
      </div>
      <div className={style.linhacor}>
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
      </div>
      <div className={style.linhacor}>
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
        <img src="/assets/iconsModal/iconcorteste.svg" alt="cor teste" />
      </div>
    </div>
  );
};

export default DropDownColors;
