import { useState } from "react";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-contas.module.css";
import DropDownBancos from "../UI/DropDownBancos/drop-down-bancos";
import DropDownTipoConta from "../UI/DropDownTipoContas/drop-down-tipo-conta"; 

// Definindo a interface para a Conta com todos os campos necessários
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
}

interface ModalContasProps {
  closeModal: () => void;
  onAddConta: (novaConta: Conta) => void; // Função que será chamada no componente pai para adicionar a conta
}

const sendData = async ({
  saldo,
  id_banco,
  id_tipo_conta,
  id_usuario,
}: Conta) => {
  try {
    const response = await fetch("http://localhost:8080/contas", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        saldo,
        id_banco,
        id_tipo_conta,
        id_usuario,
      }),
    });

    if (response.ok) {
      return { success: true, data: await response.json() };
    } else {
      const errorData = await response.json();
      return { success: false, error: errorData };
    }
  } catch (error) {
    console.error("Erro na requisição", error);
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
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  // Função para alternar a visibilidade do dropdown de bancos
  const toggleDropdownBancos = () => {
    if (openTipoConta) {
      setOpenTipoConta(false);
      setIsRotatedTipoConta(false);
    }
    setOpenBancos(!openBancos);
    setIsRotatedBancos(!isRotatedBancos);
  };

  // Função para alternar a visibilidade do dropdown de tipo de conta
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

    if (!saldo || !selectedBanco || !selectedTipoConta) {
      setErrorMessage("Por favor, preencha todos os campos obrigatórios.");
      return;
    }

    const id_usuario = 1; // ID do usuário (isso pode vir de algum contexto ou estado global)

    // Criar uma nova conta com todos os campos necessários
    const novaConta: Conta = {
      id: 0, // A API provavelmente vai gerar esse ID, ou você pode deixar como 0
      saldo: parseFloat(saldo.toString()), // Converte o saldo para um número
      banco: { nome: "Banco Exemplo", iconeUrl: "/path/to/icon" }, // Substitua com dados reais
      tipo_conta: { tipoConta: "Tipo de Conta Exemplo" }, // Substitua com dados reais
      id_banco: selectedBanco,
      id_tipo_conta: selectedTipoConta,
      id_usuario, // Defina o ID do usuário
    };

    const result = await sendData(novaConta);

    if (result.success) {
      onAddConta(result.data); // Adiciona a nova conta ao componente pai
      closeModal(); // Fecha o modal
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
