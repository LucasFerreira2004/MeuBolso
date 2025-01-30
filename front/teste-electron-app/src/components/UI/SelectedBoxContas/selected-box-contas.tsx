import { useEffect, useState } from "react";
import Select from "react-select";

interface Banco {
  nome: string;
  iconeUrl: string;
}

interface Conta {
  id: number;
  saldo: number;
  banco: Banco;
  tipo_conta: {
    tipoConta: string;
  };
}

function SelectBoxDespesas() {
  const [contas, setContas] = useState<Conta[]>([]);
  const [, setSelectedConta] = useState<Conta | null>(null);

  const getDataAtual = (): string => {
    const data = new Date();
    const ano = data.getFullYear();
    const mes = String(data.getMonth() + 1).padStart(2, "0");
    const dia = String(data.getDate()).padStart(2, "0");
    return `${ano}-${mes}-${dia}`;
  };

  useEffect(() => {
    const token = localStorage.getItem("authToken");

    if (!token) {
      console.error("Token de autenticação não encontrado.");
      return;
    }

    const dataReferencia = getDataAtual();
    const url = `http://localhost:8080/contas?data=${dataReferencia}`;

    fetch(url, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Erro ao buscar contas.");
        }
        return response.json();
      })
      .then((data: Conta[]) => {
        setContas(data);
        setSelectedConta(data.length > 0 ? data[0] : null);
      })
      .catch((error) => console.error("Erro ao buscar contas:", error));
  }, []);

  const options = contas.map((conta) => ({
    value: conta.id,
    label: (
      <div style={{ display: "flex", alignItems: "center" }}>
        <img
          src={conta.banco.iconeUrl}
          alt={conta.banco.nome}
          width={20}
          height={20}
          style={{ marginRight: "10px" }}
        />
        <span>
          {conta.banco.nome} - Saldo: R$ {conta.saldo.toFixed(2)}
        </span>
      </div>
    ),
  }));

  const handleChange = (selectedOption: any) => {
    const contaSelecionada = contas.find((conta) => conta.id === selectedOption.value) || null;
    setSelectedConta(contaSelecionada);
  };

  return (
    <div className="container">
      <div className="selectBox">
        <label>Conta: </label>
        <Select
          options={options}
          onChange={handleChange}
          placeholder="Selecione uma conta"
        />
      </div>
    </div>
  );
}

export default SelectBoxDespesas;