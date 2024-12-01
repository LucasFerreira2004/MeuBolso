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
            <span>Perfil</span>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/bank.svg"
              alt="Ícone de Contas Bancárias"
              className={styles.icon}
            />
            <span>Contas Bancárias</span>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/translation.svg"
              alt="Ícone de Transações"
              className={styles.icon}
            />
            <span>Transações</span>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/pig-bank.svg"
              alt="Ícone de Metas"
              className={styles.icon}
            />
            <span>Metas</span>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/graphics.svg"
              alt="Ícone de Relatórios"
              className={styles.icon}
            />
            <span>Relatórios</span>
          </li>
          <li className={styles.navItem}>
            <img
              src="/assets/categories.svg"
              alt="Ícone de Categorias"
              className={styles.icon}
            />
            <span>Categorias</span>
          </li>
        </ul>
      </nav>
    </div>
  );
}

export default SideBar;
