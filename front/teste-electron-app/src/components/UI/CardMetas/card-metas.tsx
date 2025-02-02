import React from 'react';
import style from './card-metas.module.css';
import ProgressBar from '../ProgressBar/progress-bar'

interface CardMetasProps {
  texto: string;
  imagem: string; // Caminho ou URL para a imagem
}

const CardMetas: React.FC<CardMetasProps> = ({ texto, imagem }) => {
  return (
    <div className={style.cardContainer}>
      <div className={style.cardContent}>
        <img src={imagem} alt={texto} className={style.cardImage} />
        <p className={style.cardText}>{texto}</p>
        <ProgressBar />
      </div>
    </div>
  );
};

export default CardMetas;
