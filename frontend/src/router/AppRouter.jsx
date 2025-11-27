import { BrowserRouter as Router, Routes, Route,Navigate  } from "react-router-dom";
import { AuthProvider, useAuth  } from "../services/AuthContext";
import ProtectedRoute from "../services/ProtectedRoute";
import Layout from "../components/Layout";
import Login from "../pages/Login";
import Home from "../pages/Home";
import TemplateManager from "../pages/TemplateManager";
import UploadData from "../pages/UploadData";
import UserManagement from "../pages/UserManagement";
import HistoryQuery from "../pages/HistoryQuery";
import FileUpload from "../pages/File Upload Management";
import FileDetails from "../pages/FileDetail";
import DataManagement from "../pages/DataManagement";
import RequestManagement from "../pages/RequestManagement";
const AppRoutes  = () => {
  const { user, loading } = useAuth(); // 🔹 Dùng useAuth() ở đây
  if (loading) {
    return <p>Đang tải...</p>;
  }
  return (
        <Routes>
        <Route path="/" element={user ? <Navigate to="/upload" /> : <Login />} />
          
          {/* Wrap all protected pages with Layout */}
          <Route element={<Layout />}>
            {/* <Route path="/home" element={<Home />} /> */}
            <Route path="/template" element={<ProtectedRoute element={<TemplateManager />} requiredRole={["ADMIN","MAKER"]} />} />
            <Route path="/upload" element={<ProtectedRoute element={<UploadData />} requiredRole={["ADMIN", "MAKER"]}  />} />
            <Route path="/users" element={<ProtectedRoute element={<UserManagement />} requiredRole={["ADMIN"]} />} />
            {/* <Route path="/data_management" element={<ProtectedRoute element={<DataManagement />} requiredRole={["ADMIN", "CHECKER"]} />} /> */}
            <Route path="/history" element={<ProtectedRoute element={<RequestManagement />} requiredRole={["ADMIN", "MAKER", "CHECKER"]}  />} />
            <Route path="/file_upload" element={<ProtectedRoute element={<FileUpload />} requiredRole={["ADMIN", "MAKER", "CHECKER"]} />} />
            <Route path="/file_details/" element={<ProtectedRoute element={<FileDetails />} requiredRole={["ADMIN", "MAKER", "CHECKER"]} />} />
          </Route>
        </Routes>
  );
};
const AppRouter = () => (
  <AuthProvider> {/* 🔹 Đảm bảo AuthProvider bọc toàn bộ ứng dụng */}
    <Router>
      <AppRoutes />
    </Router>
  </AuthProvider>
);
export default AppRouter;
