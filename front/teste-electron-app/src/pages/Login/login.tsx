import { Link } from "react-router-dom";
import styles from "./login.module.css";

function Login() {
  return (
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
          <h3>Email</h3>
          <input type="text" placeholder="user@gmail.com" className={styles.input}/>
        </div>
        <div className={styles.inputPassword}>
          <h3>Digite sua Senha</h3>
          <input type="password" placeholder="*******" className={styles.input}/>
        </div>
      </div>

      {/*Link criar conta*/}
      <p>Crie uma conta</p>

      {/*botao*/}
      <Link to="/home"><button>Entrar</button></Link>
    </div>
    
  );
}

export default Login;
