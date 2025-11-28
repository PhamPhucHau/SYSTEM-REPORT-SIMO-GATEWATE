import { useState } from "react";
import { useAuth } from "../services/AuthContext";
import { Container, Form, Button, Card, InputGroup, Alert, Spinner } from "react-bootstrap";
import { FaUser, FaLock } from "react-icons/fa";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleLogin = async () => {
    setError(null);
    setLoading(true);

    try {
      await login(username, password); // Gọi login từ AuthContext
      navigate("/file_upload");
    } catch (err) {
      setError("Đăng nhập thất bại! Kiểm tra lại thông tin.");
      console.error("Lỗi đăng nhập:", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container fluid className="d-flex justify-content-center align-items-center vh-100 bg-dark">
      <Card className="p-4 shadow-lg rounded-lg bg-light w-100" style={{ maxWidth: "420px" }}>
        <Card.Title className="text-center mb-4 text-primary" style={{ fontSize: "1.8rem", fontWeight: "bold" }}>
          Đăng nhập
        </Card.Title>
        
        {error && <Alert variant="danger">{error}</Alert>}

        <Form>
          <Form.Group className="mb-3">
            <Form.Label>Tên đăng nhập</Form.Label>
            <InputGroup>
              <InputGroup.Text><FaUser /></InputGroup.Text>
              <Form.Control
                type="text"
                placeholder="Nhập tên đăng nhập"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </InputGroup>
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Mật khẩu</Form.Label>
            <InputGroup>
              <InputGroup.Text><FaLock /></InputGroup.Text>
              <Form.Control
                type="password"
                placeholder="Nhập mật khẩu"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </InputGroup>
          </Form.Group>

          <Button 
            variant="primary" 
            onClick={handleLogin} 
            className="w-100 py-2 fs-5 fw-bold"
            disabled={loading}
          >
            {loading ? <Spinner animation="border" size="sm" /> : "Đăng nhập"}
          </Button>
        </Form>

        <div className="text-center mt-3">
          <a href="#" className="text-decoration-none text-primary">Quên mật khẩu?</a>
        </div>
      </Card>
    </Container>
  );
};

export default Login;
