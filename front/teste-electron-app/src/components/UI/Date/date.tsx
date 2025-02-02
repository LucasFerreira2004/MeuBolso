import React, { useState, ChangeEvent } from 'react';
import style from './date.module.css';

interface DateProps {
  onMonthChange?: (month: string) => void;
}

const DatePicker: React.FC<DateProps> = ({ onMonthChange }) => {
  const currentYear = new Date().getFullYear();
  const months = [
    "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", 
    "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
  ];

  const [month, setMonth] = useState<string>('01'); // Mes inicial
  const [] = useState<string>(currentYear.toString()); // Ano fixo

  const handleMonthChange = (e: ChangeEvent<HTMLSelectElement>) => {
    const selectedMonth = e.target.value;
    setMonth(selectedMonth);
    if (onMonthChange) onMonthChange(selectedMonth); // Envia só o mês
  };

  const goToPreviousMonth = () => {
    let newMonth = parseInt(month) - 1;
    if (newMonth < 1) {
      newMonth = 12; // Retorna ao dezembro
    }
    setMonth(newMonth.toString().padStart(2, '0'));
    if (onMonthChange) onMonthChange(newMonth.toString().padStart(2, '0'));
  };

  const goToNextMonth = () => {
    let newMonth = parseInt(month) + 1;
    if (newMonth > 12) {
      newMonth = 1; // Volta ao janeiro
    }
    setMonth(newMonth.toString().padStart(2, '0'));
    if (onMonthChange) onMonthChange(newMonth.toString().padStart(2, '0'));
  };

  return (
    <div className={style.dates}>
      <button onClick={goToPreviousMonth} className={style.botao}>
        <img src="/assets/iconsTransacoes/arrowL.svg" alt="Arrow Left" className={style.icone} />
      </button>

      <select 
        value={month} 
        onChange={handleMonthChange} 
        className={style.selecionarMes}
      >
        <option value="">Mês</option>
        {months.map((month, index) => (
          <option key={index} value={(index + 1).toString().padStart(2, '0')}>
            {month}
          </option>
        ))}
      </select>

      <button onClick={goToNextMonth} className={style.botao}>
        <img src="/assets/iconsTransacoes/arrowR.svg" alt="Arrow Right" className={style.icone} />
      </button>
    </div>
  );
};

export default DatePicker;
