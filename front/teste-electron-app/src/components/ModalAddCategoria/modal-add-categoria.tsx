import { useState } from "react";
import style from "./modal-add-categoria.module.css";
import DropDownColors from "../UI/DropDownCategories/drop-down-colors";

interface ModalAddCategoriaProps {
  closeModal: () => void; // Função para fechar o modal
}

function ModalAddCategoria({ closeModal }: ModalAddCategoriaProps) {
  const [openColors, setOpenColors] = useState(false); // Controle do estado de visibilidade do dropdown

  return (
    <div className={style.modalContainer}>
      <div className={style.modalHeader}>
        <h3>Nova Categoria</h3>
        <img
          src="/assets/iconsModal/iconX.svg"
          alt="Fechar modal"
          className={style.closeIcon}
          onClick={closeModal} // Fecha o modal quando clicado
        />
      </div>
      <form className={style.modalForm}>
        <div className={style.formGroup}>
          <label htmlFor="nome">Nome: </label>
          <input id="nome" type="text" placeholder="Digite o nome da categoria" />
          <button
            type="button"
            onClick={() => setOpenColors(!openColors)} // Alterna o estado para abrir/fechar o dropdown
          >
            <img src="/assets/iconsModal/selectcolor.svg" alt="select color" />
          </button>
        </div>
        <div className={style.formGroup}>
          <label htmlFor="descricao">Descrição (opcional): </label>
          <input
            id="descricao"
            type="text"
            placeholder="Adicione uma descrição"
          />
        </div>
        <div className={style.radioGroup}>
          <label>
            <input type="radio" name="tipo" value="despesas" />
            Despesas
          </label>
          <label>
            <input type="radio" name="tipo" value="receitas" />
            Receitas
          </label>
        </div>
        <div className={style.formActions}>
          <button type="submit" className={style.saveButton}>
            Salvar
          </button>
        </div>
      </form>

      {/* Renderiza o dropdown condicionalmente */}
      {openColors && <DropDownColors />}
    </div>
  );
}

export default ModalAddCategoria;
