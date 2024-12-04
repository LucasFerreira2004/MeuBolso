import { Link } from "react-router-dom";
import styles from "./login.module.css";

function Login() {
  return (
    <div className={styles.pageBackground}> {/* Aplica fundo laranja à tela de login */}
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
        </div>

        {/* Link para criar conta */}
        <Link to="/cadastro"><p className={styles.plogin}>Crie uma conta</p></Link>

        {/* Botão para entrar */}
        <Link to="/home"><button className={styles.buttonC}>Entrar</button></Link>
      </div>
    </div>
  );
}

export default Login;
