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

interface CardTransacoesProps {
  transacoes: Transacao[];
  dataTransacao: string; // Data agrupada
  onEditClick: (id: number) => void; // Função para notificar o componente pai
}

const CardTransacoes: React.FC<CardTransacoesProps> = ({ transacoes, dataTransacao, onEditClick }) => {
  const formatarValor = (valor: number): string => {
    return valor.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
  };

  const [ano, mes, dia] = dataTransacao.split("-");

  return (
    <div className={styles.card}>
      <header>
        <time>{`${dia} de ${new Intl.DateTimeFormat("pt-BR", { month: "long" }).format(
          new Date(`${ano}-${mes}-02`)
        )} de ${ano}`}</time>
      </header>
      <ul className={styles.detalhes}>
        {transacoes.map((transacao) => {
          const valor = formatarValor(transacao.valor);

          return (
            <li
              key={transacao.id}
              onClick={() => onEditClick(transacao.id)} // Notifica o componente pai
              style={{ cursor: "pointer" }}
            >
              <strong>Descrição:</strong> {transacao.descricao} <br />
              {valor} <br />
              <strong>Categoria:</strong> {transacao.categoria?.nome} <br />
              <strong>Conta:</strong> {transacao.conta?.descricao} ({transacao.conta?.banco?.nome}) <br />
              <strong>Tipo:</strong> {transacao.origem === "FIXA" ? "Fixa" : "Normal"}
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default CardTransacoes;