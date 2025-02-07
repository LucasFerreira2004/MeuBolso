import React from "react";

interface SelectedPeriodoProps {
  selectedValue: "SEMANAL" | "MENSAL" | "DIARIO"; // Corrigido o tipo para corresponder à expectativa
  onChange: (e: React.ChangeEvent<HTMLSelectElement>) => void;
}

const SelectedPeriodo: React.FC<SelectedPeriodoProps> = ({ selectedValue, onChange }) => {
  return (
    <div>
      <label>Periodicidade:</label>
      <select value={selectedValue} onChange={onChange}>
        <option value="DIARIO">Diária</option> {/* Corrigido para "DIARIO" */}
        <option value="SEMANAL">Semanal</option>
        <option value="MENSAL">Mensal</option>
      </select>
    </div>
  );
};

export default SelectedPeriodo;
