import {useAppDispatch, useAppSelector} from "../../store/store.ts";
import UserRegistrationForm from "../../components/user_registration_form/userRegistrationForm.tsx";
import styles from "./Home.module.scss"

function Home() {
    const dispatch = useAppDispatch();
    const count = useAppSelector((state) => state.setting.count);

    return (
        <div className={styles.container}>
            <UserRegistrationForm/>
        </div>
    )
}

export default Home;