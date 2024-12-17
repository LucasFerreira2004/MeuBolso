import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import style from "./login.module.css";

interface User {
  email: string;
  password: string;
}

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const handleLogin = () => {
    if (email === "" || password === "") {
      setErrorMessage("Por favor, preencha todos os campos.");
      return;
    }

    // Verifica os dados no localStorage
    const users: User[] = JSON.parse(localStorage.getItem("users") || "[]");
    const user = users.find((user) => user.email === email && user.password === password);

    if (!user) {
      setErrorMessage("Email ou senha incorretos.");
      return;
    }

    // Redireciona para a página principal após o login bem-sucedido
    navigate("/home");
  };

  return (
    <div className={style.pageBackground}>
      <div className={style.mainContainer}>
        <div className={style.containerLogo}>
          <img
            src="/assets/logo-meuBolso.svg"
            alt="Ícone meuBolso"
            className={style.iconLogo}
          />
        </div>

        <div className={style.containerInput}>
          <div className={style.inputEmail}>
            <h3 className={style.hC}>Email</h3>
            <input
              type="text"
              placeholder="user@gmail.com"
              className={style.input}
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          <div className={style.inputPassword}>
            <h3 className={style.hC}>Digite sua Senha</h3>
            <input
              type="password"
              placeholder="*******"
              className={style.input}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
        </div>

        {errorMessage && <p className={style.errorMessage}>{errorMessage}</p>}

        <Link to="/cadastro"><p className={style.plogin}>Crie uma conta</p></Link>
        <button className={style.buttonC} onClick={handleLogin}>Entrar</button>
      </div>
    </div>
  );
}

export default Login;
