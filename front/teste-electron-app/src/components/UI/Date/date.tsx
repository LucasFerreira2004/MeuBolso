import React from "react";
import styles from "./date.module.css";

interface DatePickerProps {
  mes: number;
  ano: number;
  onChange: (mes: number, ano: number) => void;
}

const DatePicker: React.FC<DatePickerProps> = ({ mes, ano, onChange }) => {
  const alterarMes = (direcao: "anterior" | "proximo") => {
    let novoMes = mes + (direcao === "proximo" ? 1 : -1);
    let novoAno = ano;

    if (novoMes > 12) {
      novoMes = 1;
      novoAno++;
    } else if (novoMes < 1) {
      novoMes = 12;
      novoAno--;
    }

    onChange(novoMes, novoAno);
  };

  return (
    <div className={styles.datePicker}>
      <button
        className={styles.arrow}
        onClick={() => alterarMes("anterior")}
        aria-label="Mês anterior"
      >
        &lt;
      </button>
      <span className={styles.date}>
        {mes.toString().padStart(2, "0")}/{ano}
      </span>
      <button
        className={styles.arrow}
        onClick={() => alterarMes("proximo")}
        aria-label="Próximo mês"
      >
        &gt;
      </button>
    </div>
  );
};

export default DatePicker;
