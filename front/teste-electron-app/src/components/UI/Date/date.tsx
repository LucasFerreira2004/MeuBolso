import React, { useState, ChangeEvent } from 'react';
import style from './date.module.css';

interface DateProps {
  onDateChange?: (month: string, year: string) => void;
}

const DatePicker: React.FC<DateProps> = ({ onDateChange }) => {
  const currentDate = new Date();
  const currentYear = currentDate.getFullYear();
  const months = [
    "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", 
    "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
  ];

  const [month, setMonth] = useState<string>((currentDate.getMonth() + 1).toString().padStart(2, '0'));
  const [year, setYear] = useState<string>(currentYear.toString());

  const handleMonthChange = (e: ChangeEvent<HTMLSelectElement>) => {
    const selectedMonth = e.target.value;
    setMonth(selectedMonth);
    if (onDateChange) onDateChange(selectedMonth, year);
  };

  const handleYearChange = (e: ChangeEvent<HTMLSelectElement>) => {
    const selectedYear = e.target.value;
    setYear(selectedYear);
    if (onDateChange) onDateChange(month, selectedYear);
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
    if (onDateChange) onDateChange(newMonth.toString().padStart(2, '0'), newYear.toString());
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
    if (onDateChange) onDateChange(newMonth.toString().padStart(2, '0'), newYear.toString());
  };

  return (
    <div className={style.dates}>
      <button onClick={goToPreviousMonth} className={style.botao}>
        <img src="/assets/iconsTransacoes/arrowL.svg" alt="Arrow Left" className={style.icone} />
      </button>

      <select value={month} onChange={handleMonthChange} className={style.selecionarMes}>
        {months.map((month, index) => (
          <option key={index} value={(index + 1).toString().padStart(2, '0')}>
            {month}
          </option>
        ))}
      </select>

      <select value={year} onChange={handleYearChange} className={style.selecionarAno}>
        {Array.from({ length: 10 }, (_, i) => currentYear - 5 + i).map((yr) => (
          <option key={yr} value={yr.toString()}>
            {yr}
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
