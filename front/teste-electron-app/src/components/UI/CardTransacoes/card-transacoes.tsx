import React, { useState } from "react";
import styles from "./card-transacoes.module.css";
import ModalEditTrans from "../../ModalEditTrans/moda-edit-trans"; // Importe o modal de edição

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
}

const CardTransacoes: React.FC<CardTransacoesProps> = ({ transacoes, dataTransacao }) => {
  const [modalOpen, setModalOpen] = useState(false);
  const [transactionId, setTransactionId] = useState<number | null>(null);

  const formatarValor = (valor: number): string => {
    return valor.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
  };

  const [ano, mes, dia] = dataTransacao.split("-");

  const handleEditClick = (id: number) => {
    setTransactionId(id); // Armazenar o ID da transação que será editada
    setModalOpen(true); // Abrir o modal
  };

  return (
    <div className={styles.card}>
      <header>
        <time>{`${dia} de ${new Intl.DateTimeFormat("pt-BR", { month: "long" }).format(new Date(`${ano}-${mes}-02`))} de ${ano}`}</time>
      </header>
      <ul className={styles.detalhes}>
        {transacoes.map((transacao) => {
          const valor = formatarValor(transacao.valor);

          return (
            <li key={transacao.id} onClick={() => handleEditClick(transacao.id)} style={{ cursor: "pointer" }}>
              <strong>Descrição:</strong> {transacao.descricao} <br />
              {valor} <br />
              <strong>Categoria:</strong> {transacao.categoria?.nome} <br />
              <strong>Conta:</strong> {transacao.conta?.descricao} ({transacao.conta?.banco?.nome}) <br />
              <strong>Tipo:</strong> {transacao.origem === "FIXA" ? "Fixa" : "Normal"}
            </li>
          );
        })}
      </ul>

      {/* Modal de edição */}
      {modalOpen && transactionId && (
        <ModalEditTrans
          onCloseAll={() => setModalOpen(false)} // Fechar o modal
          mes={parseInt(mes)}
          ano={parseInt(ano)}
          transactionId={transactionId} // Passar o ID da transação para editar
        />
      )}
    </div>
  );
};

export default CardTransacoes;
