import { useState } from "react";
import styles from "./progress-bar.module.css";

function ProgressBar() {
  const [progress] = useState(40);

  return (
    <div className={styles.wrapper}>
      <div className={styles.barra}>
        <div
          className={styles.progresso}
          style={{ width: `${progress}%` }}
        ></div>
      </div>
      <span className={styles.texto}>{progress}% completo</span>
    </div>
  );
}

export default ProgressBar;
