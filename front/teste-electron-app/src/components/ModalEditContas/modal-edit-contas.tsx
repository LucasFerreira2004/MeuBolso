import { useState } from "react";
import InputWithIcon from "../UI/InputsModal/input-modal";
import style from "./modal-edit-contas.module.css";
import DropDownBancos from "../UI/DropDownBancos/drop-down-bancos";
import DropDownTipoConta from "../UI/DropDownTipoContas/drop-down-tipo-conta";

interface ModalEditContasProps {
  closeModal: () => void;
  conta: Conta;
  refreshContas: () => void;
}

interface Conta {
  id: number;
  saldo: number;
  id_banco: number;
  id_tipo_conta: number;
  data?: string;
  descricao?: string;
  banco?: {
    nome: string;
    iconeUrl: string;
  };
  tipo_conta?: {
    tipoConta: string;
  };
}

const sendData = async ({
  id,
  saldo,
  id_banco,
  id_tipo_conta,
  data,
  descricao,
}: Conta) => {
  try {
    const token = localStorage.getItem("authToken");

    if (!token) {
      return {
        success: false,
        error: { message: "Token não encontrado. O usuário não está autenticado." },
      };
    }

    const response = await fetch(`http://localhost:8080/contas/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        saldo,
        id_banco,
        id_tipo_conta,
        data,
        descricao,
      }),
    });

    if (response.ok) {
      return { success: true, data: await response.json() };
    } else {
      const errorData = await response.json();
      return { success: false, error: errorData };
    }
  } catch (error) {
    console.error("Erro na requisição", error);
    return { success: false, error: { message: "Erro na conexão com o servidor." } };
  }
};

// Função para formatar o valor como moeda
const formatarMoeda = (valor: string): string => {
  let valorNumerico = valor.replace(/\D/g, '');
  valorNumerico = (Number(valorNumerico) / 100).toFixed(2);
  valorNumerico = valorNumerico.replace('.', ',');
  valorNumerico = valorNumerico.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
  return `R$ ${valorNumerico}`;
};

// Função para remover a formatação e retornar um número
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

function ModalEditContas({ closeModal, conta, refreshContas }: ModalEditContasProps) {
  const [openBancos, setOpenBancos] = useState(false);
  const [openTipoConta, setOpenTipoConta] = useState(false);
  const [isRotatedBancos, setIsRotatedBancos] = useState(false);
  const [isRotatedTipoConta, setIsRotatedTipoConta] = useState(false);
  const [saldo, setSaldo] = useState<string>(formatarMoeda(conta.saldo.toString())); // Estado como string para a máscara
  const [selectedBanco, setSelectedBanco] = useState<number | null>(conta.id_banco);
  const [selectedBancoName, setSelectedBancoName] = useState<string | null>(conta.banco?.nome || ""); // Para armazenar o nome do banco
  const [selectedTipoConta, setSelectedTipoConta] = useState<number | null>(conta.id_tipo_conta);
  const [selectedTipoContaName, setSelectedTipoContaName] = useState<string | null>(conta.tipo_conta?.tipoConta || ""); // Para armazenar o nome do tipo de conta
  const [data, setData] = useState<string>(conta.data || "");
  const [descricao, setDescricao] = useState<string>(conta.descricao || "");
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const handleSaldoChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const valorDigitado = e.target.value;
    const valorFormatado = formatarMoeda(valorDigitado);
    setSaldo(valorFormatado); // Atualiza o estado com o valor formatado
  };

  const toggleDropdown = (
    dropdownSetter: React.Dispatch<React.SetStateAction<boolean>>,
    rotationSetter: React.Dispatch<React.SetStateAction<boolean>>,
    closeOtherDropdown?: () => void
  ) => {
    if (closeOtherDropdown) closeOtherDropdown();
    dropdownSetter((prev) => !prev);
    rotationSetter((prev) => !prev);
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const saldoNumerico = removerFormatacaoMoeda(saldo); // Converte o valor formatado para número

    if (isNaN(saldoNumerico) || saldoNumerico <= 0) {
      setErrorMessage("Por favor, insira um saldo válido.");
      return;
    }

    if (!data || !selectedBanco || !selectedTipoConta || !descricao) {
      setErrorMessage("Por favor, preencha todos os campos obrigatórios.");
      return;
    }

    const result = await sendData({
      id: conta.id,
      saldo: saldoNumerico,
      id_banco: selectedBanco!,
      id_tipo_conta: selectedTipoConta!,
      data,
      descricao,
    });

    if (result.success) {
      closeModal();
      refreshContas();
    } else {
      setErrorMessage(result.error?.message || "Erro ao atualizar a conta.");
    }
  };

  return (
    <div className={style.overlay} onClick={closeModal}>
      <div className={style.containerModalEditContas} onClick={(e) => e.stopPropagation()}>
        <div className={style.headerModalEditContas}>
          <p>Editar Conta Bancária</p>
          <img
            src="/assets/iconsModal/iconX.svg"
            alt="Fechar"
            className={style.iconClose}
            onClick={closeModal}
          />
        </div>
        <form className={style.formModalEditContas} onSubmit={handleSubmit}>
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
              placeholder={selectedBancoName ? `${formatarCategoria(selectedBancoName)}` : "Selecione o banco"}
            />
            <img
              src="/assets/iconsModal/iconsarrowleft.svg"
              alt="Abrir seleção de bancos"
              className={`${style.iconLogoArrow} ${isRotatedBancos ? style.rotated : ""}`}
              onClick={() =>
                toggleDropdown(setOpenBancos, setIsRotatedBancos, () => setOpenTipoConta(false))
              }
            />
            {openBancos && (
              <DropDownBancos
                toggleDropdownBancos={() => setOpenBancos(false)}
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
              placeholder={selectedTipoContaName ? `${formatarCategoria(selectedTipoContaName)}` : "Selecione o tipo"}
            />
            <img
              src="/assets/iconsModal/iconsarrowleft.svg"
              alt="Abrir seleção de tipo de conta"
              className={`${style.iconLogoArrow} ${isRotatedTipoConta ? style.rotated : ""}`}
              onClick={() =>
                toggleDropdown(setOpenTipoConta, setIsRotatedTipoConta, () => setOpenBancos(false))
              }
            />
            {openTipoConta && (
              <DropDownTipoConta
                toggleDropdownTipoConta={() => setOpenTipoConta(false)}
                setTipoConta={(id, nome) => { 
                  setSelectedTipoConta(id);
                  setSelectedTipoContaName(nome); 
                }}
              />
            )}
          </div>

          {errorMessage && <div className={style.errorMessage}>{errorMessage}</div>}

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

export default ModalEditContas;
