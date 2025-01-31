import { useState } from "react";
import { Categoria } from "../../pages/Categorias/categorias";
import style from "./modal-edit-categorias.module.css";
import DropDownColors from "../../components/UI/DropDownColors/drop-down-colors";

interface ModalEditCategoriaProps {
  closeModal: () => void;
  categoria: Categoria;
  onCategoriaSaved: () => void;  // Função para atualizar a lista de categorias
}

const sendData = async (categoria: Categoria) => {
  const token = localStorage.getItem("authToken");
  if(!token){
    return{
      success: false,
      error:{message: "Você precisa estar logado para realizar esta açao"},
    };
  }

  try {
    const response = await fetch(`http://localhost:8080/categorias/${categoria.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(categoria),
    });

    if (response.ok) {
      const responseData = await response.json();
      return { success: true, data: responseData };
    } else {
      const errorData = await response.json();
      return { success: false, error: errorData };
    }
  } catch (error) {
    console.error("Erro na requisição", error);
    return { success: false, error: { message: "Erro na conexão com o servidor." } };
  }
};

const ModalEditCategoria: React.FC<ModalEditCategoriaProps> = ({ closeModal, categoria, onCategoriaSaved }) => {
  const [nome, setNome] = useState(categoria.nome);
  const [tipo, setTipo] = useState(categoria.tipo);
  const [cor, setCor] = useState(categoria.cor);
  const [isColorDropdownOpen, setIsColorDropdownOpen] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const categoriaAtualizada = { id: categoria.id, nome, tipo, cor };

    const result = await sendData(categoriaAtualizada);

    if (result.success) {
      console.log("Categoria atualizada com sucesso!");
      onCategoriaSaved();  
      closeModal();        
    } else {
      console.error("Erro ao atualizar categoria:", result.error);
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
        </div>
      </form>
    </div>
  );
};

export default ModalEditCategoria;
