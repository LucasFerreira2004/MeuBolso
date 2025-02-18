import React, { useMemo } from "react";
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
    cor: string;
  };
  data_transacao_formatada?: string;
}

interface CardTransacoesProps {
  transacoes: Transacao[];
  dataTransacao: string;
  onEditClick: (id: number, tipo: string) => void;
}

const CardTransacoes: React.FC<CardTransacoesProps> = ({
  transacoes,
  dataTransacao,
  onEditClick,
}) => {
  // Formatar a data automaticamente quando `dataTransacao` mudar
  const dataFormatada = useMemo(() => {
    if (!dataTransacao) return "";
    const [ano, mes, dia] = dataTransacao.split("-");
    const data = new Date(`${ano}-${mes}-${dia}T00:00:00`);
    return new Intl.DateTimeFormat("pt-BR", {
      day: "2-digit",
      month: "long",
      year: "numeric",
    }).format(data);
  }, [dataTransacao]); // `useMemo` garante que a formatação só acontece quando `dataTransacao` muda

  const formatarValor = (valor: number): string => {
    return valor.toLocaleString("pt-BR", {
      style: "currency",
      currency: "BRL",
    });
  };

  return (
    <div className={styles.card}>
      <header className={styles.time}>
        <div className={styles.bolinhaDate}></div>
        <time>{dataFormatada}</time>
      </header>
      <ul className={styles.detalhes}>
        {transacoes.length > 0 ? (
          transacoes.map((transacao) => {
            const valor = formatarValor(transacao.valor);
            const corTransacao =
              transacao.tipo === "DESPESA"
                ? "#C63A22"
                : transacao.tipo === "RECEITA"
                ? "#2A9D8F"
                : `#${transacao.categoria.cor}`;

            return (
              <li
                key={transacao.id}
                onClick={() => onEditClick(transacao.id, transacao.tipo)}
                className={styles.linha}
              >
                <div
                  className={styles.individualLine}
                  style={{ color: corTransacao }}
                >
                  <div
                    className={styles.bolinha}
                    style={{ backgroundColor: corTransacao }}
                  ></div>
                  {transacao.descricao} <br />
                </div>
                <div
                  className={styles.individualLine}
                  style={{ color: corTransacao }}
                >
                  {valor} <br />
                </div>
                <div className={styles.individualLine}>
                  <label className={styles.label}>Categoria:</label>{" "}
                  {transacao.categoria?.nome} <br />
                </div>
                <div className={styles.individualLine}>
                  <label className={styles.label}>Conta:</label>{" "}
                  {transacao.conta?.banco?.nome} <br />
                </div>
                <div className={styles.individualLine}>
                  <label className={styles.label}>Fixa:</label>{" "}
                  {transacao.origem === "FIXA" ? "Sim" : "Não"}
                </div>
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
