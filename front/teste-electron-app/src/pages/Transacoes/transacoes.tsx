import AddButton from "../../components/UI/AddButton/add-button";
import Date from "../../components/UI/Date/date";
import style from "./transacoes.module.css";

function Transacoes() {
  const handleMonthChange = (month: string) => {
    console.log("Mês selecionado:", month);
  };

  const handleYearChange = (year: string) => {
    console.log("Ano selecionado:", year);
  };

  return (
    <div className={style.containerTransacoes}>
      <h1>Transações</h1>
      <div className={style.headerTransacoes}>
        <h3>Estimativa do mês</h3>
        <div className={style.rowTransacoes}>
          <div>
            <img
              src="/assets/iconsTransacoes/money.svg"
              alt="iconMoney"
              className={style.iconT}
            />
            <p>
              <span className={style.spanM}>Estimativa de Saldo: </span>R$
              1516.00
            </p>
          </div>
          <div>
            <img
              src="/assets/iconsTransacoes/arrowred.svg"
              alt="iconMoney"
              className={style.iconT}
            />
            <p>
              <span className={style.spanR}>Receitas: </span>R$ 3896.00
            </p>
          </div>
          <div>
            <img
              src="/assets/iconsTransacoes/arrowgreen.svg"
              alt="iconMoney"
              className={style.iconT}
            />
            <p>
              <span className={style.spanT}>Despesas: </span>R$ 15316.00
            </p>
          </div>
        </div>
      </div>
      <div className={style.bodyTransacoes}>
        <div className={style.headerBodyT}>
          <Date
            onMonthChange={handleMonthChange}
            onYearChange={handleYearChange}
          />
          <div className={style.search}>
            <input
              className={style.input}
              type="text"
              placeholder="Buscar..."
            />
            <img
              className={style.icon}
              src="/assets/iconsTransacoes/lupa.svg"
              alt="Lupa"
            />
            <img
              className={style.icon}
              src="/assets/iconsTransacoes/filter.svg"
              alt="Filter"
            />
          </div>
          <div>
            <AddButton texto="Realizar Transação" onClick={console.log} />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Transacoes;
