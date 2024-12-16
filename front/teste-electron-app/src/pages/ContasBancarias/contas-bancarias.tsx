import { useState, useEffect } from "react";
import axios from "axios";
import CardContas from "../../components/CardContas/card-contas";
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./contas-bancarias.module.css";
import ModalEditContas from "../../components/ModalEditContas/modal-edit-contas";
import ModalDeleteConta from "../../components/ModalDeleteConta/modal-delete-conta";
import ModalContas from "../../components/ModalContas/modal-contas"; // Importando o ModalContas

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
  const [openAddModal, setOpenAddModal] = useState(false); // Modal de adição de conta

  // Requisição à API para pegar as contas bancárias e seus ícones
  useEffect(() => {
    axios
      .get<Conta[]>("http://localhost:8080/contas")
      .then((response) => {
        setContas(response.data);
      })
      .catch((error) => {
        console.error("Erro ao buscar as contas:", error);
      });
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
  };

  const closeDeleteModal = () => {
    setOpenDeleteModal(false);
    setSelectedContaToDelete(null); 
  };

  const closeAddModal = () => {
    setOpenAddModal(false);
  };

  const handleAddConta = (novaConta: Conta) => {
    setContas((prevContas) => [...prevContas, novaConta]); // Adiciona a nova conta ao estado local
    closeAddModal(); // Fecha o modal após adicionar
  };

  const handleConfirmDelete = async (conta: Conta) => {
    try {
      const response = await axios.delete(`http://localhost:8080/contas/${conta.id}`);
      if (response.status === 200) {
        setContas((prevContas) => prevContas.filter((item) => item.id !== conta.id));
        closeDeleteModal();
      } else {
        console.error("Erro ao excluir a conta");
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
        <ModalEditContas closeModal={handleCloseModal} conta={selectedConta} />
      )}

      {openDeleteModal && selectedContaToDelete && (
        <ModalDeleteConta
          onClose={closeDeleteModal}
          onConfirmDelete={() => handleConfirmDelete(selectedContaToDelete)}
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
