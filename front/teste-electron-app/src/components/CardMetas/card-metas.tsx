import React from 'react';
import styles from './card-metas.module.css';
import ProgressBar from '../ProgressBar/progress-bar';

interface CardMetasProps {
  texto: string;
  imagem: string; // Caminho ou URL para a imagem
}

const CardMetas: React.FC<CardMetasProps> = ({ texto, imagem }) => {
  return (
    <div className={styles.cardContainer}>
      <div className={styles.cardContent}>
        <img src={imagem} alt={texto} className={styles.cardImage} />
        <p className={styles.cardText}>{texto}</p>
        <ProgressBar />
      </div>
    </div>
  );
};

export default CardMetas;
