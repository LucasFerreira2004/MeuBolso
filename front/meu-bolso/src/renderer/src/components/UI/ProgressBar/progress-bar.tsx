import style from "./progress-bar.module.css";

interface ProgressBarProps {
  value: number;
}

export const ProgressBar: React.FC<ProgressBarProps> = ({ value }) => {
  return (
    <div className={style.wrapper}>
      <div className={style.barra}>
        <div
          className={style.progresso}
          style={{ width: `${value}%` }}
        ></div>
      </div>
      <span className={style.texto}>{value}% completo</span>
    </div>
  );
};
