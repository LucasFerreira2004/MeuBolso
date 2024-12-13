import React from 'react';
import style from './addButton.module.css';

interface AddButtonProps {
  texto: string;
  onClick: () => void; 
}

const AddButton: React.FC<AddButtonProps> = ({ texto, onClick }) => {
  return (
    <button className={style.addButton} onClick={onClick}>
      <img
        src="/assets/plus.svg"
        alt="Ícone de adicionar"
        className={style.iconPlus}
      />
      {texto}
    </button>
  );
};

export default AddButton;
