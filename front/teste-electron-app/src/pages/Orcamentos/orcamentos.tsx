import { useState } from "react";
import DatePicker from "../../components/UI/Date/date";
import style from "./orcamentos.module.css";

function ContasBancarias() {
  const [mes, setMes] = useState(new Date().getMonth() + 1);
  const [ano, setAno] = useState(new Date().getFullYear());

  return (
    <div className={style.orcamentos}>
      <header className={style.headerOrcamentos}>
        <h1>Orçamentos</h1>
        <div className={style.dateWrapper}>
          <DatePicker
            mes={mes}
            ano={ano}
            onChange={(novoMes, novoAno) => {
              setMes(novoMes);
              setAno(novoAno);
            }}
          />
        </div>
      </header>
      <main className={style.mainContainer}>
      <div className={style.cards}>
          
        </div>
      </main>
    </div>
  );
}

export default ContasBancarias;
