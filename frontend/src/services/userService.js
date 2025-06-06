import axios from "axios";

// const API_URL = "http://localhost:8080/api/users"; // Backend Spring Boot (Commented out for mock data)

export const getUsers = async () => {
  try {
    // const response = await axios.get(API_URL); // Fetch real data (Commented out for mock data)
    return Promise.resolve([
      { id: 1, name: "Template 1", email: "template1@example.com", phone_no: "1234567890", role_id: "Admin" },
      { id: 2, name: "Template 2", email: "template2@example.com", phone_no: "0987654321", role_id: "User" }
    ]); // Mock data
  } catch (error) {
    console.error("Lỗi khi lấy dữ liệu user:", error);
    return [];
  }
};

export const createUser = async (user) => {
  try {
    console.log("Mock create user:", user);
    // await axios.post(API_URL, user); // Real API call (Commented out for mock data)
  } catch (error) {
    console.error("Lỗi khi tạo user:", error);
  }
};

export const updateUser = async (id, user) => {
  try {
    console.log("Mock update user:", id, user);
    // await axios.put(`${API_URL}/${id}`, user); // Real API call (Commented out for mock data)
  } catch (error) {
    console.error("Lỗi khi cập nhật user:", error);
  }
};

export const deleteUser = async (id) => {
  try {
    console.log("Mock delete user ID:", id);
    // await axios.delete(`${API_URL}/${id}`); // Real API call (Commented out for mock data)
  } catch (error) {
    console.error("Lỗi khi xóa user:", error);
  }
};

export const changeUserRole = async (id, newRole) => {
  try {
    console.log("Mock change user role:", id, newRole);
    // await axios.patch(`${API_URL}/${id}/role`, { role_id: newRole }); // Real API call (Commented out for mock data)
  } catch (error) {
    console.error("Lỗi khi đổi vai trò user:", error);
  }
};
