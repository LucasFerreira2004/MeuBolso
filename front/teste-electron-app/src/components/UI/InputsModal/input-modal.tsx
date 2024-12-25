import style from './InputWithIcon.module.css';

interface InputWithIconProps {
  label: string; 
  iconSrc: string; 
  placeholder?: string; 
  [key: string]: unknown; 
}

const InputWithIcon: React.FC<InputWithIconProps> = ({ label, iconSrc, ...props }) => {
  return (
    <div className={style.inputsTransferencia}>
      <img
        src={iconSrc}
        alt="Ícone de descrição"
        className={style.iconLogo}
      />
      <label>{label}</label>
      <input type="text" {...props} />
    </div>
  );
};

export default InputWithIcon;
