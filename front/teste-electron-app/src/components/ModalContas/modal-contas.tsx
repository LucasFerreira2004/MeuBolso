import { useState } from "react";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-contas.module.css";
import DropDownBancos from "../UI/DropDownBancos/drop-down-bancos";

interface ModalContasProps {
  closeModal: () => void;
}

function ModalContas({ closeModal }: ModalContasProps) {
  const [openBancos, setOpenBancos] = useState(false); // Estado para controlar o dropdown
  const [isRotated, setIsRotated] = useState(false); // Estado para controlar a rotação do ícone

  const toggleDropdown = () => {
    setOpenBancos(!openBancos);  // Alterna o estado do dropdown
    setIsRotated(!isRotated);  // Alterna a rotação
  };

  return (
    <div className={style.overlay} onClick={closeModal}>
      <div
        className={style.containerModalContas}
        onClick={(e) => e.stopPropagation()} // Impede o fechamento do modal ao clicar dentro
      >
        <div className={style.headerModalContas}>
          <p>Conta bancária</p>
          <img
            src="/assets/iconsModal/iconX.svg"
            alt="Fechar"
            className={style.iconClose}
            onClick={closeModal} // Fecha o modal ao clicar no ícone
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
            {/* Ícone à direita */}
            <img
              src="/assets/iconsModal/iconsarrowleft.svg"
              alt="Abrir seleção de bancos"
              className={`${style.iconLogoArrow} ${isRotated ? style.rotated : ''}`}
              onClick={toggleDropdown} // Alterna o dropdown e a rotação
            />
            {/* Renderiza o dropdown condicionalmente */}
            {openBancos && <DropDownBancos />}
          </div>
          <InputWithIcon
            label="Categoria: "
            iconSrc="/assets/iconsModal/icontag.svg"
            placeholder="Categoria"
          />
        </form>
        <button className={style.buttonSalvar}>Salvar</button>
      </div>
    </div>
  );
}

export default ModalContas;
