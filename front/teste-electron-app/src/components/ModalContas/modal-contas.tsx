import { useState } from "react";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-contas.module.css";
import DropDownBancos from "../UI/DropDownBancos/drop-down-bancos";
import DropDownTipoConta from "../UI/DropDownTipoContas/drop-down-tipo-conta"; 
import axios from "axios";

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
}: Conta) => {
  try {
    const token = localStorage.getItem("authToken");

    if (!token) {
      return { success: false, error: { message: "Token não encontrado. O usuário não está autenticado." } };
    }

    // Pega a data atual no formato ISO (ano-mês-dia) sem a parte da hora
    const dataAtual = new Date().toISOString().split('T')[0]; // Isso pega apenas "YYYY-MM-DD"

    const response = await axios.post("http://localhost:8080/contas", {
      saldo,
      id_banco,
      id_tipo_conta,
      id_usuario,
      descricao,
      data: dataAtual, // Envia a data atual no formato desejado
    }, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });

    console.log("Resposta do servidor:", response.data); // Log da resposta
    return { success: true, data: response.data };
  } catch (error) {
    if (axios.isAxiosError(error)) {
      console.error("Erro na requisição Axios:", error.response?.data);  // Log do erro detalhado
    } else {
      console.error("Erro desconhecido:", error);
    }
    return { success: false, error: { message: "Erro na conexão com o servidor." } };
  }
};

function ModalContas({ closeModal, onAddConta }: ModalContasProps) {
  const [openBancos, setOpenBancos] = useState(false);
  const [openTipoConta, setOpenTipoConta] = useState(false);
  const [isRotatedBancos, setIsRotatedBancos] = useState(false);
  const [isRotatedTipoConta, setIsRotatedTipoConta] = useState(false);
  const [saldo, setSaldo] = useState<number | string>(""); 
  const [selectedBanco, setSelectedBanco] = useState<number | null>(null);
  const [selectedTipoConta, setSelectedTipoConta] = useState<number | null>(null);
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
  
    // Verifica se o saldo é um número válido e se os outros campos obrigatórios estão preenchidos
    if (isNaN(Number(saldo)) || !selectedBanco || !selectedTipoConta) {
      setErrorMessage("Por favor, preencha todos os campos obrigatórios.");
      return;
    }
  
    const id_usuario = 1;
  
    const novaConta: Conta = {
      id: 0, 
      saldo: parseFloat(saldo.toString()),  // Converte para número
      banco: { nome: "Banco Exemplo", iconeUrl: "/path/to/icon" }, 
      tipo_conta: { tipoConta: "Tipo de Conta Exemplo" },
      id_banco: selectedBanco,
      id_tipo_conta: selectedTipoConta,
      id_usuario,
      descricao, // Incluindo o campo de descrição (pode ser vazio)
      data: new Date().toISOString().split('T')[0], // Adicionando a data atual
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
      <div className={style.containerModalContas} onClick={(e) => e.stopPropagation()}>
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
              value={saldo}
              onChange={(e) => setSaldo(e.target.value)}
            />
          </div>

          <div className={style.divDropContas}>
            <InputWithIcon
              label="Banco: "
              iconSrc="/assets/iconsModal/notes.svg"
              placeholder={selectedBanco ? `Banco: ${selectedBanco}` : "Selecione o banco"}
            />
            <img
              src="/assets/iconsModal/iconsarrowleft.svg"
              alt="Abrir seleção de bancos"
              className={`${style.iconLogoArrow} ${isRotatedBancos ? style.rotated : ''}`}
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
              placeholder={selectedTipoConta ? `Tipo: ${selectedTipoConta}` : "Selecione o tipo"}
            />
            <img
              src="/assets/iconsModal/iconsarrowleft.svg"
              alt="Abrir seleção de tipo de conta"
              className={`${style.iconLogoArrow} ${isRotatedTipoConta ? style.rotated : ''}`}
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
            <div className={style.errorMessage}>
              {errorMessage}
            </div>
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
