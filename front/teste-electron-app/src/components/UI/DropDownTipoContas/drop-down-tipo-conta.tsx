import { useEffect, useState } from "react";
import style from "./drop-down-tipo-conta.module.css";

interface TipoConta {
  tipoConta: string;
  id: number;
}

interface DropDownTipoContaProps {
  toggleDropdownTipoConta: () => void;
  setTipoConta: (id: number) => void;
}

const DropDownTipoConta = ({ toggleDropdownTipoConta, setTipoConta }: DropDownTipoContaProps) => {
  const [tiposConta, setTiposConta] = useState<TipoConta[]>([]);

  useEffect(() => {
    const fetchTiposConta = async () => {
      const token = localStorage.getItem("authToken");
      try {
        const response = await fetch("http://localhost:8080/tipoConta", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (response.ok) {
          const data = await response.json();
          setTiposConta(data);
        } else {
          console.error("Erro ao buscar tipos de conta");
        }
      } catch (error) {
        console.error("Erro na requisição", error);
      }
    };

    fetchTiposConta();
  }, []);

  const handleSelectTipoConta = (id: number) => {
    setTipoConta(id);
    toggleDropdownTipoConta();
  };

  return (
    <div className={style.containerTipo}>
      <div className={style.header}>
        <h3>Opção</h3>
        <img
          src="/assets/iconsModal/iconX.svg"
          alt="Fechar"
          className={style.iconClose}
          onClick={toggleDropdownTipoConta}
        />
      </div>
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
          <p>Carregando tipos de conta...</p>
        )}
      </div>
    </div>
  );
};

export default DropDownTipoConta;
