import { Link } from "react-router-dom";
import style from "./cadastro.module.css";

function Cadastro() {
  return (
    <div className={style.pageBackground}> {/* Aplica o fundo específico à tela de cadastro */}
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
          <div className={style.inputRepeatPassword}>
            <h3 className={style.hC}>Repita sua Senha</h3>
            <input type="password" placeholder="*******" className={style.input}/>
          </div>
        </div>

        {/* Botão */}
        <Link to="/"><button className={style.buttonC}>Cadastrar</button></Link>
      </div>
    </div>
  );
}

export default Cadastro;
