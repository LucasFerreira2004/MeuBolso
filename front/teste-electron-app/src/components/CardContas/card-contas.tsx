import style from "./card-contas.module.css";

interface CardContasProps {
  titulo: string;
  tipo: string;
  saldo: number;
  banco: string;
  altBanco: string;
  onDelete: () => void;
  onEdit: () => void; // Nova função para editar
}

const CardContas: React.FC<CardContasProps> = ({ titulo, tipo, saldo, banco, altBanco, onDelete, onEdit }) => {
  const formatarSaldo = (valor: number) => {
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
          <p>Conta: {tipo}</p>
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
