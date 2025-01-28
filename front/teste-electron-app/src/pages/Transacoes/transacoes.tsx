import { useState } from "react";
import AddButton from "../../components/UI/AddButton/add-button";
import Date from "../../components/UI/Date/date";
import ModalAddTransacao from "../../components/ModalAddTransacao/modal-add-transacao";
import style from "./transacoes.module.css";
import CardTransacoes from "../../components/UI/CardTransacoes/card-transacoes";

function Transacoes() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [] = useState([
    // Exemplo de transações, você pode substituir pelo seu array de transações reais
  ]);

  const handleMonthChange = (month: string) => {
    console.log("Mês selecionado:", month);
  };

  const handleYearChange = (year: string) => {
    console.log("Ano selecionado:", year);
  };

  const toggleModal = () => {
    setIsModalOpen((prev) => !prev);
  };

  return (
    <div className={style.containerTransacoes}>
      <h1>Transações</h1>
      <div className={style.headerTransacoes}>
        <h3>Estimativa do mês</h3>
        <div className={style.rowTransacoes}>
          <div>
            <img
              src="/assets/iconsTransacoes/money.svg"
              alt="iconMoney"
              className={style.iconT}
            />
            <p>
              <span className={style.spanM}>Estimativa de Saldo: </span>R$
              1516.00
            </p>
          </div>
          <div>
            <img
              src="/assets/iconsTransacoes/arrowred.svg"
              alt="iconMoney"
              className={style.iconT}
            />
            <p>
              <span className={style.spanR}>Despesas: </span>R$ 3896.00
            </p>
          </div>
          <div>
            <img
              src="/assets/iconsTransacoes/arrowgreen.svg"
              alt="iconMoney"
              className={style.iconT}
            />
            <p>
              <span className={style.spanT}>Receitas: </span>R$ 15316.00
            </p>
          </div>
        </div>
      </div>
      <div className={style.bodyTransacoes}>
        <div className={style.headerBodyT}>
          <Date
            onMonthChange={handleMonthChange}
            onYearChange={handleYearChange}
          />
          <div className={style.search}>
            <input
              className={style.input}
              type="text"
              placeholder="Buscar..."
            />
            <img
              className={style.icon}
              src="/assets/iconsTransacoes/lupa.svg"
              alt="Lupa"
            />
            <img
              className={style.icon}
              src="/assets/iconsTransacoes/filter.svg"
              alt="Filter"
            />
          </div>
          <div>
            <AddButton texto="Realizar Transação" onClick={toggleModal} />
          </div>
        </div>

        {/* Exibindo as transações */}
        <div className={style.transacoesList}>
          <CardTransacoes
            dia="28"
            mes={1}
            ano={2025}
            valor={1500.75}
            descricao="Pagamento de serviço"
            categoria="Serviços"
            conta="Conta Corrente"
            fixa="Não"
          />
        </div>
      </div>

      {/* Renderização condicional do modal */}
      {isModalOpen && (
        <div className={style.modalOverlay}>
          <div className={style.modalContainer}>
            <ModalAddTransacao onClose={toggleModal} />
          </div>
        </div>
      )}
    </div>
  );
}

export default Transacoes;
