import React from 'react';
import styles from './card-transacoes.module.css';

interface CardTransacoesProps {
    dia: string;
    mes: number;
    ano: number;
    valor: number;
    descricao: string;
    categoria: string;
    conta: string;
    fixa: string;
}

const CardTransacoes: React.FC<CardTransacoesProps> = ({ dia, mes, ano, valor, descricao, categoria, conta, fixa }) => {
    const mesExtenso = new Date(ano, mes - 1).toLocaleString('pt-BR', { month: 'long' });
    const data = `${dia}, ${mesExtenso} de ${ano}`;
    const valorFormatado = valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

    return (
        <div className={styles.card}>
            <header>
                <time>{data}</time>
            </header>
            <ul className={styles.detalhes}>
                <li><strong>Descrição:</strong> {descricao}</li>
                <li>{valorFormatado}</li>
                <li><strong>Categoria:</strong> {categoria}</li>
                <li><strong>Conta:</strong> {conta}</li>
                <li><strong>Fixa:</strong> {fixa}</li>
            </ul>
            <ul className={styles.detalhes}>
                <li><strong>Descrição:</strong> {descricao}</li>
                <li>{valorFormatado}</li>
                <li><strong>Categoria:</strong> {categoria}</li>
                <li><strong>Conta:</strong> {conta}</li>
                <li><strong>Fixa:</strong> {fixa}</li>
            </ul>
            <ul className={styles.detalhes}>
                <li><strong>Descrição:</strong> {descricao}</li>
                <li>{valorFormatado}</li>
                <li><strong>Categoria:</strong> {categoria}</li>
                <li><strong>Conta:</strong> {conta}</li>
                <li><strong>Fixa:</strong> {fixa}</li>
            </ul>
        </div>
    );
}

export default CardTransacoes;
