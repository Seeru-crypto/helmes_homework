import {useAppDispatch, useAppSelector} from "./store/store.ts";
import {increaseCount} from "./entities/users/setting.reducer.ts";
import UserRegistrationForm from "./components/user_registration_form/userRegistrationForm.tsx";

function Home () {
    // const [counter, setCounter] = useState(0)
    const dispatch = useAppDispatch();
    const count = useAppSelector((state) => state.setting.count);

    return (
        <div>
            Home
            <button onClick={() => dispatch(increaseCount())}>
                click me
            </button>
            <p>current count is {count}</p>
            <UserRegistrationForm />
        </div>
    )
}

export default Home;