import style from "./transacoes.module.css"

function Transacoes (){
    return(
        <div className={style.containerTransacoes}>
            <h1>Transações</h1>
            <header>
                <h3>Estimativa do mês</h3>
                <div className={style.rowTransacoes}>
                    <div>
                        <img src="/assets/iconsTransacoes/money.svg" alt="iconMoney" className={style.iconT}/>
                        <p><span className={style.spanM}>Estimativa de Saldo: </span>R$ 1516.00</p>
                    </div>
                    <div>
                        <img src="/assets/iconsTransacoes/arrowred.svg" alt="iconMoney" className={style.iconT}/>
                        <p><span className={style.spanR}>Receitas: </span>R$ 3896.00</p>
                    </div>
                    <div>
                        <img src="/assets/iconsTransacoes/arrowgreen.svg" alt="iconMoney" className={style.iconT}/>
                        <p><span className={style.spanT}>Despesas: </span>R$ 15316.00</p>
                    </div>
                </div>
            </header>
        </div>
        
    );
}

export default Transacoes;