import { useState, useEffect } from "react";
import axios from "axios";
import CardContas from "../../components/CardContas/card-contas";
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./contas-bancarias.module.css";
import ModalEditContas from "../../components/ModalEditContas/modal-edit-contas";
import ModalDeleteConta from "../../components/ModalDeleteConta/modal-delete-conta";
import ModalContas from "../../components/ModalContas/modal-contas";

interface Conta {
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
  const [open, setOpen] = useState(false);
  const [selectedConta, setSelectedConta] = useState<Conta | null>(null);
  const [openDeleteModal, setOpenDeleteModal] = useState(false);
  const [selectedContaToDelete, setSelectedContaToDelete] = useState<Conta | null>(null);
  const [openAddModal, setOpenAddModal] = useState(false);

  const fetchContas = () => {
    const token = localStorage.getItem("authToken"); 
    axios
      .get<Conta[]>("http://localhost:8080/contas", {
        headers: {
          Authorization: `Bearer ${token}`, 
        },
      })
      .then((response) => {
        setContas(response.data);
      })
      .catch((error) => {
        console.error("Erro ao buscar as contas:", error);
      });
  };

  useEffect(() => {
    fetchContas();
  }, []);

  const handleEditClick = (conta: Conta) => {
    setSelectedConta(conta); 
    setOpen(true);
  };

  const handleDeleteClick = (conta: Conta) => {
    setSelectedContaToDelete(conta);
    setOpenDeleteModal(true);
  };

  const handleCloseModal = () => {
    setOpen(false);
    setSelectedConta(null); 
    fetchContas(); 
  };

  const closeDeleteModal = () => {
    setOpenDeleteModal(false);
    setSelectedContaToDelete(null); 
    fetchContas(); 
  };

  const closeAddModal = () => {
    setOpenAddModal(false);
  };

  const handleAddConta = (novaConta: Conta) => {
    setContas((prevContas) => [...prevContas, novaConta]); 
    closeAddModal();
    fetchContas(); 
  };

  const handleConfirmDelete = async (id: number) => {
    try {
      console.log("Excluindo conta com ID:", id); // Verifique o ID da conta
      const token = localStorage.getItem("authToken");
      if (!token) {
        console.error("Token de autenticação não encontrado.");
        return;
      }
      const response = await axios.delete(`http://localhost:8080/contas/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.status === 200) {
        setContas((prevContas) => prevContas.filter((item) => item.id !== id));
        closeDeleteModal();
      } else {
        console.error("Erro ao excluir a conta. Status:", response.status);
      }
    } catch (error) {
      console.error("Erro ao excluir a conta:", error);
    }
  };
  
  

  return (
    <div className={style.contas}>
      <header className={style.headerContas}>
        <h1>Contas Bancárias</h1>
        <div>
          <AddButton texto="Adicionar Conta" onClick={() => setOpenAddModal(true)} />
        </div>
      </header>

      {open && selectedConta && (
        <ModalEditContas closeModal={handleCloseModal} conta={selectedConta} refreshContas={fetchContas} />
      )}

      {openDeleteModal && selectedContaToDelete && (
        <ModalDeleteConta
          onClose={closeDeleteModal}
          onConfirmDelete={handleConfirmDelete}  // Passando a função com ID
          contaId={selectedContaToDelete.id} // Passando o ID da conta
        />
      )}

      {openAddModal && (
        <ModalContas closeModal={closeAddModal} onAddConta={handleAddConta} />
      )}

      <main className={style.cardsContas}>
        {contas.length === 0 ? (
          <p>Não há contas disponíveis.</p>
        ) : (
          contas.map((conta) => (
            <CardContas
              key={conta.id}
              titulo={conta.banco.nome}
              tipo={conta.tipo_conta.tipoConta.replace('_', ' ')}
              saldo={conta.saldo}
              banco={conta.banco.iconeUrl || '/assets/iconsContas/default.svg'}
              altBanco={`Banco ${conta.banco.nome}`}
              onDelete={() => handleDeleteClick(conta)}
              onEdit={() => handleEditClick(conta)} 
            />
          ))
        )}
      </main>
    </div>
  );
}

export default ContasBancarias;
