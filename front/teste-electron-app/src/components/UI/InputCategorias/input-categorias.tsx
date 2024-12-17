import React from "react";
import style from "./input-categorias.module.css";

interface InputCategoriasProps {
  id: number;
  nome: string;
  tipo: string; 
  cor: string; 
  onClick: () => void; // onClick está correto como prop
}

const InputCategorias: React.FC<InputCategoriasProps> = ({ id, nome, tipo, cor, onClick }) => {
  const corHex = cor.startsWith('#') ? cor : `#${cor}`;
  const tipoClass = tipo === "DESPESA" ? style.despesa : style.receita;

  return (
    <li id={`categoria-${id}`} className={`${style.listItem} ${tipoClass}`} onClick={onClick}>
      {/* Ícone de cor */}
      <div className={style.iconeCor} style={{ backgroundColor: corHex }}></div> 
      {/* Exibição do nome da categoria com a cor entre parênteses */}
      <a>{nome}</a>
    </li>
  );
};

export default InputCategorias;
