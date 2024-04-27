import UserRegistrationForm from "../users/userRegistration/userRegistrationForm.tsx";
import styles from "./Home.module.scss"
import UserCardList from "../users/userRegistration/userCardList/userCardList.tsx";

function Home() {

    return (
        <div className={styles.container}>
            <UserRegistrationForm/>
            <UserCardList/>
        </div>
    )
}

export default Home;