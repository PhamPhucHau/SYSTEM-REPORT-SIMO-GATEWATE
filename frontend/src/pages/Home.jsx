import React, { useState, useEffect } from "react";
import { Table, Form, Button, Spinner, Alert } from "react-bootstrap";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import axios from "axios";
import { useAuth } from "../services/AuthContext";
import {  useNavigate  } from "react-router-dom";
import { formatMaYeuCau } from '../util/formatter'; // Import hàm formatMaYeuCau từ file formatter.js
import Swal from 'sweetalert2';
const DataDisplay = () => {
  const [selectedTemplate, setSelectedTemplate] = useState(null);
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [selectedRows, setSelectedRows] = useState([]);
  const [selectedDate, setSelectedDate] = useState(() => {
    const today = new Date();
    return new Date(today.getFullYear(), today.getMonth() - 1, 1); // Lùi 1 tháng
  });
  
  const [templates, setTemplates] = useState([]);
  const { user, logout } = useAuth();
  const [success, setSuccess] = useState(null);
  const navigate = useNavigate();
  useEffect(() => {
   
  
    fetchTemplates();
    console.log("Go here!");
    //fetchData(); // Gọi API lấy dữ liệu ngay khi trang tải
  }, []);
  useEffect(() => {
    if (selectedTemplate && selectedDate) {
      const formattedDate = `${selectedDate.getMonth() +1}`.padStart(2, "0") + "" + selectedDate.getFullYear()

      fetchData(selectedTemplate.templateID, formattedDate);
    }
  }, [selectedTemplate, selectedDate]);
  const fetchTemplates = async () => {
    try {
      const response = await axios.get(
                `${import.meta.env.VITE_SIMO_APP_API_URL}/api/templates`,
                {
                  headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " +user.token
      
                  },
                }
              );
      setTemplates(response.data);
      
      // Nếu có dữ liệu, tự động chọn template đầu tiên
      if (response.data.length > 0) {
        const defaultTemplate = response.data[0];
        setSelectedTemplate(defaultTemplate);
      }
    } catch (err) {
      console.error("Error fetching templates", err);
        if(err.response?.status === 401) { 
        alert("Token không hợp lệ hoặc đã hết hạn."); 
        logout();
        navigate("/"); // Điều hướng về trang đăng nhập
        
      }
    }
  };

  const fetchData = async (templateId, monthYear) => {
    setLoading(true);
    setError(null);
    try {
      const response = await axios.get(`${import.meta.env.VITE_SIMO_APP_API_URL}/api/API_1_6_TTDS_TKTT_DK/getData`, {
        params: {
          templateID: templateId,
          monthYear: monthYear
        },
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer " + user.token
        }
      });
      setData(response.data.map(item => ({ ...item, selected: false })));
    } catch (err) {
      setError("Không thể lấy dữ liệu");
    } finally {
      setLoading(false);
    }
  };

  const handleTemplateChange = async  (event) => {
    const template = templates.find(t => t.id === event.target.value);
    setSelectedTemplate(template);
    setSelectedRows([]);
    //setData(data.map(row => ({ ...row, selected: false })));
    setData([]);
    if (template && selectedDate) {
      const formattedDate = `${selectedDate.getMonth() + 1}`.padStart(2, "0") + "" + selectedDate.getFullYear();
      await fetchData(template.templateID, formattedDate);
    }
  };

  const handleRowSelect = (index, isChecked) => {
    const updatedData = [...data];
    updatedData[index].selected = isChecked;
    setData(updatedData);
    setSelectedRows(updatedData.filter(row => row.selected).map((_, i) => i));
  };

  const handleSelectAll = (isChecked) => {
    const updatedData = data.map(row => ({ ...row, selected: isChecked }));
    setData(updatedData);
    setSelectedRows(isChecked ? updatedData.map((_, i) => i) : []);
  };

  const handleSubmit = async () => {
    setLoading(true);
    setError(null);
    setSuccess(null);

    const selectedData = data.filter((row) => row.selected);
    if (selectedData.length === 0) {
      setError("Vui lòng chọn ít nhất một dòng dữ liệu để gửi");
      setLoading(false);
      return;
    }

    // Ánh xạ dữ liệu sang định dạng TKTTRequestDTO
    // const tkttData = selectedData.map((row) => ({
    //   cif: row.cif,
    //   soID: row.soID,
    //   loaiID: row.loaiID,
    //   tenKhachHang: row.tenKhachHang,
    //   ngaySinh: row.ngaySinh,
    //   gioiTinh: row.gioiTinh,
    //   maSoThue: row.maSoThue,
    //   soDienThoaiDangKyDichVu: row.soDienThoaiDangKyDichVu,
    //   diaChi: row.diaChi,
    //   diaChiKiemSoatTruyCap: row.diaChiKiemSoatTruyCap,
    //   maSoNhanDangThietBiDiDong: row.maSoNhanDangThietBiDiDong,
    //   soTaiKhoan: row.soTaiKhoan,
    //   loaiTaiKhoan: row.loaiTaiKhoan,
    //   trangThaiHoatDongTaiKhoan: row.trangThaiHoatDongTaiKhoan,
    //   ngayMoTaiKhoan: row.ngayMoTaiKhoan,
    //   phuongThucMoTaiKhoan: row.phuongThucMoTaiKhoan,
    //   ngayXacThucTaiQuay: row.ngayXacThucTaiQuay,
    //   quocTich: row.quocTich,
    // }));
    const tkttData = selectedData ; 
    // Tạo maYeuCau (ví dụ: dùng timestamp)
    const maYeuCau = `REQ_${formatMaYeuCau()}`;

    // Tạo kyBaoCao từ selectedDate (mm/yyyy)
    const kyBaoCao = `${(selectedDate.getMonth() + 1).toString().padStart(2, "0")}/${selectedDate.getFullYear()}`;
    console.log(kyBaoCao);
    //const kyBaoCao = `${(selectedDate.getMonth() + 1).toString().padStart(2, "0")}/${selectedDate.getFullYear()}`;


    try {
      const response = await axios.post(
        `${import.meta.env.VITE_SIMO_APP_API_URL}/api/simo/tktt/upload?templateID=${selectedTemplate.templateID}`,
        tkttData,
        {
          headers: {
            "Content-Type": "application/json",
            "maYeuCau": maYeuCau,
            "kyBaoCao": kyBaoCao,
            "Authorization": "Bearer " + user.token, // Nếu backend yêu cầu token
          },
        }
      );

      if (response.data.success) {
        setSuccess(`Dữ liệu đã được gửi Code: ${response.data.code} message` );
        // Reset selected rows
        setData(data.map((row) => ({ ...row, selected: false })));
        setSelectedRows([]);
        Swal.fire({
          icon: 'success',
          title: 'Thành công!',
          text: `Dữ liệu đã được gửi Code: ${response.data.code} message`,
          confirmButtonText: 'OK'
        });
        
      } else {
        setError(
          `Gửi dữ liệu thất bại: ${response.data.message || "Lỗi không xác định"}`
        );
        Swal.fire({
          icon: 'error',
          title: 'Lỗi!',
          text: `Gửi dữ liệu thất bại: ${response.data.message || "Lỗi không xác định"}`,
          confirmButtonText: 'Đóng'
        });
      }
    } catch (err) {
      setError(
        `Lỗi khi gửi dữ liệu: ${
          err.response?.data?.message || err.message || "Lỗi không xác định"
        }`
      );
    Swal.fire({
      icon: 'error',
      title: 'Lỗi!',
      text: 'Xác nhận thất bại. Vui lòng kiểm tra lại.',
      confirmButtonText: 'Đóng'
    });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="data-display-container">
      <h2>Hiển Thị Dữ Liệu</h2>
      <div className="filter-container">
        <div className="filter-group">
          <label>Chọn Template:</label>
          <select onChange={handleTemplateChange} value={selectedTemplate?.id || ""}>
            <option value="">-- Chọn Template --</option>
            {templates.map(template => (
              <option key={template.id} value={template.id}>{template.name}</option>
            ))}
          </select>
        </div>

        <div className="filter-group">
          <label>Chọn Tháng & Năm:</label>
          <DatePicker
            selected={selectedDate}
            onChange={(date) => setSelectedDate(date)}
            showMonthYearPicker
            dateFormat="MM/yyyy"
            monthFormat="MM"
            dropdownMode="select"
            className="custom-datepicker"
          />
        </div>
      </div>
      {selectedTemplate && (
  <div className="d-flex justify-content-between align-items-center my-3">
    <div>Đã chọn: <strong>{selectedRows.length}</strong> dòng</div>
    <Button onClick={handleSubmit} className="submit-btn" disabled={user?.role !== "CHECKER"}> Duyệt gửi BC </Button>
  </div>
)}     
      {loading && <Spinner animation="border" />} 
      {error && <Alert variant="danger">{error}</Alert>}
      {success && <Alert variant="success">{success}</Alert>}
      {selectedTemplate && (
        <div className="table-container">
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>
                  <input 
                    type="checkbox" 
                    onChange={(e) => handleSelectAll(e.target.checked)} 
                    checked={data.length > 0 && data.every(row => row.selected)}
                  />
                </th>
                {Object.keys(JSON.parse(selectedTemplate.schemaJson.replace(/^'/, '').replace(/'$/, ''))).map((key) => (
                  <th key={key}>{key}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {data.map((row, index) => (
                <tr key={index}>
                  <td>
                    <input 
                      type="checkbox" 
                      checked={!!row.selected} 
                      onChange={(e) => handleRowSelect(index, e.target.checked)}
                    />
                  </td>
                  {Object.values(row).map((value, colIndex) => (
                    <td key={colIndex}>{value}</td>
                  ))}
                </tr>
              ))}
            </tbody>
          </Table>
          <div className="d-flex justify-content-between align-items-center my-2">
  <div>Đã chọn: <strong>{selectedRows.length}</strong> dòng</div>
  <Button onClick={handleSubmit} className= "submit-btn" disabled={user?.role !== "CHECKER"}> Duyệt gửi BC </Button>
  </div>
        </div>
      )}
    </div>
  );
};

export default DataDisplay;
