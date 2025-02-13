import style from './InputWithIcon.module.css';

interface InputWithIconProps {
  label: string;
  iconSrc?: string ;
  placeholder?: string;
  [key: string]: unknown;
}

const InputWithIcon: React.FC<InputWithIconProps> = ({ label, iconSrc, ...props }) => {
  return (
    <div className={style.inputsTransferencia}>
      <label>{label}</label>
      <div className={style.inputContainer}>
        <input type="text" {...props} />
        <img src={iconSrc} alt="Ícone de descrição" className={style.iconLogo} />
      </div>
    </div>
  );
};

export default InputWithIcon;
