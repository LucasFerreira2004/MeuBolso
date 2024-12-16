import { useState } from "react";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-contas.module.css";
import DropDownBancos from "../UI/DropDownBancos/drop-down-bancos";
import DropDownTipoConta from "../UI/DropDownTipoContas/drop-down-tipo-conta"; // Importando o DropDownTipoConta

interface ModalContasProps {
  closeModal: () => void;
}

function ModalContas({ closeModal }: ModalContasProps) {
  const [openBancos, setOpenBancos] = useState(false); // Inicializando como fechado
  const [openTipoConta, setOpenTipoConta] = useState(false); // Inicializando como fechado
  const [isRotatedBancos, setIsRotatedBancos] = useState(false);
  const [isRotatedTipoConta, setIsRotatedTipoConta] = useState(false);

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
        <form className={style.formModalContas}>
          <input
            type="number"
            placeholder="R$ 0,00"
            className={style.inputValor}
          />
          <div className={style.divDropContas}>
            <InputWithIcon
              label="Nome: "
              iconSrc="/assets/iconsModal/notes.svg"
              placeholder="Banco"
            />
            <img
              src="/assets/iconsModal/iconsarrowleft.svg"
              alt="Abrir seleção de bancos"
              className={`${style.iconLogoArrow} ${isRotatedBancos ? style.rotated : ''}`}
              onClick={toggleDropdownBancos}
            />
            {openBancos && <DropDownBancos toggleDropdownBancos={toggleDropdownBancos} />}
          </div>
          <div className={style.divDropContas}>
            <InputWithIcon
              label="Tipo: "
              iconSrc="/assets/iconsModal/icontag.svg"
              placeholder="Tipo"
            />
            <img
              src="/assets/iconsModal/iconsarrowleft.svg"
              alt="Abrir seleção de tipo de conta"
              className={`${style.iconLogoArrow} ${isRotatedTipoConta ? style.rotated : ''}`}
              onClick={toggleDropdownTipoConta}
            />
            {openTipoConta && <DropDownTipoConta toggleDropdownTipoConta={toggleDropdownTipoConta} />}
          </div>
        </form>
        <button className={style.buttonSalvar}>Salvar</button>
      </div>
    </div>
  );
}

export default ModalContas;
