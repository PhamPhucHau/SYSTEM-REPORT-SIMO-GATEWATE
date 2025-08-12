import React, { useState, useEffect } from "react";
import { Table, Form, Button, Spinner, Alert, Card, Row, Col } from "react-bootstrap";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import axios from "axios";
import { useAuth } from "../services/AuthContext";
import { useNavigate } from "react-router-dom";
import Swal from 'sweetalert2';

const HistoryQuery = () => {
  const [auditLogs, setAuditLogs] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  
  // Filter states
  const [username, setUsername] = useState("");
  const [actionType, setActionType] = useState("");
  const [resourceType, setResourceType] = useState("");
  const [startDate, setStartDate] = useState(() => {
    const today = new Date();
    return new Date(today.getFullYear(), today.getMonth() - 1, 1); // Lùi 1 tháng
  });
  const [endDate, setEndDate] = useState(new Date());
  const [searchTerm, setSearchTerm] = useState("");
  
  // Pagination
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(20);
  const [totalLogs, setTotalLogs] = useState(0);
  
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  // Action types for filter
  const actionTypes = [
    "LOGIN", "LOGOUT", "CREATE", "UPDATE", "DELETE", "UPLOAD", 
    "DOWNLOAD", "VIEW", "API_CALL", "EXPORT", "IMPORT"
  ];

  // Resource types for filter
  const resourceTypes = [
    "USER", "TEMPLATE", "FILE", "API", "AUTH", "REPORT", "SYSTEM"
  ];

  useEffect(() => {
    if (user?.role === "ADMIN") {
      fetchAuditLogs();
    }
  }, [currentPage, pageSize]);

  const fetchAuditLogs = async () => {
    setLoading(true);
    setError(null);
    
    try {
      const params = {
        page: currentPage,
        size: pageSize,
        username: username || undefined,
        actionType: actionType || undefined,
        resourceType: resourceType || undefined,
        startDate: startDate ? startDate.toISOString() : undefined,
        endDate: endDate ? endDate.toISOString() : undefined,
        searchTerm: searchTerm || undefined
      };

      // Remove undefined params
      Object.keys(params).forEach(key => 
        params[key] === undefined && delete params[key]
      );

      const response = await axios.get(
        `${import.meta.env.VITE_SIMO_APP_API_URL}/api/audit-logs/search`,
        {
          params,
          headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + user.token
          },
        }
      );
      
      setAuditLogs(response.data.content || response.data);
      setTotalLogs(response.data.totalElements || response.data.length);
      
    } catch (err) {
      console.error("Error fetching audit logs:", err);
      if (err.response?.status === 401) {
        Swal.fire({
          icon: 'error',
          title: 'Phiên đăng nhập hết hạn',
          text: 'Vui lòng đăng nhập lại',
          confirmButtonText: 'OK'
        });
        logout();
        navigate("/");
      } else {
        setError("Không thể tải dữ liệu audit log");
      }
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = () => {
    setCurrentPage(0);
    fetchAuditLogs();
  };

  const handleReset = () => {
    setUsername("");
    setActionType("");
    setResourceType("");
    setStartDate(new Date(new Date().getFullYear(), new Date().getMonth() - 1, 1));
    setEndDate(new Date());
    setSearchTerm("");
    setCurrentPage(0);
    fetchAuditLogs();
  };

  const handleExport = async () => {
    try {
      const params = {
        username: username || undefined,
        actionType: actionType || undefined,
        resourceType: resourceType || undefined,
        startDate: startDate ? startDate.toISOString() : undefined,
        endDate: endDate ? endDate.toISOString() : undefined,
        searchTerm: searchTerm || undefined
      };

      Object.keys(params).forEach(key => 
        params[key] === undefined && delete params[key]
      );

      const response = await axios.get(
        `${import.meta.env.VITE_SIMO_APP_API_URL}/api/audit-logs/export`,
        {
          params,
          headers: {
            "Authorization": "Bearer " + user.token
          },
          responseType: 'blob'
        }
      );

      // Create download link
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `audit-logs-${new Date().toISOString().split('T')[0]}.csv`);
      document.body.appendChild(link);
      link.click();
      link.remove();
      window.URL.revokeObjectURL(url);

      Swal.fire({
        icon: 'success',
        title: 'Thành công!',
        text: 'Đã xuất dữ liệu audit log',
        confirmButtonText: 'OK'
      });

    } catch (err) {
      console.error("Error exporting logs:", err);
      Swal.fire({
        icon: 'error',
        title: 'Lỗi!',
        text: 'Không thể xuất dữ liệu',
        confirmButtonText: 'Đóng'
      });
    }
  };

  const formatTimestamp = (timestamp) => {
    if (!timestamp) return "";
    const date = new Date(timestamp);
    return date.toLocaleString('vi-VN');
  };

  const getActionTypeColor = (actionType) => {
    const colors = {
      'LOGIN': 'success',
      'LOGOUT': 'secondary',
      'CREATE': 'primary',
      'UPDATE': 'warning',
      'DELETE': 'danger',
      'UPLOAD': 'info',
      'DOWNLOAD': 'info',
      'VIEW': 'secondary',
      'API_CALL': 'primary'
    };
    return colors[actionType] || 'secondary';
  };

  const getResourceTypeColor = (resourceType) => {
    const colors = {
      'USER': 'primary',
      'TEMPLATE': 'success',
      'FILE': 'info',
      'API': 'warning',
      'AUTH': 'danger',
      'REPORT': 'secondary',
      'SYSTEM': 'dark'
    };
    return colors[resourceType] || 'secondary';
  };

  // Check if user is admin
  if (user?.role !== "ADMIN") {
    return (
      <div className="data-display-container">
        <Alert variant="warning">
          <h4>Quyền truy cập bị từ chối</h4>
          <p>Chỉ có quyền ADMIN mới có thể xem Audit Log.</p>
        </Alert>
      </div>
    );
  }

  return (
    <div className="data-display-container">
      <h2>Audit Log Monitor</h2>
      
      {/* Filter Section */}
      <Card className="mb-4">
        <Card.Header>
          <h5>Bộ lọc tìm kiếm</h5>
        </Card.Header>
        <Card.Body>
          <Row>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Username:</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Nhập username"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                />
              </Form.Group>
            </Col>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Loại hành động:</Form.Label>
                <Form.Select
                  value={actionType}
                  onChange={(e) => setActionType(e.target.value)}
                >
                  <option value="">Tất cả</option>
                  {actionTypes.map(type => (
                    <option key={type} value={type}>{type}</option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Loại tài nguyên:</Form.Label>
                <Form.Select
                  value={resourceType}
                  onChange={(e) => setResourceType(e.target.value)}
                >
                  <option value="">Tất cả</option>
                  {resourceTypes.map(type => (
                    <option key={type} value={type}>{type}</option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Từ khóa tìm kiếm:</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Tìm kiếm theo mô tả..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
              </Form.Group>
            </Col>
          </Row>
          
          <Row>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Từ ngày:</Form.Label>
                <DatePicker
                  selected={startDate}
                  onChange={(date) => setStartDate(date)}
                  showTimeSelect
                  timeFormat="HH:mm"
                  timeIntervals={15}
                  dateFormat="dd/MM/yyyy HH:mm"
                  className="form-control"
                />
              </Form.Group>
            </Col>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Đến ngày:</Form.Label>
                <DatePicker
                  selected={endDate}
                  onChange={(date) => setEndDate(date)}
                  showTimeSelect
                  timeFormat="HH:mm"
                  timeIntervals={15}
                  dateFormat="dd/MM/yyyy HH:mm"
                  className="form-control"
                />
              </Form.Group>
            </Col>
            <Col md={6} className="d-flex align-items-end">
              <Button 
                variant="primary" 
                onClick={handleSearch} 
                className="me-2"
                disabled={loading}
              >
                {loading ? <Spinner animation="border" size="sm" /> : "Tìm kiếm"}
              </Button>
              <Button 
                variant="secondary" 
                onClick={handleReset}
                className="me-2"
              >
                Làm mới
              </Button>
              <Button 
                variant="success" 
                onClick={handleExport}
                disabled={loading}
              >
                Xuất dữ liệu
              </Button>
            </Col>
          </Row>
        </Card.Body>
      </Card>

      {/* Results Section */}
      <Card>
        <Card.Header>
          <div className="d-flex justify-content-between align-items-center">
            <h5>Kết quả tìm kiếm</h5>
            <div>
              Tổng số: <strong>{totalLogs}</strong> bản ghi
            </div>
          </div>
        </Card.Header>
        <Card.Body>
          {loading && (
            <div className="text-center my-4">
              <Spinner animation="border" />
              <p>Đang tải dữ liệu...</p>
            </div>
          )}
          
          {error && <Alert variant="danger">{error}</Alert>}
          {success && <Alert variant="success">{success}</Alert>}
          
          {!loading && auditLogs.length > 0 && (
            <div className="table-responsive">
              <Table striped bordered hover>
                <thead>
                  <tr>
                    <th>Thời gian</th>
                    <th>Username</th>
                    <th>Vai trò</th>
                    <th>Hành động</th>
                    <th>Tài nguyên</th>
                    <th>Mô tả</th>
                    <th>Endpoint</th>
                    <th>IP Address</th>
                    <th>Trạng thái</th>
                  </tr>
                </thead>
                <tbody>
                  {auditLogs.map((log, index) => (
                    <tr key={log.id || index}>
                      <td>{formatTimestamp(log.timestamp)}</td>
                      <td>
                        <strong>{log.username || 'N/A'}</strong>
                      </td>
                      <td>
                        <span className={`badge bg-${getResourceTypeColor(log.userRole)}`}>
                          {log.userRole || 'N/A'}
                        </span>
                      </td>
                      <td>
                        <span className={`badge bg-${getActionTypeColor(log.actionType)}`}>
                          {log.actionType || 'N/A'}
                        </span>
                      </td>
                      <td>
                        <span className={`badge bg-${getResourceTypeColor(log.resourceType)}`}>
                          {log.resourceType || 'N/A'}
                        </span>
                        {log.resourceId && (
                          <small className="d-block text-muted">ID: {log.resourceId}</small>
                        )}
                      </td>
                      <td>
                        <div style={{ maxWidth: '200px', wordWrap: 'break-word' }}>
                          {log.description || 'N/A'}
                        </div>
                      </td>
                      <td>
                        <small className="text-muted">
                          {log.httpMethod} {log.endpoint}
                        </small>
                      </td>
                      <td>
                        <code>{log.ipAddress || 'N/A'}</code>
                      </td>
                      <td>
                        {log.responseStatus ? (
                          <span className={`badge bg-${log.responseStatus.startsWith('2') ? 'success' : 'danger'}`}>
                            {log.responseStatus}
                          </span>
                        ) : (
                          <span className="badge bg-secondary">N/A</span>
                        )}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </div>
          )}
          
          {!loading && auditLogs.length === 0 && (
            <Alert variant="info">
              Không có dữ liệu audit log nào được tìm thấy.
            </Alert>
          )}
          
          {/* Pagination */}
          {totalLogs > pageSize && (
            <div className="d-flex justify-content-between align-items-center mt-3">
              <div>
                Hiển thị {currentPage * pageSize + 1} - {Math.min((currentPage + 1) * pageSize, totalLogs)} 
                trong tổng số {totalLogs} bản ghi
              </div>
              <div>
                <Button
                  variant="outline-primary"
                  size="sm"
                  onClick={() => setCurrentPage(Math.max(0, currentPage - 1))}
                  disabled={currentPage === 0}
                  className="me-2"
                >
                  Trước
                </Button>
                <span className="mx-2">Trang {currentPage + 1}</span>
                <Button
                  variant="outline-primary"
                  size="sm"
                  onClick={() => setCurrentPage(currentPage + 1)}
                  disabled={(currentPage + 1) * pageSize >= totalLogs}
                >
                  Sau
                </Button>
              </div>
            </div>
          )}
        </Card.Body>
      </Card>
    </div>
  );
};

export default HistoryQuery;