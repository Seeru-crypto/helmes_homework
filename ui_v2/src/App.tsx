import './App.css'
import styles from "./styles.module.scss"
import {ToastContainer} from 'react-toastify';
import AppRoutes from "./routes.tsx";
import Header from "./components/header/header.tsx";
import Footer from "./components/Footer/Footer.tsx";
import 'react-toastify/dist/ReactToastify.css';
import {useEffect} from "react";
import {useAppDispatch} from "./store/store.ts";
import {getServerStatus} from "./entities/setting.reducer.ts";

function App() {
    const dispatch = useAppDispatch()

    useEffect(() => {
        dispatch(getServerStatus())
    }, [])

    return (
            <div className={styles.root}>
                <ToastContainer className="toastify-container" toastClassName="toastify-toast" />
                <Header/>
                <div className={styles.container}>
                    <AppRoutes/>
                </div>
                <Footer/>
            </div>
    )
}

export default App
