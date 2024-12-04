import { Link } from 'react-router-dom';
import styles from './side-bar.module.css';

function SideBar() {
  return (
    <div className={styles.sidebar}>
      <Link to="/home">
        <div className={styles.divlogo}>
          <img
            src="/assets/logo-meuBolso.svg"
            alt="Ícone meuBolso"
            className={styles.iconLogo}
          />
        </div>
      </Link>
      <nav>
        <ul className={styles.navList}>
          <li className={styles.navItem}>
            <img
              src="/assets/profile.svg"
              alt="Ícone de Perfil"
              className={styles.icon}
            />
            <Link to="/perfil" className={styles.navLink}>Perfil</Link>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/bank.svg"
              alt="Ícone de Contas Bancárias"
              className={styles.icon}
            />
            <Link to="/contas" className={styles.navLink}>Contas Bancárias</Link>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/translation.svg"
              alt="Ícone de Transações"
              className={styles.icon}
            />
            <Link to="/transacoes" className={styles.navLink}>Transações</Link>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/pig-bank.svg"
              alt="Ícone de Metas"
              className={styles.icon}
            />
            <Link to="/Metas" className={styles.navLink}>Metas</Link>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/graphics.svg"
              alt="Ícone de Relatórios"
              className={styles.icon}
            />
            <Link to="/Relatorios" className={styles.navLink}>Relatórios</Link>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/categories.svg"
              alt="Ícone de Categorias"
              className={styles.icon}
            />
            <Link to="/categorias" className={styles.navLink}>Categorias</Link>
          </li>
        </ul>
      </nav>
    </div>
  );
}

export default SideBar;
