import style from "./card-contas.module.css";

interface CardContasProps {
  titulo: string;
  tipo: string;
  saldo: number;
  banco: string;
  altBanco: string;
}

const CardContas: React.FC<CardContasProps> = ({ titulo, tipo, saldo, banco, altBanco }) => {
  // Função para formatar o saldo como moeda
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
          <img src={banco} alt={altBanco} />  {/* Exibe o ícone do banco */}
        </div>
        <div className={style.infoText}>
          <h3>{titulo}</h3>
          <p>Conta: {tipo}</p>
          <p>Saldo: <span>{formatarSaldo(saldo)}</span></p>  {/* Exibe o saldo formatado */}
        </div>
      </div>
      <div className={style.editar}>
        <button>
          <img
            src="/assets/iconsContas/editar.svg"
            alt="icon editar"
            onClick={() => console.log('clicou')}
          />
        </button>
        <button>
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
