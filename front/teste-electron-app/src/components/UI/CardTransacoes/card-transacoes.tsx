import React from "react";
import styles from "./card-transacoes.module.css";

interface Transacao {
    id: number;
    valor: number;
    data_transacao: string;
    tipo: string;
    descricao: string;
    origem: string;
    conta: {
      descricao: string;
      banco: {
        nome: string;
      };
    };
    categoria: {
      nome: string;
    };
    data_transacao_formatada?: string;
  }

const CardTransacoes: React.FC<{ transacoes: Transacao[] }> = ({ transacoes }) => {
    const formatarValor = (valor: number): string => {
      return valor.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
    };
  
    return (
      <div>
        {transacoes.length === 0 ? (
          <p>Não há transações para o período selecionado.</p>
        ) : (
          <div className={styles.cardList}>
            {transacoes.map((transacao) => {
              const dataTransacao = transacao.data_transacao; // Aqui, a data vem diretamente como string
              const [ano, mes, dia] = dataTransacao.split("-"); // Divida para formatar como "28 de março de 2025"
              const valor = formatarValor(transacao.valor);
  
              return (
                <div key={transacao.id} className={styles.card}>
                  <header>
                    <time>{`${dia} de ${new Intl.DateTimeFormat("pt-BR", { month: "long" }).format(new Date(`${ano}-${mes}-02`))} de ${ano}`}</time>
                  </header>
                  <ul className={styles.detalhes}>
                    <li>
                      <strong>Descrição:</strong> {transacao.descricao}
                    </li>
                    <li>{valor}</li>
                    <li>
                      <strong>Categoria:</strong> {transacao.categoria?.nome}
                    </li>
                    <li>
                      <strong>Conta:</strong> {transacao.conta?.descricao} (
                      {transacao.conta?.banco?.nome})
                    </li>
                    <li>
                      <strong>Tipo:</strong>{" "}
                      {transacao.origem === "FIXA" ? "Fixa" : "Normal"}
                    </li>
                  </ul>
                </div>
              );
            })}
          </div>
        )}
      </div>
    );
  };
  
  

export default CardTransacoes;
