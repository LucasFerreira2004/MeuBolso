import React, { useState, useEffect } from 'react';
import styles from './modal-arquivadas.module.css';
import axios from 'axios';
import fetchCategorias from '../../pages/Categorias/categorias';

interface Categoria {
  id: number;
  nome: string;
  arquivado: boolean;
}

interface ModalArquivadasProps {
  onClose: () => void;
}

const ModalArquivadas: React.FC<ModalArquivadasProps> = ({ onClose }) => {
  const [categoriasArquivadas, setCategoriasArquivadas] = useState<Categoria[]>([]);

  useEffect(() => {
    fetchCategoriasArquivadas();
  }, []);

  const fetchCategoriasArquivadas = async () => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      return;
    }

    try {
      const response = await axios.get("http://localhost:8080/categorias/arquivadas", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setCategoriasArquivadas(response.data);
    } catch (error) {
      console.error("Erro ao buscar categorias arquivadas:", error);
    }
  };

  const handleArquivar = async (id: number, arquivado: boolean) => {
    await arquivarCategoria(id, arquivado);
    fetchCategoriasArquivadas(); // Atualiza o modal também
    fetchCategorias(); // Atualiza a lista de categorias
  };

  const arquivarCategoria = async (id: number, arquivado: boolean) => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      return;
    }

    try {
      await axios.patch(
        `http://localhost:8080/categorias/arquivadas/${id}`,
        { arquivado },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      fetchCategoriasArquivadas(); // Atualiza a lista após a modificação
    } catch (error) {
      console.error("Erro ao arquivar/desarquivar categoria:", error);
    }
  };

  return (
    <div className={styles.modalArquivadasOverlay}>
      <div className={styles.modalArquivadasContent}>
        <h2>Arquivadas</h2>
        <ul>
          {categoriasArquivadas.map((categoria: Categoria) => (
            <li key={categoria.id}>
              {categoria.nome}
              <button onClick={() => handleArquivar(categoria.id, !categoria.arquivado)}>
                {categoria.arquivado ? "Desarquivar" : "Arquivar"}
              </button>
            </li>
          ))}
        </ul>
        <button onClick={onClose}>Fechar</button>
      </div>
    </div>
  );
};

export default ModalArquivadas;
