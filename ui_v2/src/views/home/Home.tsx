import UserRegistrationForm from "../../components/user_registration_form/userRegistrationForm.tsx";
import styles from "./Home.module.scss"

function Home() {

    return (
        <div className={styles.container}>
            <UserRegistrationForm/>
        </div>
    )
}

export default Home;