import React from 'react';
import styles from './addButton.module.css';

interface AddButtonProps {
  texto: string;
}

const AddButton: React.FC<AddButtonProps> = ({ texto }) => {
  return (
    <button className={styles.addButton}>
      <img
        src="/assets/plus.svg"
        alt="Ícone de adicionar"
        className={styles.iconPlus}
      />
      {texto}
    </button>
  );
};

export default AddButton;
