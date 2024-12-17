import { useState } from "react";
import style from "./drop-down-colors.module.css";

interface DropDownColorsProps {
  setColor: (color: string) => void; // Função que vai ser passada pelo ModalAddCategoria
}

const DropDownColors = ({ setColor }: DropDownColorsProps) => {
  const [isOpen, setIsOpen] = useState(true); // Estado que controla a visibilidade do dropdown

  // Função para alternar a visibilidade do dropdown
  const toggleDropdown = () => {
    setIsOpen(false);
  };

  // Função para selecionar a cor e atualizar o estado no ModalAddCategoria
  const handleColorSelect = (color: string) => {
    setColor(color); // Atualiza o estado de cor no componente pai (ModalAddCategoria)
    toggleDropdown(); // Fecha o dropdown após a cor ser selecionada
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
          onClick={toggleDropdown}
        />
      </div>
      <div className={style.linhacor}>
        <img
          src="/assets/iconesCores/FF5733.svg"
          alt="Laranja vibrante"
          onClick={() => handleColorSelect("#FF5733")}
        />
        <img
          src="/assets/iconesCores/33FF57.svg"
          alt="Verde menta"
          onClick={() => handleColorSelect("#33FF57")}
        />
        <img
          src="/assets/iconesCores/3399FF.svg"
          alt="Azul claro"
          onClick={() => handleColorSelect("#3399FF")}
        />
        <img
          src="/assets/iconesCores/FF33A8.svg"
          alt="Rosa choque"
          onClick={() => handleColorSelect("#FF33A8")}
        />
        <img
          src="/assets/iconesCores/FFBF00.svg"
          alt="Amarelo ouro"
          onClick={() => handleColorSelect("#FFBF00")}
        />
        <img
          src="/assets/iconesCores/9B59B6.svg"
          alt="Roxo suave"
          onClick={() => handleColorSelect("#9B59B6")}
        />
        <img
          src="/assets/iconesCores/00C8FF.svg"
          alt="Azul celeste"
          onClick={() => handleColorSelect("#00C8FF")}
        />
        <img
          src="/assets/iconesCores/F39C12.svg"
          alt="Laranja dourado"
          onClick={() => handleColorSelect("#F39C12")}
        />
      </div>
    </div>
  );
};

export default DropDownColors;
