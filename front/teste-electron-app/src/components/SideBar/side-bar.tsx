import { Link } from 'react-router-dom';
import styles from './side-bar.module.css';

function SideBar() {
  return (
    <div className={styles.sidebar}>
      <nav>
        <ul className={styles.navList}>
          <li className={styles.navItem}>
            <img
              src="/assets/profile.svg"
              alt="Ícone de Perfil"
              className={styles.icon}
            />
            <Link to ="/perfil">Perfil</Link>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/bank.svg"
              alt="Ícone de Contas Bancárias"
              className={styles.icon}
            />
            <Link to="/contas">Contas Bancarias</Link>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/translation.svg"
              alt="Ícone de Transações"
              className={styles.icon}
            />
            <Link to="/transacoes">transações</Link>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/pig-bank.svg"
              alt="Ícone de Metas"
              className={styles.icon}
            />
            <Link to="/Metas">Metas</Link>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/graphics.svg"
              alt="Ícone de Relatórios"
              className={styles.icon}
            />
            <Link to="/Relatorios">Relatorios</Link>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/categories.svg"
              alt="Ícone de Categorias"
              className={styles.icon}
            />
            <Link to="/categorias">Categorias</Link>
          </li>
        </ul>
      </nav>
    </div>
  );
}

export default SideBar;
