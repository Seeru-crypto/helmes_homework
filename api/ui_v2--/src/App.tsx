import {useState} from 'react'
import './App.css'
import styles from "./styles.module.scss"
import {BrowserRouter} from 'react-router-dom';
import {ToastContainer} from 'react-toastify';
import AppRoutes from "./routes.tsx";

function App() {
  const [count, setCount] = useState(0)

  return (
    <BrowserRouter>
        <div>
            <ToastContainer position="top-left" className="toastify-container" toastClassName="toastify-toast" />
            <div className={styles.container}>
                <AppRoutes />
            </div>
            <h1>Vite + React</h1>
            <div className="card">
                <button onClick={() => setCount((count) => count + 1)}>
                    count is {count}
                </button>
                <p>
                    Edit <code>src/App.tsx</code> and save to test HMR
                </p>
            </div>
            <p className="read-the-docs">
                Click on the Vite and React logos to learn more
            </p>
        </div>
    </BrowserRouter>
  )
}

export default App
