import { Link } from "react-router-dom";
import style from "./side-bar.module.css";

function SideBar() {
  return (
    <div className={style.sidebar}>
      <div className={style.auxLine}>
        <Link to="/home">
          <div className={style.divlogo}>
            <img
              src="/assets/logo-meuBolso.svg"
              alt="Ícone meuBolso"
              className={style.iconLogo}
            />
          </div>
        </Link>
        <nav>
          <ul className={style.navList}>
            <Link to="/perfil" className={style.navLink}>
              <li className={style.navItem}>
                <img
                  src="/assets/profile.svg"
                  alt="Ícone de Perfil"
                  className={style.icon}
                />
                Perfil
              </li>
            </Link>
            <Link to="/contas" className={style.navLink}>
              <li className={style.navItem}>
                <img
                  src="/assets/bank.svg"
                  alt="Ícone de Contas Bancárias"
                  className={style.icon}
                />
                Contas Bancárias
              </li>
            </Link>
            <Link to="/transacoes" className={style.navLink}>
              <li className={style.navItem}>
                <img
                  src="/assets/translation.svg"
                  alt="Ícone de Transações"
                  className={style.icon}
                />
                Transações
              </li>
            </Link>
            <Link to="/metas" className={style.navLink}>
              <li className={style.navItem}>
                <img
                  src="/assets/pig-bank.svg"
                  alt="Ícone de Metas"
                  className={style.icon}
                />
                Metas
              </li>
            </Link>
            <Link to="/orcamentos" className={style.navLink}>
              <li className={style.navItem}>
                <img
                  src="/assets/orcamentos.svg"
                  alt="Ícone de Relatórios"
                  className={style.icon}
                />
                Orçamentos
              </li>
            </Link>
            <Link to="/relatorios" className={style.navLink}>
              <li className={style.navItem}>
                <img
                  src="/assets/graphics.svg"
                  alt="Ícone de Relatórios"
                  className={style.icon}
                />
                Relatórios
              </li>
            </Link>
            <Link to="/categorias" className={style.navLink}>
              <li className={style.navItem}>
                <img
                  src="/assets/categories.svg"
                  alt="Ícone de Categorias"
                  className={style.icon}
                />
                Categorias
              </li>
            </Link>
          </ul>
        </nav>
      </div>
      <div>
        <Link to="/" className={style.link}>
          <li className={style.sair}>
            <img src="/assets/iconsContas/sair.svg" alt="" />
            <p>Sair</p>
          </li>
        </Link>
      </div>
    </div>
  );
}

export default SideBar;
