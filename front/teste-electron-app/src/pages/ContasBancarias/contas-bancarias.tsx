import { useEffect, useState } from "react";
import axios from "axios";
import CardContas from "../../components/CardContas/card-contas";
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./contas-bancarias.module.css";
import ModalContas from "../../components/ModalContas/modal-contas";

interface Conta {
  id: number;
  saldo: number;
  nome_banco: string;
  tipo_conta: {
    tipoConta: string;
  };
}

function ContasBancarias() {
  const [contas, setContas] = useState<Conta[]>([]);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    axios
      .get<Conta[]>("http://localhost:8080/contas")  // Substitua pelo endpoint correto
      .then((response) => {
        console.log(response.data); // Verifica os dados no console
        setContas(response.data); // Atualiza o estado com os dados da API
      })
      .catch((error) => {
        console.error("Erro ao buscar as contas:", error);
      });
  }, []);

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

      <main className={style.cardsContas}>
        {contas.length === 0 ? (
          <p>Não há contas disponíveis.</p>
        ) : (
          contas.map((conta) => (
            <CardContas
              key={conta.id}
              titulo={conta.nome_banco}
              tipo={conta.tipo_conta.tipoConta.replace('_', ' ')}  // Exibe o tipo de conta com espaços
              saldo={conta.saldo}
              banco={`/assets/iconsContas/${conta.nome_banco.toLowerCase().replace(' ', '-')}.svg`}  // Caminho para a imagem do banco
              altBanco={`Banco ${conta.nome_banco}`}
            />
          ))
        )}
      </main>
    </div>
  );
}

export default ContasBancarias;
