import React, { useState, useEffect } from "react";
import { Table, Form, Button, Spinner, Alert, Card, Row, Col } from "react-bootstrap";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import axios from "axios";
import { useAuth } from "../services/AuthContext";
import Swal from 'sweetalert2';
import "../css/RequestManagement.css";

const RequestManagement = () => {
  const { user, logout } = useAuth();
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Filters
  const [templateID, setTemplateID] = useState("");
  const [actionType, setActionType] = useState("");
  const [username, setUsername] = useState("");
  const [searchTerm, setSearchTerm] = useState(""); // filter description/filename client-side
  const [startDate, setStartDate] = useState(() => {
    const today = new Date();
    return new Date(today.getFullYear(), today.getMonth(), 1);
  });
  const [endDate, setEndDate] = useState(new Date());

  // Action types
  const actionTypes = ["UPLOAD", "CONFIRM", "SEND", "ROLLBACK"];

  useEffect(() => {
    if (user?.token) {
      fetchData();
    }
  }, []);

  const fetchData = async () => {
    setLoading(true);
    setError(null);
    try {
      const params = {
        start: startDate ? startDate.toISOString() : undefined,
        end: endDate ? endDate.toISOString() : undefined,
        templateID: templateID || undefined,
        actionType: actionType || undefined,
      };
      Object.keys(params).forEach(key => params[key] === undefined && delete params[key]);

      const resp = await axios.get(
        `${import.meta.env.VITE_SIMO_APP_API_URL}/api/upload-management/search`,
        {
          params,
          headers: {
            "Authorization": "Bearer " + user.token,
          },
        }
      );

      let data = resp.data || [];
      // Optional client-side filter by username and description/fileName
      if (username) {
        data = data.filter(i => (i.username || "").toLowerCase().includes(username.toLowerCase()));
      }
      if (searchTerm) {
        const q = searchTerm.toLowerCase();
        data = data.filter(i =>
          (i.description || "").toLowerCase().includes(q) ||
          (i.fileName || "").toLowerCase().includes(q)
        );
      }
      setItems(data);
    } catch (err) {
      console.error("Error fetching upload management:", err);
      if (err.response?.status === 401) {
        Swal.fire({ icon: 'error', title: 'Phiên hết hạn', text: 'Vui lòng đăng nhập lại' });
        logout();
      } else {
        setError("Không thể tải dữ liệu quản lý yêu cầu");
      }
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = () => {
    fetchData();
  };

  const handleReset = () => {
    setTemplateID("");
    setActionType("");
    setUsername("");
    setSearchTerm("");
    const today = new Date();
    setStartDate(new Date(today.getFullYear(), today.getMonth(), 1));
    setEndDate(new Date());
    fetchData();
  };

  const formatTimestamp = (ts) => {
    if (!ts) return "";
    const d = new Date(ts);
    return d.toLocaleString('vi-VN');
  };

  if (!user?.token) {
    return (
      <div className="data-display-container">
        <Alert variant="warning">Vui lòng đăng nhập để xem dữ liệu</Alert>
      </div>
    );
  }

  return (
    <div className="data-display-container">
      <h2>Quản lý yêu cầu (Upload/Confirm/Send)</h2>

      <Card className="mb-4">
        <Card.Header>
          <h5>Bộ lọc tìm kiếm</h5>
        </Card.Header>
        <Card.Body>
          <Row>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Template ID</Form.Label>
                <Form.Control
                  type="text"
                  value={templateID}
                  onChange={(e) => setTemplateID(e.target.value)}
                  placeholder="VD: API_1_31_TT_TNH"
                />
              </Form.Group>
            </Col>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Loại hành động</Form.Label>
                <Form.Select value={actionType} onChange={(e) => setActionType(e.target.value)}>
                  <option value="">Tất cả</option>
                  {actionTypes.map(t => (
                    <option key={t} value={t}>{t}</option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Username</Form.Label>
                <Form.Control
                  type="text"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  placeholder="Nhập username"
                />
              </Form.Group>
            </Col>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Từ khóa (mô tả/tên file)</Form.Label>
                <Form.Control
                  type="text"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  placeholder="VD: Send to SIMO"
                />
              </Form.Group>
            </Col>
          </Row>
          <Row>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Từ ngày</Form.Label>
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
                <Form.Label>Đến ngày</Form.Label>
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
              <Button variant="primary" onClick={handleSearch} className="me-2" disabled={loading}>
                {loading ? <Spinner animation="border" size="sm" /> : "Tìm kiếm"}
              </Button>
              <Button variant="secondary" onClick={handleReset}>Làm mới</Button>
            </Col>
          </Row>
        </Card.Body>
      </Card>

      <Card>
        <Card.Header>
          <div className="d-flex justify-content-between align-items-center">
            <h5>Kết quả</h5>
            <div>Tổng số: <strong>{items.length}</strong> bản ghi</div>
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

          {!loading && items.length > 0 && (
            <div className="table-responsive">
              <Table striped bordered hover>
                <thead>
                  <tr>
                    <th>Thời gian</th>
                    <th>Username</th>
                    <th>Vai trò</th>
                    <th>Template ID</th>
                    <th>Kỳ báo cáo</th>
                    <th>Hành động</th>
                    <th>File</th>
                    <th>Trạng thái</th>
                    <th>Mô tả</th>
                  </tr>
                </thead>
                <tbody>
                  {items.map((it, idx) => (
                    <tr key={it.id || idx}>
                      <td>{formatTimestamp(it.timestamp)}</td>
                      <td>{it.username || 'N/A'}</td>
                      <td>{it.userRole || 'N/A'}</td>
                      <td>{it.templateID || 'N/A'}</td>
                      <td>{it.monthYear || 'N/A'}</td>
                      <td>{it.actionType || 'N/A'}</td>
                      <td>{it.fileName || 'N/A'}</td>
                      <td>{(it.statusBefore || '--') + ' → ' + (it.statusAfter || '--')}</td>
                      <td style={{ maxWidth: '300px', wordWrap: 'break-word' }}>{it.description || 'N/A'}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </div>
          )}

          {!loading && items.length === 0 && (
            <Alert variant="info">Không có dữ liệu</Alert>
          )}
        </Card.Body>
      </Card>
    </div>
  );
};

export default RequestManagement;


