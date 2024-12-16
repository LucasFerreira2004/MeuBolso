import { useState } from "react";
import { Categoria } from "../../pages/Categorias/categorias"; // Certifique-se de que o caminho está correto
import style from "./modal-edit-categorias.module.css";
import DropDownColors from "../../components/UI/DropDownColors/drop-down-colors";


interface ModalEditCategoriaProps {
  closeModal: () => void;
  categoria: Categoria;
}

// Função para enviar os dados ao servidor (agora com PUT)
const sendData = async (categoria: Categoria) => {
  try {
    const response = await fetch(`http://localhost:8080/categorias/${categoria.id}`, {
      method: "PUT", // Usando PUT para atualizar uma categoria existente
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(categoria), // Envia o objeto completo da categoria
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


const ModalEditCategoria: React.FC<ModalEditCategoriaProps> = ({ closeModal, categoria }) => {
  const [nome, setNome] = useState(categoria.nome); // Estado para armazenar o nome
  const [tipo, setTipo] = useState(categoria.tipo); // Estado para armazenar o tipo
  const [cor, setCor] = useState(categoria.cor); // Estado para armazenar a cor
  const [isColorDropdownOpen, setIsColorDropdownOpen] = useState(false); // Estado para controlar o dropdown de cor

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Criar um objeto de categoria com os valores dos campos
    const categoriaAtualizada = { id: categoria.id, nome, tipo, cor };

    const result = await sendData(categoriaAtualizada);

    if (result.success) {
      console.log("Categoria atualizada com sucesso!");
      closeModal(); // Fechar o modal após sucesso
    } else {
      console.error("Erro ao atualizar categoria:", result.error);
    }
  };

  return (
    <div className={style.modalContainer}>
      <div className={style.modalHeader}>
        <h3>Editar Categoria</h3>
        <img
          src="/assets/iconsModal/iconX.svg"
          alt="Fechar modal"
          className={style.closeIcon}
          onClick={closeModal} // Chama a função de fechar o modal
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
            onChange={(e) => setNome(e.target.value)}  // Atualiza o estado conforme o usuário digita 
          />
          <button
            type="button"
            className={style.colorButton}
            onClick={() => setIsColorDropdownOpen(!isColorDropdownOpen)} // Alterna a visibilidade do dropdown de cor
          >
            <div className={style.colorSet} style={{ backgroundColor: cor }}></div>
          </button>

          {/* Mostrar o dropdown de cores quando o estado for verdadeiro */}
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
          <button type="button" className={style.deleteButton}>
            Excluir
          </button>
          <button type="submit" className={style.saveButton}>
            Salvar
          </button>
        </div>
      </form>
    </div>
  );
};

export default ModalEditCategoria;
