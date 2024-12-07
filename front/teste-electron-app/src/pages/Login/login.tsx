import { Link } from "react-router-dom";
import style from "./login.module.css";

function Login() {
  return (
    <div className={style.pageBackground}> {/* Aplica fundo laranja à tela de login */}
      <div className={style.mainContainer}>
        {/* Logo Section */}
        <div className={style.containerLogo}>
          <img
            src="/assets/logo-meuBolso.svg"
            alt="Ícone meuBolso"
            className={style.iconLogo}
          />
        </div>

        {/* Form Section */}
        <div className={style.containerInput}>
          <div className={style.inputEmail}>
            <h3 className={style.hC}>Email</h3>
            <input type="text" placeholder="user@gmail.com" className={style.input}/>
          </div>
          <div className={style.inputPassword}>
            <h3 className={style.hC}>Digite sua Senha</h3>
            <input type="password" placeholder="*******" className={style.input}/>
          </div>
        </div>

        {/* Link para criar conta */}
        <Link to="/cadastro"><p className={style.plogin}>Crie uma conta</p></Link>

        {/* Botão para entrar */}
        <Link to="/home"><button className={style.buttonC}>Entrar</button></Link>
      </div>
    </div>
  );
}

export default Login;
