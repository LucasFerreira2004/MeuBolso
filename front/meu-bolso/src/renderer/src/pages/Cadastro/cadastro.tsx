import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import style from "./cadastro.module.css";
import { baseUrl } from "../../api/api";

function Cadastro() {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [repeatPassword, setRepeatPassword] = useState("");
  const navigate = useNavigate();
  const handleCadastro = async () => {
    if (
      nome === "" ||
      email === "" ||
      password === "" ||
      repeatPassword === ""
    ) {
      toast.error("Por favor, preencha todos os campos.");
      return;
    }
  
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
      toast.error("Por favor, insira um email válido.");
      return;
    }
  
    if (password !== repeatPassword) {
      toast.error("As senhas não correspondem.");
      return;
    }
  
    if (password.length < 6) {
      toast.error("A senha deve ter pelo menos 6 caracteres.");
      return;
    }
  
    try {
      const response = await fetch(`${baseUrl}/auth/cadastro`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          nome,
          email,
          senha: password,
        }),
      });
  
      if (!response.ok) {
        const data = await response.json();
        toast.error(data?.mensagem || "Erro ao cadastrar o usuário.");
        return;
      }
  
      // Redireciona para o Login com a mensagem de sucesso
      navigate("/", {
        state: { successMessage: "Usuário cadastrado com sucesso!" },
      });
    } catch (error) {
      if (error instanceof Error) {
        toast.error(error.message || "Erro ao realizar o cadastro.");
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
            src="assets/logo-meuBolso.svg"
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
              onChange={(e) => setNome(e.target.value)}
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

        <div className={style.resgistrar}>
          <p className={style.texto}>Ja tem uma conta?</p>
          <Link to="/">
            <p className={style.link}>Entrar</p>
          </Link>
        </div>

        <button className={style.buttonC} onClick={handleCadastro}>
          Cadastrar
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

export default Cadastro;
