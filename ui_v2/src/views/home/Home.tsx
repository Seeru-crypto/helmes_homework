import {useAppDispatch, useAppSelector} from "../../store/store.ts";
import {increaseCount} from "../../entities/users/setting.reducer.ts";
import UserRegistrationForm from "../../components/user_registration_form/userRegistrationForm.tsx";
import styles from "./Home.module.scss"

function Home() {
    const dispatch = useAppDispatch();
    const count = useAppSelector((state) => state.setting.count);



    return (
        <div className={styles.container}>
            <div>
                <button onClick={() => dispatch(increaseCount())}>
                    click me
                </button>
                <p>current count is {count}</p>
            </div>
            <UserRegistrationForm/>
        </div>
    )
}

export default Home;