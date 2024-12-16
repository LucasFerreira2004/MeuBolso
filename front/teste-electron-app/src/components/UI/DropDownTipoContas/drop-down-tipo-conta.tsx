import { useEffect, useState } from "react";
import style from "./drop-down-tipo-conta.module.css";

interface TipoConta {
  tipoConta: string;
  id: number;
}

interface DropDownTipoContaProps {
  toggleDropdownTipoConta: () => void;
  setTipoConta: (id: number) => void;  // Função para atualizar o estado no componente pai
}

const DropDownTipoConta = ({ toggleDropdownTipoConta, setTipoConta }: DropDownTipoContaProps) => {
  const [tiposConta, setTiposConta] = useState<TipoConta[]>([]); // Estado para armazenar os tipos de conta

  // Fazendo a requisição GET para pegar os tipos de conta
  useEffect(() => {
    const fetchTiposConta = async () => {
      try {
        const response = await fetch("http://localhost:8080/tipoConta");
        if (response.ok) {
          const data = await response.json();
          setTiposConta(data); // Atualiza o estado com os dados da API
        } else {
          console.error("Erro ao buscar tipos de conta");
        }
      } catch (error) {
        console.error("Erro na requisição", error);
      }
    };

    fetchTiposConta(); // Chama a função de requisição
  }, []); // O array vazio [] significa que o useEffect será chamado apenas uma vez

  // Função para lidar com a seleção do tipo de conta
  const handleSelectTipoConta = (id: number) => {
    setTipoConta(id);  // Passa o ID do tipo de conta para o componente pai
    toggleDropdownTipoConta(); // Fecha o dropdown após a seleção
  };

  return (
    <div className={style.containerTipo}>
      <div className={style.header}>
        <h3>Opção</h3>
        <img
          src="/assets/iconsModal/iconX.svg"
          alt="Fechar"
          className={style.iconClose}
          onClick={toggleDropdownTipoConta} // Chama a função para fechar o dropdown no componente pai
        />
      </div>
      {/* Se o dropdown estiver aberto, mostra a lista de tipos de conta */}
      <div>
        {tiposConta.length > 0 ? (
          <ul className={style.dropdownList}>
            {tiposConta.map((tipo) => (
              <li key={tipo.id} onClick={() => handleSelectTipoConta(tipo.id)}>
                <a href="#">{tipo.tipoConta}</a>
              </li>
            ))}
          </ul>
        ) : (
          <p>Carregando tipos de conta...</p> // Mensagem exibida enquanto os dados não são carregados
        )}
      </div>
    </div>
  );
};

export default DropDownTipoConta;
