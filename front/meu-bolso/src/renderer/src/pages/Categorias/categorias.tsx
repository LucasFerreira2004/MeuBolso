import { useState, useEffect } from "react";
import ModalAddCategoria from "../../components/ModalAddCategoria/modal-add-categoria";
import ModalEditCategoria from "../../components/ModalEditCategorias/modal-edit-categorias";
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./categorias.module.css";
import InputCategorias from "../../components/UI/InputCategorias/input-categorias";
import axios from "axios";
import { baseUrl } from "../../api/api";

export interface Categoria {
  id: number;
  nome: string;
  tipo: string;
  cor: string;
}

function Categorias() {
  const [open, setOpen] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [categorias, setCategorias] = useState<Categoria[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [categoriaToEdit, setCategoriaToEdit] = useState<Categoria | null>(null);

  useEffect(() => {
    fetchCategorias();
  }, []);

  const fetchCategorias = async () => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      return;
    }

    setLoading(true);
    try {
      const response = await axios.get(`${baseUrl}/categorias`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setCategorias(response.data);
    } catch (error) {
      console.error("Erro ao buscar categorias:", error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <p>Carregando categorias...</p>;
  }

  const handleEditClick = (categoria: Categoria) => {
    setCategoriaToEdit(categoria);
    setEditMode(true);
    setOpen(true);
  };

  const handleAddClick = () => {
    setEditMode(false);
    setCategoriaToEdit(null);
    setOpen(true);
  };

  const handleCategoriaSaved = () => {
    fetchCategorias();
    setOpen(false);
  };

  return (
    <div className={style.containerCategorias}>
      <header className={style.headerCategorias}>
        <h1>Categorias</h1>
        <div className={style.buttons}>
          <AddButton texto="Adicionar Categoria" onClick={handleAddClick} />
        </div>
      </header>

      {open && (
        <>
          <div className={style.overlay} onClick={() => setOpen(false)} />
          {editMode && categoriaToEdit ? (
            <ModalEditCategoria
              closeModal={() => setOpen(false)}
              categoria={categoriaToEdit}
              onCategoriaSaved={handleCategoriaSaved}
            />
          ) : (
            <ModalAddCategoria
              closeModal={() => setOpen(false)}
              onCategoriaSaved={handleCategoriaSaved}
            />
          )}
        </>
      )}

      <main className={style.containerMain}>
        <div className={style.cardCategorias}>
          <div className={style.cardDespesa}>
            <h3>Despesas</h3>
            <hr />
            {categorias
              .filter((categoria) => categoria.tipo === "DESPESA")
              .map((categoria) => (
                <InputCategorias
                  key={categoria.id}
                  id={categoria.id}
                  nome={toTitleCase(categoria.nome)}
                  tipo={categoria.tipo}
                  cor={categoria.cor}
                  onClick={() => handleEditClick(categoria)}
                />
              ))}
          </div>
          <hr className={style.hrCentral}/>
          <div className={style.cardReceita}>
            <h3>Receitas</h3>
            <hr />
            {categorias
              .filter((categoria) => categoria.tipo === "RECEITA")
              .map((categoria) => (
                <InputCategorias
                  key={categoria.id}
                  id={categoria.id}
                  nome={toTitleCase(categoria.nome)}
                  tipo={categoria.tipo}
                  cor={categoria.cor}
                  onClick={() => handleEditClick(categoria)}
                />
              ))}
          </div>
        </div>
      </main>
    </div>
  );
}

function toTitleCase(str: string): string {
  return str.toLowerCase().replace(/(?:^|\s)\w/g, (match) => match.toUpperCase());
}

export default Categorias;