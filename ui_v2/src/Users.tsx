import {useAppDispatch, useAppSelector} from "./store/store.ts";
import {decreaseCount, increaseCount} from "./entities/users/setting.reducer.ts";

function Users() {
    const dispatch = useAppDispatch();
    const count = useAppSelector((state) => state.setting.count);

    return (
        <div>
            <button onClick={() => dispatch(increaseCount())}>
                Increase
            </button>
            <button onClick={() => dispatch(decreaseCount())}>
                Decrease
            </button>
            <p>current count: {count}</p>
        </div>
    )
}

export  default Users