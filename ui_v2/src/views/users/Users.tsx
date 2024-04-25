import {useAppDispatch, useAppSelector} from "../../store/store.ts";
import {decreaseCount, increaseCount} from "../../entities/setting.reducer.ts";
import {useEffect} from "react";
import {getUsers} from "../../entities/users.reducer.ts";
import UserCardList from "../../components/user_card_list/userCardList.tsx";
import {IPage} from "../../entities/interfaces/IPage.ts";

function Users() {
    const dispatch = useAppDispatch();
    const count = useAppSelector((state) => state.setting.count);
    const users = useAppSelector((state) => state.user.users);

    useEffect(() => {
        const page: IPage = {
            sort: "createdAt,desc",
            size: 3,
            page: 0
        }
        dispatch(getUsers(page))
    }, [dispatch]);

    return (
        <div>
            <button onClick={() => dispatch(increaseCount())}>
                Increase
            </button>
            <button onClick={() => dispatch(decreaseCount())}>
                Decrease
            </button>
            <p>current count: {count}</p>
            <p>Number of users: {users.length}</p>
            <UserCardList />
        </div>
    )
}

export  default Users