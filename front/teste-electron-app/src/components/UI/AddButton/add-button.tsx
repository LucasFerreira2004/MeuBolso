import React from 'react';
import style from './addButton.module.css';

interface AddButtonProps {
  texto: string;
}

const AddButton: React.FC<AddButtonProps> = ({ texto }) => {
  return (
    <button className={style.addButton}>
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
