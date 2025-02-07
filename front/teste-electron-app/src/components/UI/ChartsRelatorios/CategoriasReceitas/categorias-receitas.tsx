import React, { useEffect, useState } from 'react';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement, CategoryScale, LinearScale } from 'chart.js';
import styles from './categorias-receitas.module.css'; 

ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale, LinearScale);

interface receitassCategoria {
  id: number;
  cor: string;
  nome: string;
  valorTotal: number;
  percentual: number;
}

interface CategoriasReceitasProps {
  mes: number;
  ano: number;
}

const CategoriasReceitas: React.FC<CategoriasReceitasProps> = ({ mes, ano }) => {
  const [dados, setDados] = useState<receitassCategoria[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      return;
    }

    const fetchData = async () => {
      try {
        const url = `http://localhost:8080/dashboards/receitasCategoria?ano=${ano}&mes=${mes}`;
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
        data: dados.map((d) => d.percentual), 
        backgroundColor: dados.map((d) => `#${d.cor}`),
        hoverBackgroundColor: dados.map((d) => `#${d.cor}`),
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      title: {
        display: true,
        text: `Receitas por Categoria - ${mes}/${ano}`,
      },
      tooltip: {
        callbacks: {
          label: (context: any) => {
            const percentual = context.raw as number;
            return `${percentual.toFixed(2)}%`; 
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
          <h2 className={styles.chartTitle}>Gráfico de Pizza de Percentual de Receitas por Categoria</h2>
          <Pie data={chartData} options={options} />
        </div>
      )}
    </div>
  );
};

export default CategoriasReceitas;
