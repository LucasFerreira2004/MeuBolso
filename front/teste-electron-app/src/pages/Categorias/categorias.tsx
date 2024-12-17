import { useState, useEffect } from "react";
import ModalAddCategoria from "../../components/ModalAddCategoria/modal-add-categoria";
import ModalEditCategoria from "../../components/ModalEditCategorias/modal-edit-categorias";
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./categorias.module.css";
import InputCategorias from "../../components/UI/InputCategorias/input-categorias";

export interface Categoria {
  id: number;
  nome: string;
  tipo: string;
  cor: string;
}

function Categorias() {
  const [open, setOpen] = useState(false);
  const [editMode, setEditMode] = useState(false); // Para saber se estamos editando ou adicionando
  const [categorias, setCategorias] = useState<Categoria[]>([]); 
  const [loading, setLoading] = useState<boolean>(true); 
  const [categoriaToEdit, setCategoriaToEdit] = useState<Categoria | null>(null); // Categoria que será editada

  useEffect(() => {
    fetchCategorias();
  }, []);

  const fetchCategorias = async () => {
    setLoading(true);
    try {
      const response = await fetch("http://localhost:8080/categorias");
      const data: Categoria[] = await response.json();
      setCategorias(data);
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
        <div>
          <AddButton texto="Adicionar Categoria" onClick={handleAddClick} />
        </div>
      </header>

      {/* Exibindo o modal dependendo do estado */}
      {open && (
        <>
          <div className={style.overlay} onClick={() => setOpen(false)} />
          {editMode && categoriaToEdit ? (
            <ModalEditCategoria
              closeModal={() => setOpen(false)} 
              categoria={categoriaToEdit} // Passa a categoria para o modal de edição
              onCategoriaSaved={handleCategoriaSaved} // Passa a função de atualização
            />
          ) : (
            <ModalAddCategoria 
              closeModal={() => setOpen(false)} 
              onCategoriaSaved={handleCategoriaSaved} // Passa a função de atualização
            />
          )}
        </>
      )}

      <main className={style.containerMain}>
        <div className={style.cardCategorias}>
          {/* Renderizando categorias de despesas */}
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
                  onClick={() => handleEditClick(categoria)} // Abre o modal de edição ao clicar
                />
              ))}
          </div>
              <hr className={style.hrCentral}/>
          {/* Renderizando categorias de receitas */}
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
                  onClick={() => handleEditClick(categoria)} // Abre o modal de edição ao clicar
                />
              ))}
          </div>
        </div>
      </main>
    </div>
  );
}

function toTitleCase(str: string): string {
  return str.toLowerCase().replace(/\b\w/g, (c) => c.toUpperCase());
}

export default Categorias;
