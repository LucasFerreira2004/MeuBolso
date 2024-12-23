import style from './InputWithIcon.module.css';

interface InputWithIconProps {
  label: string; // Texto do rótulo do input
  iconSrc: string; // Caminho da imagem do ícone
  placeholder?: string; // (opcional) Texto para o placeholder do input
  [key: string]: unknown; // Para permitir outras propriedades para o <input>
}

const InputWithIcon: React.FC<InputWithIconProps> = ({ label, iconSrc, ...props }) => {
  return (
    <div className={style.inputsTransferencia}>
      {/* Ícone à esquerda */}
      <img
        src={iconSrc}
        alt="Ícone de descrição"
        className={style.iconLogo}
      />
      {/* Rótulo */}
      <label>{label}</label>
      {/* Input */}
      <input type="text" {...props} />
    </div>
  );
};

export default InputWithIcon;
