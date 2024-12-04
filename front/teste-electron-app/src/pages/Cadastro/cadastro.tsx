import { Link } from "react-router-dom";
import styles from "./cadastro.module.css";

function Cadastro() {
  return (
    <div className={styles.pageBackground}> {/* Aplica o fundo específico à tela de cadastro */}
      <div className={styles.mainContainer}>
        {/* Logo Section */}
        <div className={styles.containerLogo}>
          <img
            src="/assets/logo-meuBolso.svg"
            alt="Ícone meuBolso"
            className={styles.iconLogo}
          />
        </div>

        {/* Form Section */}
        <div className={styles.containerInput}>
          <div className={styles.inputEmail}>
            <h3 className={styles.hC}>Email</h3>
            <input type="text" placeholder="user@gmail.com" className={styles.input}/>
          </div>
          <div className={styles.inputPassword}>
            <h3 className={styles.hC}>Digite sua Senha</h3>
            <input type="password" placeholder="*******" className={styles.input}/>
          </div>
          <div className={styles.inputRepeatPassword}>
            <h3 className={styles.hC}>Repita sua Senha</h3>
            <input type="password" placeholder="*******" className={styles.input}/>
          </div>
        </div>

        {/* Botão */}
        <Link to="/"><button className={styles.buttonC}>Cadastrar</button></Link>
      </div>
    </div>
  );
}

export default Cadastro;
