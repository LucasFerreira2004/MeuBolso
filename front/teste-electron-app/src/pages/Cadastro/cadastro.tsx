import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import style from "./cadastro.module.css";

interface User {
  email: string;
  password: string;
}

function Cadastro() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [repeatPassword, setRepeatPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const handleCadastro = () => {
    if (email === "" || password === "" || repeatPassword === "") {
      setErrorMessage("Por favor, preencha todos os campos.");
      return;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
      setErrorMessage("Por favor, insira um email válido.");
      return;
    }

    if (password !== repeatPassword) {
      setErrorMessage("As senhas não correspondem.");
      return;
    }

    if (password.length < 6) {
      setErrorMessage("A senha deve ter pelo menos 6 caracteres.");
      return;
    }

    // Salva os dados no localStorage
    const users: User[] = JSON.parse(localStorage.getItem("users") || "[]");
    users.push({ email, password });
    localStorage.setItem("users", JSON.stringify(users));

    navigate("/");
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
              type="email"
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
          <div className={style.inputRepeatPassword}>
            <h3 className={style.hC}>Repita sua Senha</h3>
            <input
              type="password"
              placeholder="*******"
              className={style.input}
              value={repeatPassword}
              onChange={(e) => setRepeatPassword(e.target.value)}
            />
          </div>
        </div>

        {errorMessage && <p className={style.errorMessage}>{errorMessage}</p>}
        {/* Link para voltar à tela de login */}
        <Link to="/" className={style.backToLogin}>Voltar para o login</Link>
        
        <button className={style.buttonC} onClick={handleCadastro}>Cadastrar</button>

      </div>
    </div>
  );
}

export default Cadastro;
