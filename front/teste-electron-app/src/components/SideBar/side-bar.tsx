import { Link } from 'react-router-dom';
import style from './side-bar.module.css';

function SideBar() {
  return (
    <div className={style.sidebar}>
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
          <li className={style.navItem}>
            <img
              src="/assets/profile.svg"
              alt="Ícone de Perfil"
              className={style.icon}
            />
            <Link to="/perfil" className={style.navLink}>Perfil</Link>
          </li>
          <li className={style.navItem}>
            <img
              src="/assets/bank.svg"
              alt="Ícone de Contas Bancárias"
              className={style.icon}
            />
            <Link to="/contas" className={style.navLink}>Contas Bancárias</Link>
          </li>
          <li className={style.navItem}>
            <img
              src="/assets/translation.svg"
              alt="Ícone de Transações"
              className={style.icon}
            />
            <Link to="/transacoes" className={style.navLink}>Transações</Link>
          </li>
          <li className={style.navItem}>
            <img
              src="/assets/pig-bank.svg"
              alt="Ícone de Metas"
              className={style.icon}
            />
            <Link to="/Metas" className={style.navLink}>Metas</Link>
          </li>
          <li className={style.navItem}>
            <img
              src="/assets/graphics.svg"
              alt="Ícone de Relatórios"
              className={style.icon}
            />
            <Link to="/Relatorios" className={style.navLink}>Relatórios</Link>
          </li>
          <li className={style.navItem}>
            <img
              src="/assets/categories.svg"
              alt="Ícone de Categorias"
              className={style.icon}
            />
            <Link to="/categorias" className={style.navLink}>Categorias</Link>
          </li>
        </ul>
      </nav>
    </div>
  );
}

export default SideBar;
