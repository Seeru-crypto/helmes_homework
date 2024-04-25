import {IUser} from "../../entities/interfaces/IUser.ts";
import UserCard from "./UserCard/UserCard.tsx";
import styles from "./userCardList.module.scss"
import {useAppDispatch, useAppSelector} from "../../store/store.ts";
import {useEffect} from "react";
import {IPage} from "../../entities/interfaces/IPage.ts";
import {getUsers} from "../../entities/users.reducer.ts";

export interface IUserCardList {
}

export function UserCardList() {
    const users = useAppSelector((state) => state.user.users);
    const dispatch = useAppDispatch();
    // TODO: Implement to the user registration page and show 3 latest users

    useEffect(() => {
        if (users.length !== 0) return;
        const page: IPage = {
            sort: "createdAt,desc",
            size: 3,
            page: 0
        }
        dispatch(getUsers(page))
    }, [dispatch]);

    return (
        <div className={styles.container}>
            {
                users.length > 0 ?
                    users
                        .slice(0,3)
                        .map((user: IUser) => {
                            return (
                                <UserCard key={user.name} title={user.name} size={"small"} sectors={user.sectors}/>
                            )
                        }
                    ) : <p>no users</p>
            }
        </div>
    )

}

export default UserCardList