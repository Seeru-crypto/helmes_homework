import styles from "./header.module.scss"

function Header() {
  return (
      <div className={styles.header}>
          <nav>
              <a href="/">first</a>
              <a href="/second">second</a>
          </nav>
      </div>
  )
}

export default Header
