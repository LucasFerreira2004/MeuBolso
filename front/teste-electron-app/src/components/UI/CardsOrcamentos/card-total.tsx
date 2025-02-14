import { ProgressBar } from "../ProgressBar/progress-bar";
import style from "./card-total.module.css";

function TotalOrcamentos() {
  return (
    <div className={style.container}>
      <header className={style.header}>
        <h3 className={style.title}>Resumo Geral</h3>
        <ProgressBar value={40} />
      </header>
      <main className={style.main}>
        <li className={style.item}>
          <p className={style.label}>Planejado:</p>
          <p className={style.value}>R$ 12.000,00</p>
        </li>
        <li className={style.item}>
          <p className={style.label}>Gasto:</p>
          <p className={style.value}>R$ 2.000,00</p>
        </li>
        <li className={style.item}>
          <p className={style.label}>Excedeu:</p>
          <p className={style.value}>R$ 0,00</p>
        </li>
      </main>
    </div>
  );
}

export default TotalOrcamentos;
