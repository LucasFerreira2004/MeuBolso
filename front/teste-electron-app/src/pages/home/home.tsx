import SideBar from "../../components/SideBar/side-bar";
import styles from "./home.module.css"

function Home(){
    return(
        <div className={styles.home}>
            <div>
                <SideBar/>
            </div>
            <header className={styles.header}>
                <div>
                    <h1>Bem vindo <span className={styles.userName}>Antonio</span></h1>
                </div>
                <div className={styles.subHeader}>
                    <img
                        src="/assets/money-home.svg"
                        alt="Ícone de Perfil"
                        className={styles.iconHeader}
                        />
                        <p className={styles.pHeader}><span className={styles.sHeader}>Estimativa de Saldo: </span> R$ 1516,00</p>
                    </div>
                    <button></button>
            </header>
        </div>
        
    )
}

export default Home;