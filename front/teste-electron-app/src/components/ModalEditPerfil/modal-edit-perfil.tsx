import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "./modal-edit-perfil.module.css";

interface ModalEditPerfilProps {
  onClose: () => void;
  usuario: {
    nome: string;
    email: string;
    imgUrl: string | null;
  };
  setUsuario: React.Dispatch<React.SetStateAction<any>>;
}

const ModalEditPerfil: React.FC<ModalEditPerfilProps> = ({ onClose, usuario, setUsuario }) => {
  const [nome, setNome] = useState(usuario.nome);
  const [email, setEmail] = useState(usuario.email);
  const [fotoPerfil, setFotoPerfil] = useState<File | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string>("");

  const handleFotoChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      setFotoPerfil(file);
    }
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const token = localStorage.getItem("authToken");
    if (!token) {
      setError("Você precisa estar logado para realizar esta ação");
      return;
    }

    setLoading(true);

    // Criar o FormData para enviar dados multipart
    const formData = new FormData();
    formData.append("nome", nome);
    formData.append("email", email);
    if (fotoPerfil) {
      formData.append("imgUrl", fotoPerfil);
    }

    try {
      const response = await axios.put(
        "http://localhost:8080/usuarios",
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "multipart/form-data",
          },
        }
      );

      // Atualiza os dados do usuário no componente Perfil
      setUsuario(response.data);
      onClose(); // Fecha o modal
    } catch (error: any) {
      console.error("Erro ao editar dados do usuário:", error);
      setError("Erro ao atualizar dados do usuário");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    setNome(usuario.nome);
    setEmail(usuario.email);
  }, [usuario]);

  return (
    <div className={styles.overlay} onClick={onClose}>
      <div className={styles.modal} onClick={(e) => e.stopPropagation()}>
        <button className={styles.closeButton} onClick={onClose}>
          ✖
        </button>
        <h2>Editar Perfil</h2>
        <div className={styles.fotoPerfil}>
          {fotoPerfil ? (
            <img src={URL.createObjectURL(fotoPerfil)} alt="Foto de Perfil" />
          ) : (
            "Foto de perfil aqui"
          )}
        </div>
        <div className={styles.escolherFoto}>
          <input type="file" onChange={handleFotoChange} />
        </div>
        <form onSubmit={handleSubmit}>
          <div className={styles.formGroup}>
            <label>Nome</label>
            <input
              type="text"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              placeholder="Digite seu nome"
            />
          </div>
          <div className={styles.formGroup}>
            <label>Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Digite seu email"
            />
          </div>
          {error && <div className={styles.error}>{error}</div>}
          <button type="submit" className={styles.saveButton} disabled={loading}>
            {loading ? "Salvando..." : "Salvar"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default ModalEditPerfil;
