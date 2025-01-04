import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import style from "./login.module.css";

// Definindo tipo para a resposta da API
interface LoginResponse {
  token: string;
}

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    if (email === "" || password === "") {
      setErrorMessage("Por favor, preencha todos os campos.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, senha: password }),
      });

      if (!response.ok) {
        throw new Error("Email ou senha incorretos.");
      }

      const data: LoginResponse = await response.json(); // Tipando a resposta
      const token = data.token;

      // Salvar o token no localStorage
      localStorage.setItem("authToken", token);

      // Redirecionar para a página inicial após login bem-sucedido
      navigate("/home");
    } catch (error: unknown) {
      // Usando 'unknown' para o erro e validando antes de usá-lo
      if (error instanceof Error) {
        setErrorMessage(error.message || "Erro ao realizar o login.");
      } else {
        setErrorMessage("Erro desconhecido.");
      }
    }
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

        <Link to="/cadastro">
          <p className={style.plogin}>Crie uma conta</p>
        </Link>
        <button className={style.buttonC} onClick={handleLogin}>
          Entrar
        </button>
      </div>
    </div>
  );
}

export default Login;
