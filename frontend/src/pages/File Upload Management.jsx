import { useState, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "../css/FileManagement.css";
import { useAuth } from "../services/AuthContext";
import Swal from 'sweetalert2';

const FileManagement = () => {
  const { user } = useAuth();
  const navigate = useNavigate();

  // --- State Variables ---
  const [files, setFiles] = useState([]);
  const [loading, setLoading] = useState(false); // Default to false, set to true when fetching
  const [error, setError] = useState(null);
  const [selectedTemplate, setSelectedTemplate] = useState("");
  const [selectedDate, setSelectedDate] = useState(() => {
    const today = new Date();
    // Initialize to the 1st day of the previous month for better UX
    return new Date(today.getFullYear(), today.getMonth() - 1, 1);
  });
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const [templates, setTemplates] = useState([]);
  const [loadingTemplates, setLoadingTemplates] = useState(false);
  const [errorTemplates, setErrorTemplates] = useState(null);

  // --- Helper to format date for API ---
  const formattedMonthYear = selectedDate.toLocaleDateString("en-GB", {
    year: "numeric",
    month: "2-digit",
  }).split("/").join("");

  // --- Fetch Templates ---
  const fetchTemplates = useCallback(async () => {
    if (!user?.token) {
      setErrorTemplates("Vui lòng đăng nhập để tải danh sách template.");
      return;
    }

    setLoadingTemplates(true);
    setErrorTemplates(null);
    try {
      const response = await axios.get(
        `${import.meta.env.VITE_SIMO_APP_API_URL}/api/templates`,
        {
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${user.token}`,
          },
        }
      );
      setTemplates(response.data);
      // Automatically select the first template if available and no template is selected yet
      if (response.data.length > 0 && !selectedTemplate) {
        setSelectedTemplate(response.data[0].templateID);
      }
    } catch (err) {
      console.error("Lỗi khi tải danh sách template:", err);
      setErrorTemplates("Không thể tải danh sách template. Vui lòng thử lại.");
    } finally {
      setLoadingTemplates(false);
    }
  }, [user?.token, selectedTemplate]); // Add selectedTemplate to dependency to prevent re-fetching if already set

  // --- Fetch Files ---
  const fetchFiles = useCallback(async () => {
    // Only fetch if a template is selected and there's more data to load
    if (!selectedTemplate || !hasMore || loading) {
      console.log("Skipping fetchFiles:", { selectedTemplate, hasMore, loading });
      return;
    }
    
    setLoading(true);
    setError(null);
    try {
      const response = await axios.get(
        `${import.meta.env.VITE_SIMO_APP_API_URL}/api/upload_history/list`,
        {
          params: {
            templateID: selectedTemplate,
            monthYear: formattedMonthYear, // Always send formattedMonthYear
            username: user.role === "CHECKER" ? null:user?.name,
            page,
            size: 100,
          },
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${user?.token}`,
          },
        }
      );
      setFiles(prevFiles => page === 0 ? response.data.content : [...prevFiles, ...response.data.content]);
      setHasMore(!response.data.last);
    } catch (err) {
      setError("Lỗi khi tải danh sách file.");
      console.error("Lỗi khi gọi API danh sách file:", err);
    } finally {
      setLoading(false);
    }
  }, [selectedTemplate, formattedMonthYear, user?.name, user?.token, page, hasMore, loading]);

  // --- useEffect Hooks ---

  // Initial fetch for templates
  useEffect(() => {
    fetchTemplates();
  }, [fetchTemplates]);

  // Reset page and files when filters change
  useEffect(() => {
    // Only reset if templates have been loaded and a template is selected
    if (!loadingTemplates && selectedTemplate) {
      setFiles([]);
      setPage(0);
      setHasMore(true);
    }
  }, [selectedTemplate, selectedDate, loadingTemplates]);

  // Fetch files when page or filters change
  useEffect(() => {
    // Prevent fetching if templates are still loading or no template is selected
    if (loadingTemplates || !selectedTemplate) {
      return;
    }
    fetchFiles();
  }, [page, selectedTemplate, selectedDate, fetchFiles, loadingTemplates]);

  // --- Event Handlers ---

  const handleTemplateSelect = (event) => {
    setSelectedTemplate(event.target.value);
  };

  const handleDateChange = (date) => {
    setSelectedDate(date);
  };

  const handleViewDetails = (fileData) => {
    navigate(`/file_details/`, { state: { fileData } });
  };

  const handleConfirm = async (file_id) => {
    Swal.fire({
      title: 'Bạn có chắc chắn muốn xác nhận?',
      text: "Hành động này không thể hoàn tác!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Xác nhận!',
      cancelButtonText: 'Hủy bỏ'
    }).then(async (result) => {
      if (result.isConfirmed) {
        setLoading(true); // Indicate loading state
        try {
          const response = await axios.post(
            `${import.meta.env.VITE_SIMO_APP_API_URL}/api/upload_history/confirm?id=${file_id}`,
            null, // No request body
            {
              headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${user?.token}`,
                'X-Username': user?.name,
                'X-User-Role': user?.role,
                'X-Template-ID': selectedTemplate.templateID,
                'X-Month-Year': `${selectedDate.getMonth() +1}`.padStart(2, "0") + "" + selectedDate.getFullYear(),
                'X-Request-Id': crypto.randomUUID(),         // Sinh ID ngẫu nhiên
                'X-Correlation-Id': Date.now().toString(), 
              },
            }
          );
          console.log("Phản hồi từ server:", response.data);
          Swal.fire({
            icon: 'success',
            title: 'Thành công!',
            text: 'Xác nhận tải lên thành công.',
            confirmButtonText: 'OK'
          });
          // Refresh the file list after successful confirmation
          setFiles([]);
          setPage(0);
          setHasMore(true);
          fetchFiles();
        } catch (error) {
          setError("Lỗi khi xác nhận file.");
          console.error("Lỗi khi gọi API xác nhận:", error);
          Swal.fire({
            icon: 'error',
            title: 'Lỗi!',
            text: `Xác nhận thất bại. Vui lòng kiểm tra lại. Chi tiết lỗi: ${error.response?.data?.message || error.message}`,
            confirmButtonText: 'Đóng'
          });
        } finally {
          setLoading(false);
        }
      }
    });
  };

  return (
    <div className="file-management-container">
      <h1>Quản Lý File Đã Upload</h1>

      <div className="filters-container">
        <div className="form-group">
          <label htmlFor="template-select">Chọn Template:</label>
          {loadingTemplates ? (
            <p>Đang tải template...</p>
          ) : errorTemplates ? (
            <p className="error">{errorTemplates}</p>
          ) : (
            <select
              id="template-select"
              onChange={handleTemplateSelect}
              className="dropdown"
              value={selectedTemplate}
            >
              <option value="">-- Chọn Template --</option>
              {templates.map(template => (
                <option key={template.templateID} value={template.templateID}>
                  {template.name}
                </option>
              ))}
            </select>
          )}
        </div>

        <div className="form-group">
          <label htmlFor="date-picker">Chọn Tháng & Năm:</label>
          <DatePicker
            id="date-picker"
            selected={selectedDate}
            onChange={handleDateChange}
            showMonthYearPicker
            dateFormat="MM/yyyy"
            dropdownMode="select"
            className="date-picker"
          />
        </div>
      </div>

      {loading && files.length === 0 && <p className="loading-message">Đang tải dữ liệu...</p>}
      {error && <p className="error-message">{error}</p>}

      {files.length > 0 ? (
        <table className="file-table">
          <thead>
            <tr>
              <th>Tên File</th>
              <th>Template</th>
              <th>Tổng Record</th>
              <th>Ngày Upload</th>
              <th>Người Upload</th>
              <th>Trạng Thái</th>
              <th>Chi Tiết</th>
              <th>Xác Nhận</th>
            </tr>
          </thead>
          <tbody>
            {files.map(file => {
              const canConfirm = (file.data_ledg_s === "00" && file.username === user.name) ; // Assuming "00" means pending confirmation

              return (
                <tr key={file.id}>
                  <td>{file.fileName}</td>
                  <td>{file.templateName}</td>
                  <td>{file.total_record}</td>
                  <td>{file.inf_regis_dt}</td>
                  <td>{file.username}</td>
                  <td>{file.data_ledg_s}</td>
                  <td>
                    <button
                      onClick={() => handleViewDetails(file.data)}
                      className="btn-view-details"
                    >
                      Chi Tiết
                    </button>
                  </td>
                  <td>
                    <button
                      disabled={!canConfirm || loading} // Disable if already loading
                      onClick={() => handleConfirm(file.id)}
                      className={`btn-confirm ${!canConfirm || loading ? "disabled" : ""}`}
                    >
                      {loading && canConfirm ? "Đang xử lý..." : "Xác Nhận"}
                    </button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      ) : (
        !loading && !error && <p className="no-data-message">Không có file nào được tìm thấy với các tiêu chí đã chọn.</p>
      )}

      {hasMore && !loading && files.length > 0 && (
        <button onClick={() => setPage(prev => prev + 1)} className="btn-load-more">
          Tải Thêm
        </button>
      )}
      {loading && files.length > 0 && <p className="loading-message">Đang tải thêm...</p>}
    </div>
  );
};

export default FileManagement;