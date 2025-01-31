import React, { useState, ChangeEvent } from 'react';
import style from './date.module.css';

interface DateProps {
  onMonthChange?: (month: string) => void;
  onYearChange?: (year: string) => void;
}

const DatePicker: React.FC<DateProps> = ({ onMonthChange, onYearChange }) => {
  const currentYear = new Date().getFullYear();
  const months = [
    "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", 
    "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
  ];

  const [month, setMonth] = useState<string>('01');
  const [year, setYear] = useState<string>(currentYear.toString());

  const handleMonthChange = (e: ChangeEvent<HTMLSelectElement>) => {
    const selectedMonth = e.target.value;
    setMonth(selectedMonth);
    if (onMonthChange) onMonthChange(selectedMonth);
  };

  const handleYearChange = (e: ChangeEvent<HTMLSelectElement>) => {
    const selectedYear = e.target.value;
    setYear(selectedYear);
    if (onYearChange) onYearChange(selectedYear);
  };

  const goToPreviousMonth = () => {
    let newMonth = parseInt(month) - 1;
    let newYear = parseInt(year);
    if (newMonth < 1) {
      newMonth = 12;
      newYear -= 1;
    }
    setMonth(newMonth.toString().padStart(2, '0'));
    setYear(newYear.toString());
    if (onMonthChange) onMonthChange(newMonth.toString().padStart(2, '0'));
    if (onYearChange) onYearChange(newYear.toString());
  };

  const goToNextMonth = () => {
    let newMonth = parseInt(month) + 1;
    let newYear = parseInt(year);
    if (newMonth > 12) {
      newMonth = 1;
      newYear += 1;
    }
    setMonth(newMonth.toString().padStart(2, '0'));
    setYear(newYear.toString());
    if (onMonthChange) onMonthChange(newMonth.toString().padStart(2, '0'));
    if (onYearChange) onYearChange(newYear.toString());
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

      <select 
        value={year} 
        onChange={handleYearChange} 
        className={style.selecionarAno}
      >
        <option value="">Ano</option>
        {[...Array(5)].map((_, index) => {
          const nextYear = currentYear + index;
          return <option key={nextYear} value={nextYear}>{nextYear}</option>;
        })}
      </select>

      <button onClick={goToNextMonth} className={style.botao}>
        <img src="/assets/iconsTransacoes/arrowR.svg" alt="Arrow Right" className={style.icone} />
      </button>
    </div>
  );
};

export default DatePicker;
