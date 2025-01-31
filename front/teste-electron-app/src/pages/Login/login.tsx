import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { saveToken } from "../../service/auth-service"; // Importe a função saveToken
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import style from "./login.module.css";

// Definindo tipo para a resposta da API
interface LoginResponse {
  token: string;
}

function Login() {
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    if (email === "" || password === "") {
      toast.error("Por favor, preencha todos os campos.");
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
      saveToken(token);
      console.log("Token salvo:", token);

      // Exibe um toast de sucesso e redireciona
      toast.success("Login realizado com sucesso!");
      setTimeout(() => {
        navigate("/home");
      }, 2000); // Redireciona após 2 segundos
    } catch (error) {
      // Usando TypeScript para validar o tipo do erro
      if (error instanceof Error) {
        toast.error(error.message || "Erro ao realizar o login.");
      } else {
        toast.error("Erro desconhecido.");
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

        <Link to="/cadastro">
          <p className={style.plogin}>Crie uma conta</p>
        </Link>
        <button className={style.buttonC} onClick={handleLogin}>
          Entrar
        </button>

        {/* Adicione o ToastContainer no final do componente */}
        <ToastContainer
          position="top-right"
          autoClose={3000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
        />
      </div>
    </div>
  );
}

export default Login;