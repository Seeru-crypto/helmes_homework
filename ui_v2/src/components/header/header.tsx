import styles from "./header.module.scss"
import {useNavigate} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "../../store/store.ts";
import Selector, {ISelectorOptions} from "../selector/selector.tsx";
import {useEffect, useState} from "react";
import {IUser} from "../../entities/interfaces/IUser.ts";
import {setCurrentUser} from "../../entities/setting.reducer.ts";
import CustomButton from "../button/Button.tsx";

function Header() {
    const navigate = useNavigate();
    const dispatch = useAppDispatch();
    const users = useAppSelector(state => state.user.users)
    const [formattedUsers, setFormatedUSers] = useState<ISelectorOptions[]>([])
    const [userNameLabel, setUserNameLabel] = useState("")

    useEffect(() => {
        formatUsers()
    }, [users])

    function formatUsers(): void {
        const options: ISelectorOptions[] = users.map(user => {
            return {
                value: user.id,
                label: user.name
            } as ISelectorOptions
        })
        setFormatedUSers(options)
    }

    function updateUser(id: string) {
        const user: IUser[] = users.filter(user => user.id == id)
        setUserNameLabel(user[0].name)
        dispatch(setCurrentUser(user))
    }

    function navigateToPage({event, destination}: {
        event: React.MouseEvent<HTMLElement, MouseEvent>;
        destination: string
    }): void {
        event.preventDefault();
        navigate(`${destination}`);
    }

    return (
        <div className={styles.container}>
            <nav className={styles.navigationButtons}>
                    <CustomButton
                        onClick={(event) => navigateToPage({event, destination: "/"})}>
                        Home
                    </CustomButton>
                    <CustomButton
                        onClick={(event) => navigateToPage({event, destination: "/users"})}>
                        users
                    </CustomButton>
            </nav>
            <div className={styles.userSelector}>
                <Selector value={userNameLabel} options={formattedUsers} onChange={(id) => updateUser(id)}></Selector>
            </div>
        </div>
    )
}

export default Header
