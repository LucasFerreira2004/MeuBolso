import style from "./card-contas.module.css";

interface CardContasProps {
    titulo: string;
    tipo: string;
    saldo: number;
    estado: string;
    banco: string;
    altBanco: string;
    iconEstado: string;
}

const CardContas: React.FC<CardContasProps> = ({ titulo, tipo, saldo, estado, iconEstado, banco, altBanco }) => {
    return (
        <div className={style.cardContas}>
            <div className={style.parteInfo}>
                <div className={style.imagemContainer}>
                    <img src={banco} alt={altBanco} />
                </div>
                <div className={style.infoText}>
                    <h3>{titulo}</h3>
                    <p>Conta: {tipo}</p>
                    <p>Saldo: <span>R$ {saldo}</span></p>
                    <div className={style.estado}>
                        <img src={iconEstado} alt="Ícone Estado" />
                        <p>{estado}</p>
                    </div>
                </div>
            </div>
            <div className={style.editar}>
                <button><img src="/assets/iconsContas/editar.svg" alt="icon editar"/></button>
                <button><img src="/assets/iconsContas/excluir.svg" alt="icon excluir" className={style.img2}/></button>
                
            </div>
        </div>
    );
}

export default CardContas;
