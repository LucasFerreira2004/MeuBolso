import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import style from "./cadastro.module.css";

function Cadastro() {
  const [nome, setNome] = useState(""); // Campo nome adicionado
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [repeatPassword, setRepeatPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const handleCadastro = async () => {
    if (nome === "" || email === "" || password === "" || repeatPassword === "") {
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

    try {
      const response = await fetch("http://localhost:8080/auth/cadastro", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ nome, email, senha: password }), // Adicionando "nome" no corpo da requisição
      });

      if (!response.ok) {
        throw new Error("Erro ao cadastrar o usuário.");
      }

      // Não há corpo na resposta do cadastro, então apenas redireciona
      alert("Usuário cadastrado com sucesso!");
      navigate("/");
    } catch (error: unknown) {
      if (error instanceof Error) {
        setErrorMessage(error.message || "Erro ao realizar o cadastro.");
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
          <div className={style.inputNome}>
            <h3 className={style.hC}>Nome</h3>
            <input
              type="text"
              placeholder="Seu nome"
              className={style.input}
              value={nome}
              onChange={(e) => setNome(e.target.value)} // Atualiza o campo nome
            />
          </div>
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

        <Link to="/" className={style.backToLogin}>
          Voltar para o login
        </Link>

        <button className={style.buttonC} onClick={handleCadastro}>
          Cadastrar
        </button>
      </div>
    </div>
  );
}

export default Cadastro;
