import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-transaferencia.module.css";

function Modal() {
  return (
    <div className={style.containerModal}>
      <div className={style.headerModal}>
        <h1>Nova Transferência</h1>
        <img
          src="/assets/iconsModal/iconX.svg"
          alt="Ícone fechar"
          className={style.iconLogo}
        />
      </div>
      <div className={style.containerMain}>
        <form>
        <div>
          <input type="number" />
        </div>
          <InputWithIcon
            label="Descrição (opcional): "
            iconSrc="/assets/iconsModal/icondescription.svg"
            placeholder="Icon description"
          />
          <InputWithIcon
            label="Conta: "
            iconSrc="/assets/iconsModal/iconbankmodal.svg"
            placeholder="Icon Bank"
          />
          <div>
          <img
              src="/assets/iconsModal/iconarrowbottom.svg"
              alt="Ícon arrow"
              className={style.iconArrow}
            />
          </div>
          <InputWithIcon
            label="Conta: "
            iconSrc="/assets/iconsModal/iconbankmodal.svg"
            placeholder="Icon Bank"
          />
          <InputWithIcon
            label="Data: "
            iconSrc="/assets/iconsModal/iconcalendario.svg"
            placeholder="Icon calendario"
          />
        </form>
      </div>
    </div>
  );
}

export default Modal;
