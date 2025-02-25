import { NavLink } from "react-router-dom";
import style from "./side-bar.module.css";

function SideBar() {
  return (
    <div className={style.sidebar}>
      <div className={style.auxLine}>
        <div className={style.divlogo}>
          <img
            src="assets/logo-meuBolso.svg"
            alt="Ícone meuBolso"
            className={style.iconLogo}
          />
        </div>
        <nav>
          <NavLink
            to="/home"
            className={({ isActive }) =>
              isActive ? `${style.navLink} ${style.active}` : style.navLink
            }
          >
            <li className={style.navItem}>
              <img
                src="assets/home.svg"
                alt="Ícone de home"
                className={style.icon}
              />
              Home
            </li>
          </NavLink>
          <ul className={style.navList}>
            <NavLink
              to="/perfil"
              className={({ isActive }) =>
                isActive ? `${style.navLink} ${style.active}` : style.navLink
              }
            >
              <li className={style.navItem}>
                <img
                  src="assets/profile.svg"
                  alt="Ícone de Perfil"
                  className={style.icon}
                />
                Perfil
              </li>
            </NavLink>
            <NavLink
              to="/contas"
              className={({ isActive }) =>
                isActive ? `${style.navLink} ${style.active}` : style.navLink
              }
            >
              <li className={style.navItem}>
                <img
                  src="assets/bank.svg"
                  alt="Ícone de Contas Bancárias"
                  className={style.icon}
                />
                Contas Bancárias
              </li>
            </NavLink>
            <NavLink
              to="/transacoes"
              className={({ isActive }) =>
                isActive ? `${style.navLink} ${style.active}` : style.navLink
              }
            >
              <li className={style.navItem}>
                <img
                  src="assets/translation.svg"
                  alt="Ícone de Transações"
                  className={style.icon}
                />
                Transações
              </li>
            </NavLink>
            <NavLink
              to="/orcamentos"
              className={({ isActive }) =>
                isActive ? `${style.navLink} ${style.active}` : style.navLink
              }
            >
              <li className={style.navItem}>
                <img
                  src="assets/orcamentos.svg"
                  alt="Ícone de Relatórios"
                  className={style.icon}
                />
                Orçamentos
              </li>
            </NavLink>
            <NavLink
              to="/relatorios"
              className={({ isActive }) =>
                isActive ? `${style.navLink} ${style.active}` : style.navLink
              }
            >
              <li className={style.navItem}>
                <img
                  src="assets/graphics.svg"
                  alt="Ícone de Relatórios"
                  className={style.icon}
                />
                Relatórios
              </li>
            </NavLink>
            <NavLink
              to="/categorias"
              className={({ isActive }) =>
                isActive ? `${style.navLink} ${style.active}` : style.navLink
              }
            >
              <li className={style.navItem}>
                <img
                  src="assets/categories.svg"
                  alt="Ícone de Categorias"
                  className={style.icon}
                />
                Categorias
              </li>
            </NavLink>
          </ul>
        </nav>
      </div>
      <div>
        <NavLink
          to="/"
          className={({ isActive }) =>
            isActive ? `${style.link} ${style.active}` : style.link
          }
        >
          <li className={style.sair}>
            <img src="assets/iconsContas/sair.svg" alt="" />
            <p>Sair</p>
          </li>
        </NavLink>
      </div>
    </div>
  );
}

export default SideBar;