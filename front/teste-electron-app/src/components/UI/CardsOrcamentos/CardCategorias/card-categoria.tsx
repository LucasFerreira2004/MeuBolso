import { ProgressBar } from "../../ProgressBar/progress-bar";
import style from "./card-categoria.module.css";

function TotalCategorias() {
  return (
    <div className={style.container}>
      <header className={style.header}>
        <h3 className={style.title}>Categoria aqui</h3>
        <div className={style.iconsEdit}>
            <img src="/assets/iconsContas/editar.svg" alt="Editar" />
            <img src="/assets/iconsContas/excluir.svg" alt="Editar" />
        </div>
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
          <p className={style.label}>Progresso:</p>
          <ProgressBar value={40} />
        </li>
        <li className={style.item}>
          <p className={style.label}>Excedeu:</p>
          <p className={style.value}>R$ 0,00</p>
        </li>
      </main>
    </div>
  );
}

export default TotalCategorias;
