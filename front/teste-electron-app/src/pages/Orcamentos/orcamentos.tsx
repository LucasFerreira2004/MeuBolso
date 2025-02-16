import { useState } from "react";
import DatePicker from "../../components/UI/Date/date";
import style from "./orcamentos.module.css";
import AddButton from "../../components/UI/AddButton/add-button";
import TotalCategorias from "../../components/UI/CardsOrcamentos/CardCategorias/card-categoria";
import ModalAddOrcamento from "../../components/ModalAddOrcamento/modal-add-orcamento"; 

function Orcamentos() {
  const [mes, setMes] = useState(new Date().getMonth() + 1);
  const [ano, setAno] = useState(new Date().getFullYear());
  const [modalAberto, setModalAberto] = useState(false); 

  const abrirModal = () => {
    setModalAberto(true);
  };

  const fecharModal = () => {
    setModalAberto(false); 
  };

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
          onClick={abrirModal} 
        />
      </header>

      <main className={style.mainContainer}>
        <div className={style.cards}>
          <TotalCategorias />
        </div>
      </main>

      {modalAberto && (
        <ModalAddOrcamento
          onCloseAll={fecharModal} 
        />
      )}
    </div>
  );
}

export default Orcamentos;