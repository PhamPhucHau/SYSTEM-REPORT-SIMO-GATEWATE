import { Link, useNavigate  } from "react-router-dom";
import "../css/Header.css"; // Add some styling
import { useAuth } from "../services/AuthContext"; // Import AuthContext

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser } from '@fortawesome/free-solid-svg-icons';
const Header = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const environmentVersion = import.meta.env.VITE_ENVIRONMENT_VERSION; // Nếu dùng Vite
  console.log(environmentVersion)
  const handleLogout = () => {
    logout();  // Xóa thông tin đăng nhập
    navigate("/"); // Điều hướng về trang đăng nhập
  };
  return (
    <header className="header">
      <h1>
      SHBVN Portal SIMO ({environmentVersion}) {/* Hiển thị phiên bản môi trường */}
    </h1>
      <nav>
        <Link to="/home">Home</Link>
        <Link to="/template">Template Manager</Link>
        <Link to="/upload">Upload Data</Link>
        <Link to="/users">User Management</Link>
        <Link to="/data_management">Data Management</Link>
        <Link to="/history">History</Link>
        <Link to="/file_upload">File Upload History</Link>
        <span className="username">
        <FontAwesomeIcon icon={faUser} style={{ color: '#FFD700', marginRight: 5 }} />
        {user.name}
      </span>
        <button onClick={handleLogout} className="logout-btn">Logout</button>
      </nav>
    </header>
  );
}     
export default Header;
