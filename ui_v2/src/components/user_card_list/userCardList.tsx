import {IUser} from "../../entities/interfaces/IUser.ts";
import UserCard from "./UserCard/UserCard.tsx";
import styles from "./userCardList.module.scss"
import {useAppSelector} from "../../store/store.ts";

export interface IUserCardList {
}

export function UserCardList() {
    const users = useAppSelector((state) => state.user.users);
    // TODO: Implement to the user registration page and show 3 latest users
    return (
        <div className={styles.container}>
            {
                users.length > 0 ?
                    users.map((user: IUser) => {
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