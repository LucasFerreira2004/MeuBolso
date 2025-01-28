import { useState } from "react";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-contas.module.css";
import DropDownBancos from "../UI/DropDownBancos/drop-down-bancos";
import DropDownTipoConta from "../UI/DropDownTipoContas/drop-down-tipo-conta";

interface Conta {
  id: number;
  saldo: number;
  banco: {
    nome: string;
    iconeUrl: string;
  };
  tipo_conta: {
    tipoConta: string;
  };
  id_banco: number;
  id_tipo_conta: number;
  id_usuario: number;
  descricao: string;
  data: string;
}

interface ModalContasProps {
  closeModal: () => void;
  onAddConta: (novaConta: Conta) => void;
}

const sendData = async ({
  saldo,
  id_banco,
  id_tipo_conta,
  id_usuario,
  descricao,
  data,
}: Conta) => {
  try {
    const token = localStorage.getItem("authToken");

    if (!token) {
      return {
        success: false,
        error: {
          message: "Token não encontrado. O usuário não está autenticado.",
        },
      };
    }

    const url = `http://localhost:8080/contas?data=${data}`;

    const novaConta = {
      saldo,
      id_banco,
      id_tipo_conta,
      id_usuario,
      descricao: descricao.trim(),
      data,
    };

    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(novaConta),
    });

    if (!response.ok) {
      throw new Error(`Erro ${response.status}: ${response.statusText}`);
    }

    const dataResponse = await response.json();
    console.log("Resposta do servidor:", dataResponse);
    return { success: true, data: dataResponse };
  } catch (error) {
    console.error("Erro na requisição fetch:", error);
    return {
      success: false,
      error: { message: "Erro na conexão com o servidor." },
    };
  }
};

function ModalContas({ closeModal, onAddConta }: ModalContasProps) {
  const [openBancos, setOpenBancos] = useState(false);
  const [openTipoConta, setOpenTipoConta] = useState(false);
  const [isRotatedBancos, setIsRotatedBancos] = useState(false);
  const [isRotatedTipoConta, setIsRotatedTipoConta] = useState(false);
  const [saldo, setSaldo] = useState<string>(""); // Estado como string para a máscara
  const [selectedBanco, setSelectedBanco] = useState<number | null>(null);
  const [selectedBancoName, setSelectedBancoName] = useState<string | null>(null); // Para armazenar o nome do banco
  const [selectedTipoConta, setSelectedTipoConta] = useState<number | null>(null);
  const [selectedTipoContaName, setSelectedTipoContaName] = useState<string | null>(null); // Para armazenar o nome do tipo de conta
  const [descricao, setDescricao] = useState("");
  const [data, setData] = useState<string>("");

  const toggleDropdownBancos = () => {
    if (openTipoConta) {
      setOpenTipoConta(false);
      setIsRotatedTipoConta(false);
    }
    setOpenBancos(!openBancos);
    setIsRotatedBancos(!isRotatedBancos);
  };

  const toggleDropdownTipoConta = () => {
    if (openBancos) {
      setOpenBancos(false);
      setIsRotatedBancos(false);
    }
    setOpenTipoConta(!openTipoConta);
    setIsRotatedTipoConta(!isRotatedTipoConta);
  };

  // Função para formatar o valor como moeda
  const formatarMoeda = (valor: string): string => {
    let valorNumerico = valor.replace(/\D/g, '');
    valorNumerico = (Number(valorNumerico) / 100).toFixed(2);
    valorNumerico = valorNumerico.replace('.', ',');
    valorNumerico = valorNumerico.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
    return `R$ ${valorNumerico}`;
  };

  const removerFormatacaoMoeda = (valorFormatado: string): number => {
    const valorNumerico = valorFormatado
      .replace("R$ ", "")
      .replace(/\./g, "")
      .replace(",", ".");
    return parseFloat(valorNumerico);
  };

  // Função para formatar o nome das categorias (Banco e Tipo de Conta)
  const formatarCategoria = (categoria: string) => {
    return categoria
      .toLowerCase()  // transforma tudo para minúsculo
      .replace(/_/g, ' ')  // substitui underscores por espaços
      .replace(/\b\w/g, (char) => char.toUpperCase());  // deixa a primeira letra de cada palavra em maiúscula
  };

  const handleSaldoChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const valorDigitado = e.target.value;
    const valorFormatado = formatarMoeda(valorDigitado);
    setSaldo(valorFormatado); // Atualiza o estado com o valor formatado
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const saldoNumerico = removerFormatacaoMoeda(saldo); // Converte o valor formatado para número

    if (isNaN(saldoNumerico) || saldoNumerico <= 0) {
      return;
    }

    if (!data || !selectedBanco || !selectedTipoConta || !descricao) {
      return;
    }

    const id_usuario = 1;

    const novaConta: Conta = {
      id: 0,
      saldo: saldoNumerico,
      banco: { nome: selectedBancoName || "Banco não selecionado", iconeUrl: "/path/to/icon" },
      tipo_conta: { tipoConta: selectedTipoContaName || "Tipo de Conta Exemplo" },
      id_banco: selectedBanco,
      id_tipo_conta: selectedTipoConta!,
      id_usuario,
      descricao,
      data,
    };

    const result = await sendData(novaConta);

    if (result.success) {
      onAddConta(result.data);
      closeModal();
    }
  };

  return (
    <div className={style.overlay} onClick={closeModal}>
      <div className={style.containerModalContas} onClick={(e) => e.stopPropagation()}>
        <div className={style.headerModalContas}>
          <p>Conta bancária</p>
          <img
            src="/assets/iconsModal/iconX.svg"
            alt="Fechar"
            className={style.iconClose}
            onClick={closeModal}
          />
        </div>
        <form className={style.formModalContas} onSubmit={handleSubmit}>
          <div className={style.formGroup}>
            <label htmlFor="saldo">Saldo:</label>
            <input
              id="saldo"
              type="text"
              placeholder="R$ 0,00"
              value={saldo}
              onChange={handleSaldoChange}
            />
          </div>

          <div className={style.formGroup}>
            <label htmlFor="data">Data:</label>
            <input
              id="data"
              type="date"
              value={data}
              onChange={(e) => setData(e.target.value)}
            />
          </div>

          <div className={style.formGroup}>
            <label htmlFor="descricao">Descrição:</label>
            <textarea
              id="descricao"
              placeholder="Digite uma descrição"
              value={descricao}
              onChange={(e) => setDescricao(e.target.value)}
            />
          </div>

          <div className={style.divDropContas}>
            <InputWithIcon
              label="Banco:"
              iconSrc="/assets/iconsModal/notes.svg"
              placeholder={selectedBancoName ? `Banco: ${formatarCategoria(selectedBancoName)}` : "Selecione o banco"}
            />
            <img
              src="/assets/iconsModal/iconsarrowleft.svg"
              alt="Abrir seleção de bancos"
              className={`${style.iconLogoArrow} ${isRotatedBancos ? style.rotated : ""}`}
              onClick={toggleDropdownBancos}
            />
            {openBancos && (
              <DropDownBancos
                toggleDropdownBancos={toggleDropdownBancos}
                setBanco={(id, nome) => { 
                  setSelectedBanco(id);
                  setSelectedBancoName(nome);
                }}
              />
            )}
          </div>

          <div className={style.divDropContas}>
            <InputWithIcon
              label="Tipo:"
              iconSrc="/assets/iconsModal/icontag.svg"
              placeholder={selectedTipoContaName ? `Tipo: ${formatarCategoria(selectedTipoContaName)}` : "Selecione o tipo"}
            />
            <img
              src="/assets/iconsModal/iconsarrowleft.svg"
              alt="Abrir seleção de tipo de conta"
              className={`${style.iconLogoArrow} ${isRotatedTipoConta ? style.rotated : ""}`}
              onClick={toggleDropdownTipoConta}
            />
            {openTipoConta && (
              <DropDownTipoConta
                toggleDropdownTipoConta={toggleDropdownTipoConta}
                setTipoConta={(id, nome) => { 
                  setSelectedTipoConta(id);
                  setSelectedTipoContaName(nome); 
                }}
              />
            )}
          </div>

          <div className={style.formActions}>
            <button type="submit" className={style.saveButton}>
              Salvar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default ModalContas;
