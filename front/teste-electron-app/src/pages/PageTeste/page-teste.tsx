import style from "./page-teste.module.css";

function PageTeste() {
    return (
        <div className={style.containerTeste}>
            <div className={style.containerRuleMain}>
                <img 
                    src= "/assets/pageteste/iconteste.svg"
                    alt="Ícone de teste" 
                    className={style.iconTeste} 
                />
            </div>
        </div>
    );
}

export default PageTeste;
