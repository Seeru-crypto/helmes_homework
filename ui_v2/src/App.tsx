import './App.css'
import styles from "./styles.module.scss"
import {ToastContainer} from 'react-toastify';
import AppRoutes from "./routes.tsx";
import Header from "./components/header/header.tsx";
import Footer from "./components/Footer/Footer.tsx";
import {BrowserRouter} from "react-router-dom";

function App() {

    return (
        <BrowserRouter>
            <div className={styles.AppHeader}>
                <ToastContainer className="toastify-container" toastClassName="toastify-toast"/>
                <Header/>
                <div className={styles.container}>
                    <AppRoutes/>
                </div>
                <Footer/>
            </div>
        </BrowserRouter>
    )
}

export default App
