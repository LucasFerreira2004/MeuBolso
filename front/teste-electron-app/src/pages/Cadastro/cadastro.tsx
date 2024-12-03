import styles from "./cadastro.module.css";

function Cadastro() {
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
        <div className={styles.inputRepeatPassword}>
          <h3>Repita sua Senha</h3>
          <input type="password" placeholder="*******" className={styles.input}/>
        </div>
      </div>

      {/*botao*/}
      <button>Entrar</button>
    </div>
    
  );
}

export default Cadastro;
