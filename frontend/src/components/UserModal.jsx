import React, { useEffect } from "react";

const UserModal = ({ isOpen, onClose, form, handleChange, handleSubmit, isSubmitting, editingUser }) => {
  if (!isOpen) return null;

  useEffect(() => {
    const handleOutsideClick = (event) => {
      if (event.target.classList.contains("modal-overlay")) {
        onClose();
      }
    };
    document.addEventListener("mousedown", handleOutsideClick);
    return () => {
      document.removeEventListener("mousedown", handleOutsideClick);
    };
  }, [onClose]);

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <span className="close-button" onClick={onClose}>&times;</span>
        <h2>{editingUser ? "Chỉnh sửa Người dùng" : "Thêm Người dùng"}</h2>
        <form onSubmit={handleSubmit}>
          <input type="text" name="name" placeholder="Tên" value={form.name} onChange={handleChange} required />
          <input type="text" name="username" placeholder="Username" value={form.username} onChange={handleChange} required />
          <input type="text" name="password" placeholder="Password" value={form.password} onChange={handleChange} required />
          <input type="email" name="email" placeholder="Email" value={form.email} onChange={handleChange} required />
          <input type="text" name="phoneNo" placeholder="Số điện thoại" value={form.phoneNo} onChange={handleChange} required />
          <select name="role" value={form.role} onChange={handleChange} required>
            <option value="">Chọn vai trò</option>
            <option value="ADMIN">Admin</option>
            <option value="USER">User</option>
            <option value="EDITOR">Editor</option>
          </select>
          <button type="submit" disabled={isSubmitting}>
            {isSubmitting ? "Đang xử lý..." : editingUser ? "Cập nhật" : "Thêm mới"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default UserModal;
