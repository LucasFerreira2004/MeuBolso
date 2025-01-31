import { useState, useEffect } from "react";
import CardContas from "../../components/UI/CardContas/card-contas";
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./contas-bancarias.module.css";
import ModalContas from "../../components/ModalContas/modal-contas"; // Importe o ModalContas

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
  const [openAddModal, setOpenAddModal] = useState(false); // Estado para controlar o modal de adicionar contas

  const getDataAtual = () => {
    const data = new Date();
    const ano = data.getFullYear();
    const mes = String(data.getMonth() + 1).padStart(2, "0");
    const dia = String(data.getDate()).padStart(2, "0");
    return `${ano}-${mes}-${dia}`;
  };

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
        console.log("Resposta do servidor:", data);
        setContas(data);
      })
      .catch((error) => {
        console.error("Erro ao buscar as contas:", error);
      });
  };

  useEffect(() => {
    fetchContas();
  }, []);

  return (
    <div className={style.contas}>
      <header className={style.headerContas}>
        <h1>Contas Bancárias</h1>
        <div>
          <AddButton
            texto="Adicionar Conta"
            onClick={() => setOpenAddModal(true)} // Abre o modal de adicionar conta
          />
        </div>
      </header>

      {/* Modal de Adicionar Contas */}
      {openAddModal && (
        <ModalContas
          onCloseAll={() => {
            setOpenAddModal(false); // Fecha o modal após adicionar
            fetchContas(); // Atualiza a lista de contas
          }}
        />
      )}

      {/* Lista de Contas */}
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
              altBanco={`Banco ${conta.banco.nome}`} onDelete={function (): void {
                throw new Error("Function not implemented.");
              } } onEdit={function (): void {
                throw new Error("Function not implemented.");
              } }            />
          ))
        )}
      </main>
    </div>
  );
}

export default ContasBancarias;
