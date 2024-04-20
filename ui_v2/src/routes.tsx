import {Route, Routes} from 'react-router-dom';
import Users from "./Users.tsx";
import Home from "./Home.tsx";
import PageNotFound from "./page-not-found.tsx";

const AppRoutes = () => {
    return (
        <div>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/users" index element={<Users/>}/>
                <Route path="*" element={<PageNotFound/>}/>
            </Routes>
        </div>
    );
};

export default AppRoutes;
