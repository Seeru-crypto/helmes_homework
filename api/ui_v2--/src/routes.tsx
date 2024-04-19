import { Route } from 'react-router-dom';
import Second from "./Second.tsx";
import Home from "./Home.tsx";
import PageNotFound from "./page-not-found.tsx";
import ErrorBoundaryRoutes from "./config/error-boundary-routes.tsx";

const AppRoutes = () => {
  return (
    <div className="view-routes">
        <ErrorBoundaryRoutes>
        <Route index element={<Home />} />
        <Route path="/second"  index element={<Second />} />
        <Route path="*" element={<PageNotFound />} />
        </ErrorBoundaryRoutes>
        </div>
  );
};

export default AppRoutes;
