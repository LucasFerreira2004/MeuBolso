import { useState } from "react";
import style from "./progress-bar.module.css";

function ProgressBar() {
  const [progress] = useState(40);

  return (
    <div className={style.wrapper}>
      <div className={style.barra}>
        <div
          className={style.progresso}
          style={{ width: `${progress}%` }}
        ></div>
      </div>
      <span className={style.texto}>{progress}% completo</span>
    </div>
  );
}

export default ProgressBar;
