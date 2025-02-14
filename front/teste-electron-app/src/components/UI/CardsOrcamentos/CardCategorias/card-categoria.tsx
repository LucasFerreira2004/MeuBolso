import { useState } from "react";
import { ProgressBar } from "../../ProgressBar/progress-bar";
import ModalEditOrcamento from "../../../ModalEditOrcamento/modal-edit-orcamento"; // Certifique-se de ajustar o caminho do import
import style from "./card-categoria.module.css";

function TotalCategorias() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleOpenModal = () => {
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className={style.container}>
      <header className={style.header}>
        <h3 className={style.title}>Categoria aqui</h3>
        <div className={style.iconsEdit}>
          <button onClick={handleOpenModal}>
            <img src="/assets/iconsContas/editar.svg" alt="Editar" />
          </button>
          <button>
            <img src="/assets/iconsContas/excluir.svg" alt="Excluir" />
          </button>
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

      {isModalOpen && (
        <ModalEditOrcamento
          valor="12000" // Passe os valores necessários como props
          data="2025-02-13"
          onCloseAll={handleCloseModal}
          handleChangeValor={(e) => console.log(e.target.value)}
          setCategoria={(categoriaId) => console.log(categoriaId)}
          setData={(date) => console.log(date)}
        />
      )}
    </div>
  );
}

export default TotalCategorias;
