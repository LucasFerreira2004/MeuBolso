import { useState } from "react";
import { Categoria } from "../../pages/Categorias/categorias";
import style from "./modal-edit-categorias.module.css";
import DropDownColors from "../../components/UI/DropDownColors/drop-down-colors";
import axios from "axios";

interface ModalEditCategoriaProps {
  closeModal: () => void;
  categoria: Categoria;
  onCategoriaSaved: () => void;
  onCategoriaArchived: (categoriaId: number, arquivado: boolean) => void;
}

const ModalEditCategoria: React.FC<ModalEditCategoriaProps> = ({
  closeModal,
  categoria,
  onCategoriaSaved,
  onCategoriaArchived,
}) => {
  const [nome, setNome] = useState(categoria.nome);
  const [tipo, setTipo] = useState(categoria.tipo);
  const [cor, setCor] = useState(categoria.cor);
  const [isColorDropdownOpen, setIsColorDropdownOpen] = useState(false);

  const handleArchive = () => {
    onCategoriaArchived(categoria.id, !categoria.arquivado);
    closeModal();
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const token = localStorage.getItem("authToken");
    if (!token) {
      return;
    }

    try {
      await axios.put(
        `http://localhost:8080/categorias/${categoria.id}`,
        { nome, tipo, cor },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );
      console.log("Categoria atualizada com sucesso!");
      onCategoriaSaved();
      closeModal();
    } catch (error) {
      console.error("Erro ao atualizar categoria:", error);
    }
  };

  return (
    <div className={style.modalContainerCategoria}>
      <div className={style.modalHeader}>
        <h3>Editar Categoria</h3>
        <img
          src="/assets/iconsModal/iconX.svg"
          alt="Fechar modal"
          className={style.closeIcon}
          onClick={closeModal}
        />
      </div>

      <form className={style.modalForm} onSubmit={handleSubmit}>
        <div className={style.formGroup}>
          <label htmlFor="nome">Nome: </label>
          <input
            id="nome"
            type="text"
            placeholder="Digite o nome da categoria"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
          />
          <button
            type="button"
            className={style.colorButton}
            onClick={() => setIsColorDropdownOpen(!isColorDropdownOpen)}
          >
            <div className={style.colorSet} style={{ backgroundColor: cor }}></div>
          </button>

          {isColorDropdownOpen && <DropDownColors setColor={setCor} />}
        </div>

        <div className={style.radioGroup}>
          <label>
            <input
              type="radio"
              name="tipo"
              value="despesa"
              checked={tipo === "DESPESA"}
              onChange={() => setTipo("DESPESA")}
            />
            Despesas
          </label>
          <label>
            <input
              type="radio"
              name="tipo"
              value="receita"
              checked={tipo === "RECEITA"}
              onChange={() => setTipo("RECEITA")}
            />
            Receitas
          </label>
        </div>

        <div className={style.formActions}>
          <button type="submit" className={style.saveButton}>
            Salvar
          </button>
          <button type="button" className={style.archiveButton} onClick={handleArchive}>
            {categoria.arquivado ? "Desarquivar" : "Arquivar"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default ModalEditCategoria;