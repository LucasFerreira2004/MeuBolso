export interface Transacao {
    id: number;
    data_transacao: string;
    tipo: string;
    valor: number;
    descricao: string;
    origem?: string; // Adicione propriedades opcionais, se necessário
    idTransacaoRecorrente?: number;
    conta?: string;
    categoria?: string;
    deleted?: boolean;
  }