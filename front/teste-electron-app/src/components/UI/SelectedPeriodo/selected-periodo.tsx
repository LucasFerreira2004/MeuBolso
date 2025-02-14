import Select from "react-select";
import style from "./selected-periodo.module.css";

interface SelectedPeriodoProps {
  selectedValue: "SEMANAL" | "MENSAL" | "DIARIO"; // Corrigido o tipo para corresponder à expectativa
  onChange: (e: React.ChangeEvent<HTMLSelectElement>) => void;
}

function SelectedPeriodo({ selectedValue, onChange }: SelectedPeriodoProps) {
  const options = [
    { value: "DIARIO", label: "Diária" },
    { value: "SEMANAL", label: "Semanal" },
    { value: "MENSAL", label: "Mensal" }
  ];

  const handleChange = (selectedOption: any) => {
    const periodoSelecionado = selectedOption ? selectedOption.value : null;
    onChange({ target: { value: periodoSelecionado } } as React.ChangeEvent<HTMLSelectElement>);
  };

  return (
    <div className={style.selectBoxContainer}>
      <div className={style.selectBoxWrapper}>
        <label className={style.selectBoxLabel}>Periodicidade:</label>
        <Select
          options={options}
          value={options.find(option => option.value === selectedValue)}
          onChange={handleChange}
          placeholder="Selecione o período"
          className={style.selectBox}
          classNamePrefix="react-select"
        />
      </div>
    </div>
  );
}

export default SelectedPeriodo;
