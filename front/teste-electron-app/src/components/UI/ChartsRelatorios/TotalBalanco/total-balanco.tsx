import React, { useState, useEffect, useCallback } from "react";
import { Bar } from "react-chartjs-2";
import DatePicker, { meses } from "../../Date/date";
import style from "./total-balanco.module.css";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { baseUrl } from "../../../../api/api";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const TotalBalanco: React.FC = () => {
  const [dataInicial, setDataInicial] = useState({ mes: 1, ano: 2025 });
  const [dataFinal, setDataFinal] = useState({ mes: 3, ano: 2025 });
  const [balanco, setBalanco] = useState<
    { ano: number; mes: number; despesas: number; receitas: number }[]
  >([]);
  const [loading, setLoading] = useState<boolean>(true);

  const fetchBalanco = useCallback(async () => {
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      return;
    }

    const url = `${baseUrl}/dashboards/transacoes/balanco?anoInicial=${dataInicial.ano}&mesInicial=${dataInicial.mes}&anoFinal=${dataFinal.ano}&mesFinal=${dataFinal.mes}`;
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
  }, [dataInicial, dataFinal]);

  useEffect(() => {
    console.log("Datas atualizadas:", { dataInicial, dataFinal });
    fetchBalanco();
  }, [fetchBalanco, dataInicial, dataFinal]);

  const data = {
    labels: balanco.map((item) => `${meses[item.mes - 1]} / ${item.ano}`),
    datasets: [
      {
        label: "Despesas",
        data: balanco.map((item) => item.despesas),
        backgroundColor: "#C63A22",
        barPercentage: 0.8,
        categoryPercentage: 0.5,
      },
      {
        label: "Receitas",
        data: balanco.map((item) => item.receitas),
        backgroundColor: "#2A9D8F",
        barPercentage: 0.8,
        categoryPercentage: 0.5,
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
        text: "Balanço de Transações",
      },
    },
  };

  return (
    <div className={style.container}>
      <h1 className={style.title}>Balanço total</h1>
      <div className={style.datePickerContainer}>
        <div className={style.containerDate}>
          <h3>Data Inicial</h3>
          <DatePicker
            mes={dataInicial.mes}
            ano={dataInicial.ano}
            onChange={(mes, ano) => setDataInicial({ mes, ano })}
          />
        </div>
        <div className={style.containerDate}>
          <h3>Data Final</h3>
          <DatePicker
            mes={dataFinal.mes}
            ano={dataFinal.ano}
            onChange={(mes, ano) => setDataFinal({ mes, ano })}
          />
        </div>
      </div>
      {loading ? (
        <p className={style.loading}>Carregando dados...</p>
      ) : (
        <Bar options={options} data={data} />
      )}
    </div>
  );
};

export default TotalBalanco;