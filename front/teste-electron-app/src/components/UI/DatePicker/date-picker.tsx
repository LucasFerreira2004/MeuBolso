import React from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import styles from './date-picker.module.css';

interface DatePickerProps {
  value: string;
  onChange: (date: string) => void;
  iconsrc: string;
}

const CustomDatePicker: React.FC<DatePickerProps> = ({ value,iconsrc, onChange }) => {
  const handleDateChange = (date: Date | null) => {
    if (date) {
      const formattedDate = date.toISOString().split('T')[0]; // Formato 'YYYY-MM-DD'
      onChange(formattedDate);
    } else {
      onChange(""); // Limpa a data
    }
  };

  return (
    <div className={styles.datePickerContainer}>
      <label htmlFor="datepicker" className={styles.datePickerLabel}>
        Escolha uma data:
      </label>
      <div className={styles.datePickerInputContainer}>
        <DatePicker
          selected={value ? new Date(value) : null}
          onChange={handleDateChange}
          className={styles.datePickerInput}
          dateFormat="yyyy-MM-dd"
          placeholderText="EX: 2005/04/02"
        />
        <img src={iconsrc}className={styles.iconDate}/>
      </div>
    </div>
  );
};

export default CustomDatePicker;
