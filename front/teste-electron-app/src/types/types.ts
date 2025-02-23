export interface Transacao {
  id: number;
  valor: number;
  data_transacao: string;
  tipo: string;
  descricao: string;
  origem: string;
  idTransacaoRecorrente: number;
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
  deleted?: boolean;
}