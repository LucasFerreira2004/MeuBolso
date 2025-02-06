import React, { useEffect, useState } from 'react';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement, CategoryScale, LinearScale } from 'chart.js';
import styles from './categorias-despesas.module.css'; 

ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale, LinearScale);

interface DespesaCategoria {
  id: number;
  cor: string;
  nome: string;
  valorTotal: number;
  percentual: number;
}

interface CategoriasDespesasProps {
  mes: number;
  ano: number;
}

const CategoriasDespesas: React.FC<CategoriasDespesasProps> = ({ mes, ano }) => {
  const [dados, setDados] = useState<DespesaCategoria[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      return;
    }

    const fetchData = async () => {
      try {
        const url = `http://localhost:8080/dashboards/despesasCategoria?ano=${ano}&mes=${mes}`;
        const response = await fetch(url, {
          headers: {
            Authorization: `Bearer ${token}`, 
            'Content-Type': 'application/json',
          },
        });

        if (!response.ok) {
          throw new Error('Erro ao buscar dados');
        }

        const result = await response.json();
        console.log('Dados recebidos do servidor:', result);

        setDados(result);
      } catch (error) {
        console.error('Erro ao buscar dados:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [ano, mes]);
  const chartData = {
    labels: dados.map((d) => d.nome), 
    datasets: [
      {
        label: 'Percentual',
        data: dados.map((d) => d.percentual), // Usa o percentual
        backgroundColor: dados.map((d) => `#${d.cor}`), // Cor de fundo
        hoverBackgroundColor: dados.map((d) => `#${d.cor}`), // Cor ao passar o mouse
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      title: {
        display: true,
        text: `Despesas por Categoria - ${mes}/${ano}`,
      },
      tooltip: {
        callbacks: {
          label: (context: any) => {
            const percentual = context.raw as number;
            return `${percentual.toFixed(2)}%`; // Exibe o percentual
          },
        },
      },
    },
  };

  return (
    <div className={styles.chartContainer}>
      {loading ? (
        <p className={styles.loadingText}>Carregando dados...</p>
      ) : (
        <div>
          <h2 className={styles.chartTitle}>Gráfico de Pizza de Percentual de Despesas por Categoria</h2>
          <Pie data={chartData} options={options} />
        </div>
      )}
    </div>
  );
};

export default CategoriasDespesas;
