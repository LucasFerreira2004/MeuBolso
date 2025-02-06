import React from 'react';
import CategoriasDespesas from '../../components/UI/ChartsRelatorios/CategoriasDespesas/categorias-despesas'; // Importe o componente CategoriasDespesas

const Relatorios: React.FC = () => {
  
  return (
    <div>
      <h1>Relatórios de Despesas</h1>

      <CategoriasDespesas /> {/* Chame o componente CategoriasDespesas aqui */}
    </div>
  );
};

export default Relatorios;
