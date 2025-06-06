import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../services/AuthContext";
import { useEffect, useState } from "react";

const ProtectedRoute = ({ element, requiredRole }) => {
  const { user, refreshAccessToken, loading } = useAuth();
  const [isAuthorized, setIsAuthorized] = useState(null);

  useEffect(() => {
    const checkAuth = async () => {
      if (!user) {
        setIsAuthorized(false);
        return;
      }

      let accessToken = localStorage.getItem("accessToken");
      if (!accessToken) {
        accessToken = await refreshAccessToken();
      }

      if (!accessToken) {
        setIsAuthorized(false);
        return;
      }

      // Kiểm tra quyền truy cập
      if (requiredRole && user.role !== requiredRole) {
        setIsAuthorized(false);
      } else {
        setIsAuthorized(true);
      }
    };

    checkAuth();
  }, [user, requiredRole]);

  if (loading || isAuthorized === null) return <p>Đang kiểm tra quyền truy cập...</p>;

  return isAuthorized ? element : <Navigate to="/" />;
};

export default ProtectedRoute;
