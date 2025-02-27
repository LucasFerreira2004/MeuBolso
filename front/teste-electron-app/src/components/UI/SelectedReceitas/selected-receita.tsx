import { useEffect, useState } from "react";
import Select from "react-select";
import style from "./selected-receita.module.css"; 

interface Categoria {
  id: number;
  nome: string;
  tipo: string;
  cor: string;
}

interface SelectedReceitaProps {
  setCategoria: (categoriaId: number | null) => void; 
}

function SelectedReceita({ setCategoria }: SelectedReceitaProps) {
  const [categorias, setCategorias] = useState<Categoria[]>([]);

  useEffect(() => {
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      return;
    }

    const url = "http://localhost:8080/categorias/receitas"; 

    fetch(url, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Erro ao buscar categorias.");
        }
        return response.json();
      })
      .then((data: Categoria[]) => {
        setCategorias(data);
      })
      .catch((error) => console.error("Erro ao buscar categorias:", error));
  }, []);

  const options = categorias.map((categoria) => ({
    value: categoria.id,
    label: (
      <div style={{ display: "flex", alignItems: "center" }}>
        <span
          style={{
            backgroundColor: `#${categoria.cor}`,
            padding: "5px",
            borderRadius: "4px",
            color: "white",
            marginRight: "10px",
          }}
        >
          {categoria.nome}
        </span>
      </div>
    ),
  }));

  const handleChange = (selectedOption: any) => {
    const categoriaSelecionada = selectedOption ? selectedOption.value : null;
    setCategoria(categoriaSelecionada); // Passando a categoria para o componente pai
  };

  return (
    <div className={style.selectBoxContainer}>
      <div className={style.selectBoxWrapper}>
        <label className={style.selectBoxLabel}>Categoria:</label>
        <Select
          options={options}
          onChange={handleChange}
          placeholder="Selecione uma categoria"
          className={style.selectBox}
          classNamePrefix="react-select"
        />
      </div>
    </div>
  );
}

export default SelectedReceita;
