import { useEffect, useState } from "react";
import {  useNavigate  } from "react-router-dom";
import axios from "axios";
import "../css/UserManagement.css";
import { useAuth } from "../services/AuthContext";
import UserModal from "../components/UserModal";

const UserManagement = () => {
  const { user,logout } = useAuth();
  const navigate = useNavigate();
  const token = user?.token || localStorage.getItem("token");
  const [users, setUsers] = useState([]);
  const [form, setForm] = useState({ name: "", username: "", password: "",email: "", role: "", phoneNo: "" });
  const [editingUser, setEditingUser] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    if (token) fetchUsers();
  }, [token]);

  const fetchUsers = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`${import.meta.env.VITE_SIMO_APP_API_URL}/api/users`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setUsers(response.data);
    } catch (error) {
      console.error("Lỗi khi tải danh sách người dùng:", error);
      alert("Không thể tải danh sách người dùng!");
      if(error.response?.status === 401) { 
        alert("Token không hợp lệ hoặc đã hết hạn."); 
        logout();
        navigate("/"); // Điều hướng về trang đăng nhập
        
      }
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (isSubmitting) return;
    setIsSubmitting(true);

    try {
      const url = editingUser
        ? `${import.meta.env.VITE_SIMO_APP_API_URL}/api/users/${editingUser.id}`
        : `${import.meta.env.VITE_SIMO_APP_API_URL}/api/users`;
      const method = editingUser ? "put" : "post";

      await axios[method](url, form, {
        headers: { "Content-Type": "application/json", Authorization: `Bearer ${token}` }
      });

      fetchUsers();
      resetForm();
    } catch (error) {

      console.error("Lỗi khi lưu người dùng:", error);
      alert("Không thể lưu người dùng!");
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleEdit = (user) => {
    setEditingUser(user);
    setForm({ name: user.name, username: user.username, email: user.email, role: user.role, phoneNo: user.phoneNo });
    setIsModalOpen(true);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Bạn có chắc chắn muốn xóa người dùng này?")) return;
    try {
      await axios.delete(`${import.meta.env.VITE_SIMO_APP_API_URL}/api/users/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      fetchUsers();
    } catch (error) {
      console.error("Lỗi khi xóa người dùng:", error);
      alert("Không thể xóa người dùng!");
    }
  };

  const resetForm = () => {
    setForm({ name: "", username: "", password: "", email: "", role: "", phoneNo: "" });
    setEditingUser(null);
    setIsModalOpen(false);
  };

  return (
    <div className="user-management-container">
      <h1 className="title">Quản lý Người dùng</h1>
      <button className="open-modal-button" onClick={() => setIsModalOpen(true)}>Thêm Người Dùng</button>

      {loading ? (
        <p>Đang tải danh sách người dùng...</p>
      ) : users.length === 0 ? (
        <p>Không có người dùng nào.</p>
      ) : (
        <table className="user-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Username</th>
              <th>Tên</th>
              <th>Email</th>
              <th>Số ĐT</th>
              <th>Vai trò</th>
              <th>Hành động</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td>{user.username}</td>
                <td>{user.name}</td>
                <td>{user.email}</td>
                <td>{user.phoneNo}</td>
                <td>
                  <select value={user.role} onChange={(e) => handleEdit(user.id, e.target.value)}>
                    <option value="ADMIN">Admin</option>
                    <option value="USER">User</option>
                    <option value="EDITOR">Editor</option>
                  </select>
                </td>
                <td>
                  <button onClick={() => handleEdit(user)} className="edit-button">Sửa</button>
                  <button onClick={() => handleDelete(user.id)} className="delete-button">Xóa</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      <UserModal
        isOpen={isModalOpen}
        onClose={resetForm}
        form={form}
        handleChange={handleChange}
        handleSubmit={handleSubmit}
        isSubmitting={isSubmitting}
        editingUser={editingUser}
      />
    </div>
  );
};

export default UserManagement;
