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
  dataTransacao: string; 
  onEditClick: (id: number) => void; 
}

const CardTransacoes: React.FC<CardTransacoesProps> = ({
  transacoes,
  dataTransacao,
  onEditClick,
}) => {
  const [ano, mes, dia] = dataTransacao.split("-");

  const formatarValor = (valor: number): string => {
    return valor.toLocaleString("pt-BR", {
      style: "currency",
      currency: "BRL",
    });
  };

  const formatarData = (): string => {
    const data = new Date(`${ano}-${mes}-${dia}T00:00:00`);
    return new Intl.DateTimeFormat("pt-BR", {
      day: "2-digit",
      month: "long",
      year: "numeric",
    }).format(data);
  };
  

  return (
    <div className={styles.card}>
      <header>
        <time>{formatarData()}</time>
      </header>
      <ul className={styles.detalhes}>
        {transacoes.length > 0 ? (
          transacoes.map((transacao) => {
            const valor = formatarValor(transacao.valor);

            return (
              <li
                key={transacao.id}
                onClick={() => onEditClick(transacao.id)}
                style={{ cursor: "pointer" }}
              >
                <strong>Descrição:</strong> {transacao.descricao} <br />
                <strong>Valor:</strong> {valor} <br />
                <strong>Categoria:</strong> {transacao.categoria?.nome} <br />
                <strong>Conta:</strong> {transacao.conta?.descricao} (
                {transacao.conta?.banco?.nome}) <br />
                <strong>Tipo:</strong>{" "}
                {transacao.origem === "FIXA" ? "Fixa" : "Normal"}
              </li>
            );
          })
        ) : (
          <p>Nenhuma transação encontrada.</p>
        )}
      </ul>
    </div>
  );
};

export default CardTransacoes;
