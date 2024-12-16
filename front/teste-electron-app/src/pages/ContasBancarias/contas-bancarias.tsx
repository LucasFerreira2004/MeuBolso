import { useEffect, useState } from "react";
import axios from "axios";
import CardContas from "../../components/CardContas/card-contas";
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./contas-bancarias.module.css";
import ModalContas from "../../components/ModalContas/modal-contas";
import ModalDeleteConta from "../../components/ModalDeleteConta/modal-delete-conta";  // Importando o ModalDeleteConta

interface Conta {
  id: number;
  saldo: number;
  banco: {
    nome: string;
    iconeUrl: string;  // Campo para o ícone
  };
  tipo_conta: {
    tipoConta: string;
  };
}

function ContasBancarias() {
  const [contas, setContas] = useState<Conta[]>([]);
  const [open, setOpen] = useState(false);
  const [openDeleteModal, setOpenDeleteModal] = useState(false);
  const [accountToDelete, setAccountToDelete] = useState<number | null>(null); // Armazena a conta a ser excluída

  // Requisição à API para pegar as contas bancárias e seus ícones
  useEffect(() => {
    axios
      .get<Conta[]>("http://localhost:8080/contas")  // Substitua pelo endpoint correto
      .then((response) => {
        console.log("Dados da API:", response.data);  // Verifica os dados no console
        setContas(response.data); // Atualiza o estado com os dados da API
      })
      .catch((error) => {
        console.error("Erro ao buscar as contas:", error);
      });
  }, []);

  // Função para excluir a conta
  const handleDelete = (id: number) => {
    // Excluindo a conta via API
    axios
      .delete(`http://localhost:8080/contas/${id}`)  // Substitua pelo endpoint correto de exclusão
      .then(() => {
        setContas(contas.filter((conta) => conta.id !== id)); // Remove a conta do estado
        setOpenDeleteModal(false);  // Fecha o modal de exclusão
      })
      .catch((error) => {
        console.error("Erro ao excluir a conta:", error);
      });
  };

  // Função para abrir o modal de exclusão
  const handleOpenDeleteModal = (id: number) => {
    setAccountToDelete(id);
    setOpenDeleteModal(true);
  };

  // Função para fechar o modal de exclusão
  const handleCloseDeleteModal = () => {
    setOpenDeleteModal(false);
    setAccountToDelete(null);
  };

  return (
    <div className={style.contas}>
      <header className={style.headerContas}>
        <h1>Contas Bancárias</h1>
        <div>
          <AddButton texto="Adicionar Conta" onClick={() => setOpen(!open)} />
        </div>
      </header>
      {/* Exibindo ModalContas apenas se open for true */}
      {open && <ModalContas closeModal={() => setOpen(false)} />}
      {/* Exibindo ModalDeleteConta apenas se openDeleteModal for true */}
      {openDeleteModal && accountToDelete && (
        <ModalDeleteConta
          onClose={handleCloseDeleteModal}
          onConfirmDelete={() => handleDelete(accountToDelete)}
        />
      )}
      <main className={style.cardsContas}>
        {contas.length === 0 ? (
          <p>Não há contas disponíveis.</p>
        ) : (
          contas.map((conta) => (
            <CardContas
              key={conta.id}
              titulo={conta.banco.nome}  // Acesso ao nome do banco
              tipo={conta.tipo_conta.tipoConta.replace('_', ' ')}  // Exibe o tipo de conta com espaços
              saldo={conta.saldo}
              banco={conta.banco.iconeUrl || '/assets/iconsContas/default.svg'}  // Acesso à URL do ícone
              altBanco={`Banco ${conta.banco.nome}`}
              onDelete={() => handleOpenDeleteModal(conta.id)} // Passa o id da conta para o modal
            />
          ))
        )}
      </main>
    </div>
  );
}

export default ContasBancarias;
