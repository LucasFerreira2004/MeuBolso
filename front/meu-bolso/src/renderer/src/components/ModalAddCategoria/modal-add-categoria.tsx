import { useState } from "react";
import style from "./modal-add-categoria.module.css";
import DropDownColors from "../UI/DropDownColors/drop-down-colors";
import { baseUrl } from "../../api/api";

interface ModalAddCategoriaProps {
  closeModal: () => void;
  onCategoriaSaved: () => void;  // Função para notificar que a categoria foi salva
}

interface CategoriaProps {
  cor: string;
  nome: string;
  tipo: string;
}

const sendData = async ({ cor, nome, tipo }: CategoriaProps) => {
  const token = localStorage.getItem("authToken"); //isso aqui serve pra pegar o token, e usar dnv
  if (!token) { //faz uma verificacaao do token
    return {
      success: false,
      error: { message: "Você precisa estar logado para realizar esta ação" },
    };
  }
  try {
    const response = await fetch(`${baseUrl}/categorias`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`, //valida o token no cabeçalho
      },
      body: JSON.stringify({ cor, nome, tipo }),
    });

    if (response.ok) {
      return { success: true, data: await response.json() };
    } else {
      const errorData = await response.json();
      return { success: false, error: errorData };
    }
  } catch (error) {
    console.error("Erro na requisição", error);
    return { success: false, error: { message: "Erro na conexão com o servidor." } };
  }
};


function ModalAddCategoria({ closeModal, onCategoriaSaved }: ModalAddCategoriaProps) {
  const [openColors, setOpenColors] = useState(false);
  const [name, setName] = useState("");
  const [selectedValue, setSelectedValue] = useState<string>("");
  const [color, setColor] = useState<string>("#000fff000");
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedValue(event.target.value);
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    if (!name || !selectedValue || !color) {
      alert("Por favor, preencha todos os campos obrigatórios.");
      return;
    }

    const tipoUpperCase = selectedValue.toUpperCase();

    const result = await sendData({
      cor: color,
      nome: name,
      tipo: tipoUpperCase,
    });

    if (result.success) {
      onCategoriaSaved();  // Chama a função para atualizar a lista de categorias
      closeModal();  // Fecha o modal após salvar
    } else {
      setErrorMessage(result.error?.message || "Erro ao criar a categoria.");
    }
  };

  return (
    <div className={style.modalContainerCategoria}>
      <div className={style.modalHeader}>
        <h3>Nova Categoria</h3>
        <img
          src="assets/iconsModal/iconX.svg"
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
            onChange={(e) => setName(e.target.value)}
            value={name}
          />
          <button
            type="button"
            className={style.colorButton}
            onClick={() => setOpenColors(!openColors)}
          >
            <div
              className={style.colorSet}
              style={{ backgroundColor: color }}
            ></div>
          </button>
        </div>

        <div className={style.radioGroup}>
          <label>
            <input
              type="radio"
              name="tipo"
              value="despesa"
              checked={selectedValue === "despesa"}
              onChange={handleChange}
            />
            Despesas
          </label>
          <label>
            <input
              type="radio"
              name="tipo"
              value="receita"
              checked={selectedValue === "receita"}
              onChange={handleChange}
            />
            Receitas
          </label>
        </div>

        {errorMessage && (
          <div className={style.errorMessage}>
            {errorMessage}
          </div>
        )}

        <div className={style.formActions}>
          <button type="submit" className={style.saveButton}>
            Salvar
          </button>
        </div>
      </form>

      {openColors && <DropDownColors setColor={setColor} />}
    </div>
  );
}

export default ModalAddCategoria;
