import React, { useState } from 'react';
import CategoriasDespesas from '../../components/UI/ChartsRelatorios/CategoriasDespesas/categorias-despesas';
import DatePicker from '../../components/UI/Date/date'; // Importe o componente DatePicker

const Relatorios: React.FC = () => {
  const [mes, setMes] = useState(new Date().getMonth() + 1); // Mês atual (1-12)
  const [ano, setAno] = useState(new Date().getFullYear()); // Ano atual

  return (
    <div>
      <h1>Relatórios de Despesas</h1>
      
      {/* Adicione o DatePicker aqui */}
      <DatePicker
        mes={mes}
        ano={ano}
        onChange={(novoMes, novoAno) => {
          setMes(novoMes);
          setAno(novoAno);
        }}
      />

      {/* Passe o mês e o ano selecionados para o componente CategoriasDespesas */}
      <CategoriasDespesas mes={mes} ano={ano} />
    </div>
  );
};

export default Relatorios;
