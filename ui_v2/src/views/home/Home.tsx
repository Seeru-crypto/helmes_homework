import UserRegistrationForm from "../../components/user_registration_form/userRegistrationForm.tsx";
import styles from "./Home.module.scss"
import UserCardList from "../../components/user_card_list/userCardList.tsx";

function Home() {

    return (
        <div className={styles.container}>
            <UserRegistrationForm/>
            <UserCardList/>
        </div>
    )
}

export default Home;