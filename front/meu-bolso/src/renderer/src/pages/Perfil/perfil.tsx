import { useEffect, useState } from "react";
import axios from "axios";
import style from "./perfil.module.css";
import BalancoBancos from "../../components/UI/ChartsRelatorios/BalancoBancos/balanco-bancos";
import ModalEditPerfil from "../../components/ModalEditPerfil/modal-edit-perfil";
import { baseUrl } from "../../api/api";

interface Usuario {
  nome: string;
  email: string;
  imgUrl: string | null;
}

function Perfil() {
  const [usuario, setUsuario] = useState<Usuario>({
    nome: "",
    email: "",
    imgUrl: null,
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const fetchUsuario = async () => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      setError("Você precisa estar logado para realizar esta ação");
      setLoading(false);
      return;
    }

    setLoading(true);
    try {
      const response = await axios.get(`${baseUrl}/usuarios`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      setUsuario(response.data);
    } catch (error) {
      console.error("Erro ao buscar dados do usuário:", error);
      if (axios.isAxiosError(error)) {
        console.error("Resposta do servidor:", error.response?.data);
        console.error("Status do erro:", error.response?.status);
      }
      setError("Erro ao carregar dados do usuário");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUsuario();
  }, []);

  if (loading) {
    return <div>Carregando...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <div className={style.containerPerfil}>
      <header className={style.headerPerfil}>
        <h1>Perfil</h1>
      </header>
      <div className={style.body}>
        <div className={style.headerPerfil}>
          <div className={style.fotoPerfil}>
            <img src="assets/sung.svg" alt="" className={style.fotoPerfil}/>
          </div>
          <div className={style.infoPerfil}>
            <div className={style.infoItem}>
              <strong>Nome:</strong>
              <span>{usuario.nome}</span>
            </div>
            <div className={style.infoItem}>
              <strong>Email:</strong>
              <span>{usuario.email}</span>
            </div>
          </div>
        </div>
        <div className={style.classButton}>
          <button className={style.button} onClick={() => setIsModalOpen(true)}>
            <img src="assets/iconsPerfil/editperfil.svg" alt="" />
            Editar informações
          </button>
        </div>
      </div>

      <BalancoBancos />

      {isModalOpen && (
        <ModalEditPerfil
          onClose={() => setIsModalOpen(false)}
          usuario={usuario}
          setUsuario={setUsuario}
        />
      )}
    </div>
  );
}

export default Perfil;