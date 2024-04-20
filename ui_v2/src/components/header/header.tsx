import styles from "./header.module.scss"
import {useNavigate} from "react-router-dom";

function Header() {
    const navigate = useNavigate();

    function navigateToPage({ event, destination }: { event: React.MouseEvent<HTMLElement, MouseEvent>; destination: string }): void {
        event.preventDefault();
        navigate(`${destination}`);
    }

  return (
      <div className={styles.container}>
          <nav className={styles.navigationButtons}>
              <button
              onClick={(event) => navigateToPage({event, destination: "/"})}
              >
                  First
              </button>
              <button
              onClick={(event) => navigateToPage({event, destination: "/users"})}
              >
                  users
              </button>
          </nav>
      </div>
  )
}

export default Header
