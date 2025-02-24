// date.tsx
import React, { useState } from "react";
import styles from "./date.module.css";
import { meses } from "./consts"; // Importe a constante do novo arquivo

interface DatePickerProps {
  mes: number;
  ano: number;
  onChange: (mes: number, ano: number) => void;
}

const DatePicker: React.FC<DatePickerProps> = ({ mes, ano, onChange }) => {
  const [mesSelecionado, setMesSelecionado] = useState(mes);
  const [anoSelecionado, setAnoSelecionado] = useState(ano);

  const alterarMes = (direcao: "anterior" | "proximo") => {
    let novoMes = mesSelecionado + (direcao === "proximo" ? 1 : -1);
    let novoAno = anoSelecionado;

    if (novoMes > 12) {
      novoMes = 1;
      novoAno++;
    } else if (novoMes < 1) {
      novoMes = 12;
      novoAno--;
    }

    setMesSelecionado(novoMes);
    setAnoSelecionado(novoAno);
    onChange(novoMes, novoAno);
  };

  const handleSelectMes = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const novoMes = parseInt(e.target.value);
    setMesSelecionado(novoMes);
    onChange(novoMes, anoSelecionado);
  };

  const handleSelectAno = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const novoAno = parseInt(e.target.value);
    setAnoSelecionado(novoAno);
    onChange(mesSelecionado, novoAno);
  };

  return (
    <div className={styles.datePicker}>
      <button
        className={styles.arrow}
        onClick={() => alterarMes("anterior")}
        aria-label="Mês anterior"
      >
        <img src="assets/iconsTransacoes/arrowL.svg" alt="Seta anterior" />
      </button>

      <div className={styles.selectorContainer}>
        <select
          className={styles.selector}
          value={mesSelecionado}
          onChange={handleSelectMes}
        >
          {meses.map((mes, index) => (
            <option key={index} value={index + 1}>
              {mes}
            </option>
          ))}
        </select>

        <select
          className={styles.selector}
          value={anoSelecionado}
          onChange={handleSelectAno}
        >
          {Array.from({ length: 10 }, (_, i) => anoSelecionado - 5 + i).map(
            (ano) => (
              <option key={ano} value={ano}>
                {ano}
              </option>
            )
          )}
        </select>
      </div>

      <button
        className={styles.arrow}
        onClick={() => alterarMes("proximo")}
        aria-label="Próximo mês"
      >
        <img src="assets/iconsTransacoes/arrowR.svg" alt="Seta próximo" />
      </button>
    </div>
  );
};

export default DatePicker;