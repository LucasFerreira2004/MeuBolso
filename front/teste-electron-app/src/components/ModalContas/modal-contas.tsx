import { useState } from "react";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-contas.module.css";
import DropDownBancos from "../UI/DropDownBancos/drop-down-bancos";
import DropDownTipoConta from "../UI/DropDownTipoContas/drop-down-tipo-conta";

interface Conta {
  id: number;
  saldo: number;
  banco: {
    nome: string;
    iconeUrl: string;
  };
  tipo_conta: {
    tipoConta: string;
  };
  id_banco: number;
  id_tipo_conta: number;
  id_usuario: number;
  descricao: string;
  data: string; // Adicionando o campo data
}

interface ModalContasProps {
  closeModal: () => void;
  onAddConta: (novaConta: Conta) => void;
}

const sendData = async ({
  saldo,
  id_banco,
  id_tipo_conta,
  id_usuario,
  descricao,
  data,
}: Conta) => {
  try {
    const token = localStorage.getItem("authToken");

    if (!token) {
      return {
        success: false,
        error: {
          message: "Token não encontrado. O usuário não está autenticado.",
        },
      };
    }

    // Constrói a URL para enviar os dados via POST (não usa query params agora)
    const url = "http://localhost:8080/contas";

    const novaConta = {
      saldo,
      id_banco,
      id_tipo_conta,
      id_usuario,
      descricao: descricao.trim(),
      data,
    };

    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(novaConta),
    });

    if (!response.ok) {
      throw new Error(`Erro ${response.status}: ${response.statusText}`);
    }

    const dataResponse = await response.json();
    console.log("Resposta do servidor:", dataResponse);
    return { success: true, data: dataResponse };
  } catch (error) {
    console.error("Erro na requisição fetch:", error);
    return {
      success: false,
      error: { message: "Erro na conexão com o servidor." },
    };
  }
};

function ModalContas({ closeModal, onAddConta }: ModalContasProps) {
  const [openBancos, setOpenBancos] = useState(false);
  const [openTipoConta, setOpenTipoConta] = useState(false);
  const [isRotatedBancos, setIsRotatedBancos] = useState(false);
  const [isRotatedTipoConta, setIsRotatedTipoConta] = useState(false);
  const [saldo, setSaldo] = useState<number>(0); // Valor inicial como número
  const [selectedBanco, setSelectedBanco] = useState<number | null>(null);
  const [selectedTipoConta, setSelectedTipoConta] = useState<number | null>(
    null
  );
  const [descricao, setDescricao] = useState(""); // Campo de descrição
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const toggleDropdownBancos = () => {
    if (openTipoConta) {
      setOpenTipoConta(false);
      setIsRotatedTipoConta(false);
    }
    setOpenBancos(!openBancos);
    setIsRotatedBancos(!isRotatedBancos);
  };

  const toggleDropdownTipoConta = () => {
    if (openBancos) {
      setOpenBancos(false);
      setIsRotatedBancos(false);
    }
    setOpenTipoConta(!openTipoConta);
    setIsRotatedTipoConta(!isRotatedTipoConta);
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
  
    // Limpa a mensagem de erro
    setErrorMessage(null);
  
    const parsedSaldo = parseFloat(saldo.toString());
  
    if (isNaN(parsedSaldo) || parsedSaldo <= 0) {
      setErrorMessage("Por favor, insira um saldo válido.");
      return;
    }
  
    console.log("Saldo a ser enviado:", parsedSaldo);
  
    if (!selectedBanco || !selectedTipoConta) {
      setErrorMessage("Por favor, preencha todos os campos obrigatórios.");
      return;
    }
  
    const id_usuario = 1;
  
    const novaConta: Conta = {
      id: 0,
      saldo: parsedSaldo, // Verifique se o saldo está correto
      banco: { nome: "Banco Exemplo", iconeUrl: "/path/to/icon" },
      tipo_conta: { tipoConta: "Tipo de Conta Exemplo" },
      id_banco: selectedBanco,
      id_tipo_conta: selectedTipoConta,
      id_usuario,
      descricao,
      data: new Date().toISOString().split("T")[0],
    };
  
    const result = await sendData(novaConta);
  
    if (result.success) {
      onAddConta(result.data);
      closeModal();
    } else {
      setErrorMessage(result.error?.message || "Erro ao criar a conta.");
    }
  };  

  return (
    <div className={style.overlay} onClick={closeModal}>
      <div
        className={style.containerModalContas}
        onClick={(e) => e.stopPropagation()}
      >
        <div className={style.headerModalContas}>
          <p>Conta bancária</p>
          <img
            src="/assets/iconsModal/iconX.svg"
            alt="Fechar"
            className={style.iconClose}
            onClick={closeModal}
          />
        </div>
        <form className={style.formModalContas} onSubmit={handleSubmit}>
          <div className={style.formGroup}>
            <label htmlFor="saldo">Saldo: </label>
            <input
              id="saldo"
              type="number"
              placeholder="R$ 0,00"
              value={saldo || ""} // Verifique se é um valor válido ou use "" como fallback
              onChange={(e) => setSaldo(parseFloat(e.target.value) || 0)} // Converte para número, se não for um número válido, define como 0
            />
          </div>

          <div className={style.divDropContas}>
            <InputWithIcon
              label="Banco: "
              iconSrc="/assets/iconsModal/notes.svg"
              placeholder={
                selectedBanco ? `Banco: ${selectedBanco}` : "Selecione o banco"
              }
            />
            <img
              src="/assets/iconsModal/iconsarrowleft.svg"
              alt="Abrir seleção de bancos"
              className={`${style.iconLogoArrow} ${
                isRotatedBancos ? style.rotated : ""
              }`}
              onClick={toggleDropdownBancos}
            />
            {openBancos && (
              <DropDownBancos
                toggleDropdownBancos={toggleDropdownBancos}
                setBanco={setSelectedBanco}
              />
            )}
          </div>

          <div className={style.divDropContas}>
            <InputWithIcon
              label="Tipo: "
              iconSrc="/assets/iconsModal/icontag.svg"
              placeholder={
                selectedTipoConta
                  ? `Tipo: ${selectedTipoConta}`
                  : "Selecione o tipo"
              }
            />
            <img
              src="/assets/iconsModal/iconsarrowleft.svg"
              alt="Abrir seleção de tipo de conta"
              className={`${style.iconLogoArrow} ${
                isRotatedTipoConta ? style.rotated : ""
              }`}
              onClick={toggleDropdownTipoConta}
            />
            {openTipoConta && (
              <DropDownTipoConta
                toggleDropdownTipoConta={toggleDropdownTipoConta}
                setTipoConta={setSelectedTipoConta}
              />
            )}
          </div>

          <div className={style.formGroup}>
            <label htmlFor="descricao">Descrição: </label>
            <input
              id="descricao"
              type="text"
              placeholder="Descrição da conta"
              value={descricao}
              onChange={(e) => setDescricao(e.target.value)}
            />
          </div>

          {errorMessage && (
            <div className={style.errorMessage}>{errorMessage}</div>
          )}

          <div className={style.formActions}>
            <button type="submit" className={style.saveButton}>
              Salvar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default ModalContas;
