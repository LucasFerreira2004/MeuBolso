import React, { useEffect, useState } from 'react';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement, CategoryScale, LinearScale, TooltipItem } from 'chart.js';
import styles from './categorias-receitas.module.css';

ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale, LinearScale);

interface ReceitasCategoria {
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
  const [dados, setDados] = useState<ReceitasCategoria[]>([]);
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
          label: (context: TooltipItem<'pie'>) => {
            const index = context.dataIndex; // Obtém o índice do item no tooltip
            const categoria = dados[index]; // Recupera os dados da categoria
            const valorTotal = categoria.valorTotal;
            const percentual = categoria.percentual;

            return `${categoria.nome}: R$ ${valorTotal.toFixed(2)} (${percentual.toFixed(2)}%)`;
          },
        },
      },
    },
  };

  return (
    <div className={styles.chartContainer}>
      {loading ? (
        <div className={styles.skeletonContainer}>
          <div className={styles.skeleton} />
          <div className={styles.skeleton} />
          <div className={styles.skeleton} />
        </div>
      ) : dados.length === 0 ? (
        <p className={styles.emptyText}>Nenhuma informação disponível para o período selecionado.</p>
      ) : (
        <div>
          <h2 className={styles.chartTitle}>Receitas por Categoria</h2>
          <Pie data={chartData} options={options} />
        </div>
      )}
    </div>
  );
};

export default CategoriasReceitas;