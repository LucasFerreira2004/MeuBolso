import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-contas.module.css";

interface ModalContasProps {
  closeModal: () => void;
}

function ModalContas({ closeModal }: ModalContasProps) {
  return (
    <div className={style.overlay} onClick={closeModal}> {/* Overlay para escurecer o fundo */}
      <div className={style.containerModalContas} onClick={(e) => e.stopPropagation()}> {/* Impede o fechamento do modal ao clicar dentro */}
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
          <InputWithIcon
            label="Nome: "
            iconSrc="/assets/iconsModal/notes.svg"
            placeholder="Banco"
          />
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
