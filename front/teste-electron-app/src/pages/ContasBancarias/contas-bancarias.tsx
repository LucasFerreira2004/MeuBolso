import { useState, useEffect } from "react";
import CardContas from "../../components/UI/CardContas/card-contas";
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./contas-bancarias.module.css";
import ModalEditContas from "../../components/ModalEditContas/modal-edit-contas";
import ModalContas from "../../components/ModalContas/modal-contas";
import ModalDeleteConta from "../../components/ModalDeleteConta/modal-delete-conta"; // Modal de exclusão

interface Conta {
  data: any;
  descricao: any;
  id: number;
  saldo: number;
  banco: {
    nome: string;
    iconeUrl: string;
  };
  tipo_conta: {
    tipoConta: string;
  };
  id_banco: number;
  id_tipo_conta: number;
  id_usuario: number;
}

function ContasBancarias() {
  const [contas, setContas] = useState<Conta[]>([]);
  const [openEditModal, setOpenEditModal] = useState(false);
  const [openCreateModal, setOpenCreateModal] = useState(false);
  const [selectedContaId, setSelectedContaId] = useState<number | null>(null);
  const [openDeleteModal, setOpenDeleteModal] = useState(false);
  const [selectedDeleteId, setSelectedDeleteId] = useState<number | null>(null);

  // Função para pegar a data atual no formato YYYY-MM-DD
  const getDataAtual = () => {
    const data = new Date();
    const ano = data.getFullYear();
    const mes = String(data.getMonth() + 1).padStart(2, "0");
    const dia = String(data.getDate()).padStart(2, "0");
    return `${ano}-${mes}-${dia}`;
  };

  // Função para buscar as contas da API
  const fetchContas = () => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      console.error("Token de autenticação não encontrado.");
      return;
    }

    const dataReferencia = getDataAtual();
    const url = `http://localhost:8080/contas?data=${dataReferencia}`;

    fetch(url, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (!response.ok) {
          return response.text().then((text) => {
            throw new Error(`Erro ${response.status}: ${text}`);
          });
        }
        return response.json();
      })
      .then((data) => {
        setContas(data);
      })
      .catch((error) => {
        console.error("Erro ao buscar as contas:", error);
      });
  };

  // Efeito para carregar as contas ao montar o componente
  useEffect(() => {
    fetchContas();
  }, []);

  // Função para abrir o modal de exclusão
  const handleDeleteRequest = (contaId: number) => {
    setSelectedDeleteId(contaId);
    setOpenDeleteModal(true);
  };


  // Função para abrir o modal de edição
  const handleEdit = (contaId: number) => {
    setSelectedContaId(contaId);
    setOpenEditModal(true);
  };

  return (
    <div className={style.contas}>
      <header className={style.headerContas}>
        <h1>Contas Bancárias</h1>
        <div>
          <AddButton
            onClick={() => setOpenCreateModal(true)}
            texto={"Adicionar conta"}
          />
        </div>
      </header>

      {/* Modal de criação de conta */}
      {openCreateModal && (
        <ModalContas
          onCloseAll={() => {
            setOpenCreateModal(false);
            fetchContas();
          }}
        />
      )}

      {/* Modal de edição de conta */}
      {openEditModal && selectedContaId !== null && (
        <ModalEditContas
          contaId={selectedContaId}
          onCloseAll={() => {
            setOpenEditModal(false);
            fetchContas();
          }}
        />
      )}

      {/* Lista de contas */}
      <main className={style.cardsContas}>
        {contas.length === 0 ? (
          <p>Não há contas disponíveis.</p>
        ) : (
          contas.map((conta) => (
            <CardContas
              key={conta.id}
              titulo={conta.banco.nome}
              tipo={conta.tipo_conta.tipoConta.replace("_", " ")}
              saldo={conta.saldo}
              banco={conta.banco.iconeUrl || "/assets/iconsContas/default.svg"}
              altBanco={`Ícone do banco ${conta.banco.nome}`}
              data={conta.data}
              descricao={conta.descricao}
              onDelete={() => handleDeleteRequest(conta.id)} // Chama a função para abrir o modal de exclusão
              onEdit={() => handleEdit(conta.id)}
            />
          ))
        )}
      </main>

      {/* Modal de exclusão de conta */}
      {openDeleteModal && selectedDeleteId !== null && (
  <ModalDeleteConta
    contaId={selectedDeleteId}
    onClose={() => setOpenDeleteModal(false)}
    onConfirmDelete={() => {
      setOpenDeleteModal(false);
      fetchContas(); // ✅ Apenas atualiza a lista, sem outra requisição DELETE
    }}
  />
)}

    </div>
  );
}

export default ContasBancarias;
