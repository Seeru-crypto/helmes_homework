import {Route, Routes} from 'react-router-dom';
import Second from "./Second.tsx";
import Home from "./Home.tsx";
import PageNotFound from "./page-not-found.tsx";

const AppRoutes = () => {
    return (
        <div>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/second" index element={<Second/>}/>
                <Route path="*" element={<PageNotFound/>}/>
            </Routes>
        </div>
    );
};

export default AppRoutes;
