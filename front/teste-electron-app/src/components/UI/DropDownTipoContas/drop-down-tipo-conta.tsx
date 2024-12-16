import { useEffect, useState } from "react";
import style from "./drop-down-tipo-conta.module.css";

interface TipoConta {
  tipoConta: string;
  id: number;
}

const DropDownTipoConta = ({ toggleDropdownTipoConta }: { toggleDropdownTipoConta: () => void }) => {
  const [tiposConta, setTiposConta] = useState<TipoConta[]>([]); // Estado para armazenar os tipos de conta
  const [isOpen, setIsOpen] = useState(true); // Inicializa como aberto

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

  // Função para alternar o estado de abertura e fechamento do dropdown
  const toggleDropdown = () => {
    setIsOpen((prevState) => !prevState); // Alterna o estado entre verdadeiro e falso
  };

  return (
    <div className={style.containerTipo}>
      <div className={style.header}>
        <h3>Opção</h3>
        <img
          src="/assets/iconsModal/iconX.svg"
          alt="Fechar"
          className={style.iconClose}
          onClick={() => {
            toggleDropdown(); // Fecha o dropdown de tipo de conta
            toggleDropdownTipoConta(); // Chama a função para fechar o dropdown no componente pai
          }} // Chama a função de alternar
        />
      </div>
      {/* Se o dropdown estiver aberto, mostra a lista de tipos de conta */}
      {isOpen && (
        <div>
          {tiposConta.length > 0 ? (
            <ul className={style.dropdownList}>
              {tiposConta.map((tipo) => (
                <li key={tipo.id}>
                  <a href="#">{tipo.tipoConta}</a>
                </li>
              ))}
            </ul>
          ) : (
            <p>Carregando tipos de conta...</p> // Mensagem exibida enquanto os dados não são carregados
          )}
        </div>
      )}
    </div>
  );
};

export default DropDownTipoConta;
