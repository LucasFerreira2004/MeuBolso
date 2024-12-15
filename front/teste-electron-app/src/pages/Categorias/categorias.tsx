import { useState, useEffect } from "react";
import ModalAddCategoria from "../../components/ModalAddCategoria/modal-add-categoria";
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./categorias.module.css";
import InputCategorias from "../../components/UI/InputCategorias/input-categorias";

// Defina o tipo para a categoria
interface Categoria {
  id: number;
  nome: string;
  tipo: string;
  cor: string;
}

function Categorias() {
  const [open, setOpen] = useState(false);
  const [categorias, setCategorias] = useState<Categoria[]>([]); 
  const [loading, setLoading] = useState<boolean>(true); 

  useEffect(() => {
    // Função para buscar as categorias da API
    const fetchCategorias = async () => {
      try {
        const response = await fetch("http://localhost:8080/categorias"); // Substitua pela URL da sua API
        const data: Categoria[] = await response.json();
        setCategorias(data); // Atualiza o estado com as categorias recebidas
      } catch (error) {
        console.error("Erro ao buscar categorias:", error);
      } finally {
        setLoading(false); // Define o carregamento como falso
      }
    };

    fetchCategorias();
  }, []);

  if (loading) {
    return <p>Carregando categorias...</p>; 
  }

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
                  nome={categoria.nome}
                  tipo={categoria.tipo}
                  cor={categoria.cor}
                />
              ))}
          </div>
          
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
                  nome={categoria.nome}
                  tipo={categoria.tipo}
                  cor={categoria.cor}
                />
              ))}
          </div>
        </div>
      </main>
    </div>
  );
}

export default Categorias;
