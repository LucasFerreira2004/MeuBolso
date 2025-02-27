import React, { useState, useEffect } from "react";
import { Bar } from "react-chartjs-2";
import DatePicker, { meses } from "../../Date/date";
import styles from "./balanco-bancos.module.css";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const BalancoBancos: React.FC = () => {
  const [dataInicial, setDataInicial] = useState({ mes: 1, ano: 2025 });
  const [dataFinal, setDataFinal] = useState({ mes: 12, ano: 2025 });
  const [balanco, setBalanco] = useState<{ ano: number; mes: number; saldo: number }[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  const fetchBalanco = async () => {
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      return;
    }

    const url = `http://localhost:8080/dashboards/saldo/balanco?anoInicial=${dataInicial.ano}&mesInicial=${dataInicial.mes}&anoFinal=${dataFinal.ano}&mesFinal=${dataFinal.mes}`;
    console.log("Iniciando requisição para URL:", url);

    try {
      const response = await fetch(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      console.log("Status da resposta:", response.status);
      if (!response.ok) {
        throw new Error(`Erro na API: ${response.statusText}`);
      }

      const json = await response.json();
      console.log("Dados recebidos:", json);

      setBalanco(json);
    } catch (error) {
      console.error("Erro ao buscar balanço:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    console.log("Datas atualizadas:", { dataInicial, dataFinal });
    fetchBalanco();
  }, [dataInicial, dataFinal]);

  const data = {
    labels: balanco.map((item) => `${meses[item.mes - 1]} / ${item.ano}`),
    datasets: [
      {
        label: "Saldo",
        data: balanco.map((item) => item.saldo),
        backgroundColor: "rgba(75, 192, 192, 0.8)", // Cor mais forte
        borderColor: "rgba(75, 192, 192, 1)",
        borderWidth: 2,
        barThickness: 40, // Ajusta a espessura das barras
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: "top" as const,
      },
      title: {
        display: true,
        text: "Balanço de Saldos por Bancos",
      },
    },
    scales: {
      x: {
        grid: {
          display: false, // Remove as linhas verticais do grid
        },
      },
      y: {
        beginAtZero: true, // Garante que o eixo Y começa do 0
        grid: {
          color: "#ddd", // Cor das linhas horizontais
        },
      },
    },
  };

  return (
    <div className={styles.container}>
      <h1>Balanço de Bancos</h1>
      <div className={styles.datePickers}>
        <div className={styles.containerDate}>
          <h3>Data Inicial</h3>
          <DatePicker
            mes={dataInicial.mes}
            ano={dataInicial.ano}
            onChange={(mes, ano) => setDataInicial({ mes, ano })}
          />
        </div>
        <div className={styles.containerDate}>
          <h3>Data Final</h3>
          <DatePicker
            mes={dataFinal.mes}
            ano={dataFinal.ano}
            onChange={(mes, ano) => setDataFinal({ mes, ano })}
          />
        </div>
      </div>
      {loading ? (
        <p>Carregando dados...</p>
      ) : (
        <Bar options={options} data={data} />
      )}
    </div>
  );
};

export default BalancoBancos;
