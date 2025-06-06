import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "../css/FileManagement.css";
import { useAuth } from "../services/AuthContext";

const FileManagement = () => {
  const { user } = useAuth();
  const [files, setFiles] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [selectedTemplate, setSelectedTemplate] = useState("");
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const [templates, setTemplates] = useState([]);
  const [loadingTemplates, setLoadingTemplates] = useState(false);
  const [errorTemplates, setErrorTemplates] = useState(null);
  const navigate = useNavigate();

  // --- THÊM useEffect ĐỂ GỌI API TEMPLATES ---
  useEffect(() => {
    const fetchTemplates = async () => {
      setLoadingTemplates(true);
      setErrorTemplates(null);
      try {
        const response = await axios.get(
          `${import.meta.env.VITE_SIMO_APP_API_URL}/api/templates`,
          {
            headers: {
              "Content-Type": "application/json",
              "Authorization": "Bearer " + user?.token, // Sử dụng optional chaining đề phòng user chưa có
            },
          }
        );
        setTemplates(response.data);
        if (response.data.length > 0) {
          const defaultTemplate = response.data[0];
          setSelectedTemplate(defaultTemplate);
        }
        // Không tự động chọn template đầu tiên, để người dùng chọn
      } catch (err) {
        console.error("Error fetching templates", err);
        setErrorTemplates("Không thể tải danh sách template. Vui lòng thử lại.");
      } finally {
        setLoadingTemplates(false);
      }
    };

    if (user?.token) { // Chỉ fetch khi có token
        fetchTemplates();
    } else {
        setErrorTemplates("Vui lòng đăng nhập để tải danh sách template.");
    }
  }, [user?.token]); // Dependency là user.token để fetch lại nếu token thay đổi

  
  useEffect(() => {
    console.log("Trigger useEffect, page:", page, "files.length:", files.length);
    
    if (page === 0 && (selectedTemplate || selectedDate)) {
      setFiles([]); // Reset files khi có filter mới
      setHasMore(true);
    }
  
    if (loading || (page === 0 && files.length > 0)) return;
    fetchFiles();
  }, [selectedTemplate, selectedDate, page]);

  const fetchFiles = async () => {
    if (!hasMore) return;
    setLoading(true);
    console.log("Loading:" +loading)
    setError(null);
    try {
      const response = await axios.get(
        `${import.meta.env.VITE_SIMO_APP_API_URL}/api/upload_history/list`,
        {
          params: {
            templateID: selectedTemplate || undefined,
            monthYear: selectedDate ? formattedMonthYear : undefined,
            page,
            size: 100,
          },
          headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + user.token,
          },
        }
      );
      setFiles(prevFiles => [...prevFiles, ...response.data.content]);
      setHasMore(!response.data.last);
    } catch (err) {
      setError("Lỗi khi tải danh sách file");
      console.error("Lỗi khi gọi API:", err);
    } finally {
      setLoading(false);
    }
  };
  const formattedMonthYear = selectedDate.toLocaleDateString("en-GB", {
    year: "numeric",
    month: "2-digit",
  }).split("/").join("");
  const handleTemplateSelect = (event) => {
    setSelectedTemplate(event.target.value);
    setPage(0);
    setFiles([]);
    setHasMore(true);
  };

  const handleViewDetails = (file) => {
    navigate(`/file_details/`, { state: { fileData: file} });
  };

  return (
    <div className="file-management-container">
      <h1>Quản Lý File Đã Upload</h1>
      <div className="form-group">
        <label>Chọn Template:</label>
        <select onChange={handleTemplateSelect} className="dropdown" defaultValue="">
          <option value="">-- Chọn Template --</option>
          {templates.map(template => (
            <option key={template.templateID} value={template.templateID}>
              {template.name}
            </option>
          ))}
        </select>
      </div>
      <div className="form-group">
        <label>Chọn Tháng & Năm:</label>
        <DatePicker
          selected={selectedDate}
          onChange={(date) => {
            setSelectedDate(date);
            setPage(0);
            setFiles([]);
            setHasMore(true);
          }}
          showMonthYearPicker
          dateFormat="MM/yyyy"
          dropdownMode="select"
        />
      </div>
      {loading && <p>Đang tải dữ liệu...</p>}
      {error && <p className="error">{error}</p>}
      <table className="file-table">
        <thead>
          <tr>
            <th>Tên File</th>
            <th>Template</th>
            <th>Tổng Record</th>
            <th>Ngày Upload</th>
            <th>Người Upload</th>
            <th>Hành Động</th>
          </tr>
        </thead>
        <tbody>
          {files.map((file) => (
            <tr key={file.id}>
              <td>{file.fileName}</td>
              <td>{file.templateName}</td>
              <td>{file.total_record}</td>
              <td>{file.inf_regis_dt}</td>
              <td>{file.username}</td>
              <td>
                <button onClick={() => handleViewDetails(file.data)}>Chi Tiết</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {hasMore && !loading && (
        <button onClick={() => setPage(prev => prev + 1)}>Tải Thêm</button>
      )}
    </div>
  );
};

export default FileManagement;
