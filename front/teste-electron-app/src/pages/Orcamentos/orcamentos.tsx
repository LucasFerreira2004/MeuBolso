import { useState } from "react";
import DatePicker from "../../components/UI/Date/date";
import style from "./orcamentos.module.css";
import TotalOrcamentos from "../../components/UI/CardsOrcamentos/card-total";
import AddButton from "../../components/UI/AddButton/add-button";

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
        <AddButton
          texto="Adicionar orçamento"
          onClick={function (): void {
            throw new Error("Function not implemented.");
          }}
        />
      </header>
      <main className={style.mainContainer}>
      <div className={style.cards}>
        <TotalOrcamentos/>
        </div>
      </main>
    </div>
  );
}

export default ContasBancarias;
