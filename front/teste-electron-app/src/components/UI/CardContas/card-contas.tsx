import style from "./card-contas.module.css";

interface CardContasProps {
  titulo: string;
  tipo: string;
  saldo: number | null | undefined; 
  banco: string;
  altBanco: string;
  onDelete: () => void;
  onEdit: () => void;
}

const formatarTipoConta = (tipo: string) => {
  return tipo
    .toLowerCase()  
    .replace(/_/g, ' ')  
    .replace(/\b\w/g, (char) => char.toUpperCase());  
};

const CardContas: React.FC<CardContasProps> = ({ titulo, tipo, saldo, banco, altBanco, onDelete, onEdit }) => {
  const formatarSaldo = (valor: number | null | undefined) => {
    if (valor == null) {
      return "R$ 0,00"; 
    }
    return valor.toLocaleString("pt-BR", {
      style: "currency",
      currency: "BRL",
    });
  };

  return (
    <div className={style.cardContas}>
      <div className={style.parteInfo}>
        <div className={style.imagemContainer}>
          <img src={banco} alt={altBanco} />
        </div>
        <div className={style.infoText}>
          <h3>{titulo}</h3>
          <p>Conta: {formatarTipoConta(tipo)}</p> {/* Formatar tipo aqui */}
          <p>Saldo: <span>{formatarSaldo(saldo)}</span></p>
        </div>
      </div>
      <div className={style.editar}>
        <button onClick={onEdit}>
          <img
            src="/assets/iconsContas/editar.svg"
            alt="icon editar"
          />
        </button>
        <button onClick={onDelete}>
          <img
            src="/assets/iconsContas/excluir.svg"
            alt="icon excluir"
            className={style.img2}
          />
        </button>
      </div>
    </div>
  );
};

export default CardContas;
