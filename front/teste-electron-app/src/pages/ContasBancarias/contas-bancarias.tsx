import CardContas from "../../components/CardContas/card-contas";
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./contas-bancarias.module.css";

function ContasBancarias() {

    return (
        <div className={style.contas}>
            <header className={style.headerContas}>
                <h1>Contas Bancárias</h1>
                <div>
                    <AddButton texto="Adicionar Conta"/>
                </div>
            </header>
            <main className={style.cardsContas}>
                <CardContas 
                    titulo="Nubank"
                    tipo="Corrente"
                    saldo={1440.00}
                    estado="Ativo"
                    iconEstado="/assets/iconsContas/estado.svg"
                    banco="/assets/iconsContas/nubank.svg"
                    altBanco="Banco Nubank"
                />
            </main>
        </div>
    );
}

export default ContasBancarias;
