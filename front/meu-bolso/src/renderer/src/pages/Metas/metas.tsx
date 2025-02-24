import CardMetas from "../../components/UI/CardMetas/card-metas";
import AddButton from "../../components/UI/AddButton/add-button";
import style from "./metas.module.css"

function Metas() {
    return (
        <div className={style.containerMetas}>
            <div className={style.headerMetas}>
                <h1>Metas</h1>
                <div>
                    <AddButton texto={"Adicionar meta"} onClick={function (): void {
                        throw new Error("Function not implemented.");
                    }} />
                </div>
            </div>
            <div className={style.body}>
                    <CardMetas texto={"Moto"} imagem={"assets/moto.svg"}/>
            </div>
        </div>

    );
}
export default Metas;