import { useState } from "react";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-contas.module.css";
import DropDownBancos from "../UI/DropDownBancos/drop-down-bancos";
import DropDownTipoConta from "../UI/DropDownTipoContas/drop-down-tipo-conta"; // Importando o DropDownTipoConta

interface ModalContasProps {
  closeModal: () => void;
}

interface ContaProps {
  saldo: number;
  id_banco: number;
  id_tipo_conta: number;
  id_usuario: number;
}

const sendData = async ({
  saldo,
  id_banco,
  id_tipo_conta,
  id_usuario,
}: ContaProps) => {
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

function ModalContas({ closeModal }: ModalContasProps) {
  const [openBancos, setOpenBancos] = useState(false); // Inicializando como fechado
  const [openTipoConta, setOpenTipoConta] = useState(false); // Inicializando como fechado
  const [isRotatedBancos, setIsRotatedBancos] = useState(false);
  const [isRotatedTipoConta, setIsRotatedTipoConta] = useState(false);
  const [saldo, setSaldo] = useState<number | string>(""); // Valor do saldo
  const [selectedBanco, setSelectedBanco] = useState<number | null>(null); // ID do banco
  const [selectedTipoConta, setSelectedTipoConta] = useState<number | null>(null); // ID do tipo de conta
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

    // Verificar se todos os campos obrigatórios estão preenchidos
    if (!saldo || !selectedBanco || !selectedTipoConta) {
      setErrorMessage("Por favor, preencha todos os campos obrigatórios.");
      return;
    }

    const id_usuario = 1; // ID do usuário (isso pode vir de algum contexto ou estado global)

    const result = await sendData({
      saldo: parseFloat(saldo.toString()), // Convertendo para float
      id_banco: selectedBanco,
      id_tipo_conta: selectedTipoConta,
      id_usuario,
    });

    if (result.success) {
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
              placeholder={selectedBanco ? `Banco: ${selectedBanco}` : "Selecione o banco"} // Exibe o banco selecionado
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
              placeholder={selectedTipoConta ? `Tipo: ${selectedTipoConta}` : "Selecione o tipo"} // Exibe o tipo de conta selecionado
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
