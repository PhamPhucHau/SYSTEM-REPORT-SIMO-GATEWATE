import { Link, useNavigate  } from "react-router-dom";
import "../css/Header.css"; // Add some styling
import { useAuth } from "../services/AuthContext"; // Import AuthContext
const Header = () => {
  const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();  // Xóa thông tin đăng nhập
    navigate("/"); // Điều hướng về trang đăng nhập
  };
  return (
    <header className="header">
      <h1>
      SHBVN Portal SIMO
    </h1>
      <nav>
        <Link to="/home">Home</Link>
        <Link to="/template">Template Manager</Link>
        <Link to="/upload">Upload Data</Link>
        <Link to="/users">User Management</Link>
        <Link to="/history">History</Link>
        <Link to="/file_upload">File UploadUpload</Link>
        <button onClick={handleLogout} className="logout-btn">Logout</button>
      </nav>
    </header>
  );
}     
export default Header;
