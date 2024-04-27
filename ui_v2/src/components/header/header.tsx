import styles from "./header.module.scss"
import {useNavigate} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "../../store/store.ts";
import Selector, {ISelectorOptions} from "../selector/selector.tsx";
import {useEffect, useState} from "react";
import {IUser} from "../../entities/interfaces/IUser.ts";
import {setCurrentUser} from "../../entities/setting.reducer.ts";

function Header() {
    const navigate = useNavigate();
    const dispatch = useAppDispatch();
    const users = useAppSelector(state => state.user.users)
    const [formattedUsers, setFormatedUSers] = useState<ISelectorOptions[]>([])

    useEffect(() => {
        formatUsers()
    }, [users])

    function formatUsers(): void {
        console.log("formating")
        const options: ISelectorOptions[] = users.map(user => {
            return {
                value: user.id,
                label: user.name
            }
        })
        setFormatedUSers(options)
    }

    function updateUser(id: string) {
        const user: IUser = users.filter(user => user.id == id)
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
                <button
                    onClick={(event) => navigateToPage({event, destination: "/"})}
                >
                    Landing
                </button>
                <button
                    onClick={(event) => navigateToPage({event, destination: "/users"})}
                >
                    users
                </button>
                <Selector defaultValue={""} options={formattedUsers} onChange={(id) => updateUser(id)}></Selector>
            </nav>
        </div>
    )
}

export default Header
