import React from "react";
import style from "./arquivadas-button.module.css";

interface ArquivadasButtonProps {
  texto: string;
  onClick: () => void;
}

const ArquivadasButton: React.FC<ArquivadasButtonProps> = ({ texto, onClick }) => {
  return (
    <button className={style.arquivadasButton} onClick={onClick}>
      <img
        src="/assets/arquivadas.svg" /* Certifique-se de que o caminho está correto */
        alt="Ícone de arquivados"
        className={style.iconArquivadas}
      />
      {texto}
    </button>
  );
};

export default ArquivadasButton;
