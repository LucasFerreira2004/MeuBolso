import { useState } from "react";
import ModalAddCategoria from "../../components/ModalAddCategoria/modal-add-categoria";
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./categorias.module.css";

function Categorias () {
  const [open, setOpen] = useState(false);

  return (
    <div className={style.containerCategorias}>
      <header className={style.headerCategorias}>
        <h1>Categorias</h1>
        <div>
          <AddButton texto="Adicionar Categoria" onClick={() => setOpen(true)} />
        </div>
      </header>

      {/* Exibe o modal apenas se 'open' for true */}
      {open && (
        <>
          <div className={style.overlay} onClick={() => setOpen(false)} /> {/* Overlay */}
          <ModalAddCategoria closeModal={() => setOpen(false)} /> {/* Passa a função de fechar */}
        </>
      )}

      <main className={style.containerMain}>
        <div className={style.cardCategorias}>
          <div className={style.cardDespesa}></div>
          <div className={style.cardReceita}></div>
        </div>
      </main>
    </div>
  );
}

export default Categorias;
