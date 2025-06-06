import axios from "axios";
import { useAuth } from "../services/AuthContext";

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_SIMO_APP_API_URL,
  headers: { "Content-Type": "application/json" },
});

// Interceptor để tự động refresh token nếu gặp lỗi 401
axiosInstance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const { refreshAccessToken, logout } = useAuth();
    
    if (error.response?.status === 401) {
      try {
        const newAccessToken = await refreshAccessToken();
        if (newAccessToken) {
          error.config.headers["Authorization"] = `Bearer ${newAccessToken}`;
          return axiosInstance.request(error.config);
        }
      } catch (err) {
        logout();
      }
    }
        // Nếu lỗi 403 - buộc logout
        console.log(error.response?.status);
        if (error.response?.status === 403) {
          logout(); // 👈 auto logout khi không đủ quyền
          navigate("/"); 
        }
    return Promise.reject(error);
  }
);

export default axiosInstance;
