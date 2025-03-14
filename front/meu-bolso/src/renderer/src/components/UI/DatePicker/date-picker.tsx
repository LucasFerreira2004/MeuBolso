import React from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import styles from './date-picker.module.css';

interface DatePickerProps {
  value: string;
  label: string;
  onChange: (date: string) => void;
  iconsrc: string;
}

const CustomDatePicker: React.FC<DatePickerProps> = ({ value,label, iconsrc, onChange }) => {
  const handleDateChange = (date: Date | null) => {
    if (date) {
      const formattedDate = date.toISOString().split('T')[0];
      onChange(formattedDate);
    } else {
      onChange("");
    }
  };

  const maxDate = new Date();

  return (
    <div className={styles.datePickerContainer}>
      <label htmlFor="datepicker" className={styles.datePickerLabel}>
        {label}
      </label>
      <div className={styles.datePickerInputContainer}>
        <DatePicker
          selected={value ? new Date(value) : null}
          onChange={handleDateChange}
          className={styles.datePickerInput}
          dateFormat="yyyy-MM-dd"
          placeholderText="EX: 2005/04/02"
          maxDate={maxDate}
        />
        <img src={iconsrc} className={styles.iconDate} alt="Ícone de calendário" />
      </div>
    </div>
  );
};

export default CustomDatePicker;