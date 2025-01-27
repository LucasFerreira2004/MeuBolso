import { useState } from "react";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-edit-contas.module.css";
import DropDownBancos from "../UI/DropDownBancos/drop-down-bancos";
import DropDownTipoConta from "../UI/DropDownTipoContas/drop-down-tipo-conta";

interface ModalEditContasProps {
  closeModal: () => void;
  conta: Conta;
  refreshContas: () => void;
}

interface Conta {
  id: number;
  saldo: number;
  id_banco: number;
  id_tipo_conta: number;
  data?: string;
  descricao?: string;
}

const sendData = async ({
  id,
  saldo,
  id_banco,
  id_tipo_conta,
  data,
  descricao,
}: Conta) => {
  try {
    const token = localStorage.getItem("authToken");

    if (!token) {
      return {
        success: false,
        error: { message: "Token não encontrado. O usuário não está autenticado." },
      };
    }

    const response = await fetch(`http://localhost:8080/contas/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        saldo,
        id_banco,
        id_tipo_conta,
        data,
        descricao,
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

function ModalEditContas({ closeModal, conta, refreshContas }: ModalEditContasProps) {
  const [openBancos, setOpenBancos] = useState(false);
  const [openTipoConta, setOpenTipoConta] = useState(false);
  const [isRotatedBancos, setIsRotatedBancos] = useState(false);
  const [isRotatedTipoConta, setIsRotatedTipoConta] = useState(false);
  const [saldo, setSaldo] = useState<number | string>(conta.saldo);
  const [selectedBanco, setSelectedBanco] = useState<number | null>(conta.id_banco);
  const [selectedTipoConta, setSelectedTipoConta] = useState<number | null>(conta.id_tipo_conta);
  const [data, setData] = useState<string>(conta.data || "");
  const [descricao, setDescricao] = useState<string>(conta.descricao || "");
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const toggleDropdown = (
    dropdownSetter: React.Dispatch<React.SetStateAction<boolean>>,
    rotationSetter: React.Dispatch<React.SetStateAction<boolean>>,
    closeOtherDropdown?: () => void
  ) => {
    if (closeOtherDropdown) closeOtherDropdown();
    dropdownSetter((prev) => !prev);
    rotationSetter((prev) => !prev);
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    if (!saldo || !selectedBanco || !selectedTipoConta || !data || !descricao) {
      setErrorMessage("Por favor, preencha todos os campos obrigatórios.");
      return;
    }

    const result = await sendData({
      id: conta.id,
      saldo: parseFloat(saldo.toString()),
      id_banco: selectedBanco!,
      id_tipo_conta: selectedTipoConta!,
      data,
      descricao,
    });

    if (result.success) {
      closeModal();
      refreshContas();
    } else {
      setErrorMessage(result.error?.message || "Erro ao atualizar a conta.");
    }
  };

  return (
    <div className={style.overlay} onClick={closeModal}>
      <div className={style.containerModalEditContas} onClick={(e) => e.stopPropagation()}>
        <div className={style.headerModalEditContas}>
          <p>Editar Conta Bancária</p>
          <img
            src="/assets/iconsModal/iconX.svg"
            alt="Fechar"
            className={style.iconClose}
            onClick={closeModal}
          />
        </div>
        <form className={style.formModalEditContas} onSubmit={handleSubmit}>
          <div className={style.formGroup}>
            <label htmlFor="saldo">Saldo:</label>
            <input
              id="saldo"
              type="number"
              placeholder="R$ 0,00"
              value={saldo}
              onChange={(e) => setSaldo(e.target.value)}
            />
          </div>

          <div className={style.formGroup}>
            <label htmlFor="data">Data:</label>
            <input
              id="data"
              type="date"
              value={data}
              onChange={(e) => setData(e.target.value)}
            />
          </div>

          <div className={style.formGroup}>
            <label htmlFor="descricao">Descrição:</label>
            <textarea
              id="descricao"
              placeholder="Digite uma descrição"
              value={descricao}
              onChange={(e) => setDescricao(e.target.value)}
            />
          </div>

          <div className={style.divDropContas}>
            <InputWithIcon
              label="Banco:"
              iconSrc="/assets/iconsModal/notes.svg"
              placeholder={selectedBanco ? `Banco: ${selectedBanco}` : "Selecione o banco"}
            />
            <img
              src="/assets/iconsModal/iconsarrowleft.svg"
              alt="Abrir seleção de bancos"
              className={`${style.iconLogoArrow} ${isRotatedBancos ? style.rotated : ""}`}
              onClick={() =>
                toggleDropdown(setOpenBancos, setIsRotatedBancos, () => setOpenTipoConta(false))
              }
            />
            {openBancos && (
              <DropDownBancos toggleDropdownBancos={() => setOpenBancos(false)} setBanco={setSelectedBanco} />
            )}
          </div>

          <div className={style.divDropContas}>
            <InputWithIcon
              label="Tipo:"
              iconSrc="/assets/iconsModal/icontag.svg"
              placeholder={selectedTipoConta ? `Tipo: ${selectedTipoConta}` : "Selecione o tipo"}
            />
            <img
              src="/assets/iconsModal/iconsarrowleft.svg"
              alt="Abrir seleção de tipo de conta"
              className={`${style.iconLogoArrow} ${isRotatedTipoConta ? style.rotated : ""}`}
              onClick={() =>
                toggleDropdown(setOpenTipoConta, setIsRotatedTipoConta, () => setOpenBancos(false))
              }
            />
            {openTipoConta && (
              <DropDownTipoConta
                toggleDropdownTipoConta={() => setOpenTipoConta(false)}
                setTipoConta={setSelectedTipoConta}
              />
            )}
          </div>

          {errorMessage && <div className={style.errorMessage}>{errorMessage}</div>}

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

export default ModalEditContas;
