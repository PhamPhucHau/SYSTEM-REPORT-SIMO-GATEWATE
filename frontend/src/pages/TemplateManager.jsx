import { useState, useEffect, useRef } from "react";
import "../css/TemplateManager.css";
import axios from "axios";
import { useAuth } from "../services/AuthContext";
import Modal from 'react-modal';

const TemplateManager = () => {
  const { user } = useAuth();
  const [templates, setTemplates] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [editModalOpen, setEditModalOpen] = useState(false);
  const [addModalOpen, setAddModalOpen] = useState(false);
  const [selectedTemplate, setSelectedTemplate] = useState(null);
  const [newTemplate, setNewTemplate] = useState({
    templateID: "",
    name: "",
    schemaJson: "{}"
  });
  const [newField, setNewField] = useState({ name: "", type: "string", required: false });
  const addInputRef = useRef(null);

  // Load templates
  useEffect(() => {
    const fetchTemplates = async () => {
      setLoading(true);
      try {
        const response = await axios.get(
          `${import.meta.env.VITE_SIMO_APP_API_URL}/api/templates`,
          {
            headers: {
              Authorization: "Bearer " + user?.token,
            },
          }
        );
        setTemplates(response.data);
      } catch (err) {
        setError("Không thể tải danh sách template");
      } finally {
        setLoading(false);
      }
    };

    if (user?.token) fetchTemplates();
  }, [user?.token]);

  // Focus vào input Template ID khi mở modal Add
  useEffect(() => {
    if (addModalOpen) {
      addInputRef.current?.focus();
    }
  }, [addModalOpen]);

  // Open edit modal
  const handleEdit = (template) => {
    setSelectedTemplate(template);
    setEditModalOpen(true);
  };

  // Update template
  const handleUpdate = async () => {
    setLoading(true);
    try {
      await axios.put(
        `${import.meta.env.VITE_SIMO_APP_API_URL}/api/templates/${selectedTemplate.id}`,
        selectedTemplate,
        {
          headers: {
            Authorization: "Bearer " + user?.token,
          },
        }
      );
      setTemplates(prev => prev.map(t => 
        t.id === selectedTemplate.id ? selectedTemplate : t
      ));
      setEditModalOpen(false);
    } catch (err) {
      setError("Cập nhật thất bại");
    } finally {
      setLoading(false);
    }
  };

  // Delete template
  const handleDelete = async (id) => {
    if (!window.confirm("Bạn có chắc muốn xóa template này?")) return;
    setLoading(true);
    try {
      await axios.delete(
        `${import.meta.env.VITE_SIMO_APP_API_URL}/api/templates/${id}`,
        {
          headers: {
            Authorization: "Bearer " + user?.token,
          },
        }
      );
      setTemplates(prev => prev.filter(t => t.id !== id));
    } catch (err) {
      setError("Xóa thất bại");
    } finally {
      setLoading(false);
    }
  };

  // Clean code: small, focused helper to download a blob with a given filename
  const downloadBlob = (blob, filename) => {
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.href = url;
    link.download = filename;
    document.body.appendChild(link);
    link.click();
    link.remove();
    window.URL.revokeObjectURL(url);
  };

  // SOLID - SRP: this function only handles the download flow
  // Assumes backend endpoint returns an Excel file for a template
  const handleDownload = async (template) => {
    setError(null);
    setLoading(true);
    try {
      const response = await axios.get(
        `${import.meta.env.VITE_SIMO_APP_API_URL}/api/templates/download`,
        {
          params: {
            templateID: template.templateID,
          },
          headers: {
            Authorization: "Bearer " + user?.token,
          },
          responseType: "blob",
        }
      );

      const blob = new Blob(
        [response.data],
        { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" }
      );
      downloadBlob(blob, `${template.templateID}.xlsx`);
    } catch (err) {
      setError("Tải xuống thất bại");
    } finally {
      setLoading(false);
    }
  };

  // Add new field to schema
  const handleAddField = () => {
    if (!newField.name.trim()) {
      setError("Tên trường không được để trống");
      return;
    }
    let schema;
    try {
      schema = JSON.parse(newTemplate.schemaJson);
    } catch (e) {
      setError("Schema JSON không hợp lệ");
      return;
    }
    if (schema[newField.name]) {
      setError("Tên trường đã tồn tại");
      return;
    }
    schema[newField.name] = {
      type: newField.type,
      required: newField.required
    };
    setNewTemplate(prev => ({
      ...prev,
      schemaJson: JSON.stringify(schema, null, 2)
    }));
    setNewField({ name: "", type: "string", required: false });
    setError(null);
  };

  // Create new template
  const handleCreate = async () => {
    if (!newTemplate.templateID || !newTemplate.name) {
      setError("Vui lòng điền đầy đủ Template ID và Template Name");
      return;
    }
    setLoading(true);
    try {
      const response = await axios.post(
        `${import.meta.env.VITE_SIMO_APP_API_URL}/api/templates`,
        newTemplate,
        {
          headers: {
            Authorization: "Bearer " + user?.token,
          },
        }
      );
      setTemplates(prev => [...prev, response.data]);
      setAddModalOpen(false);
      setNewTemplate({
        templateID: "",
        name: "",
        schemaJson: "{}"
      });
    } catch (err) {
      setError("Tạo template thất bại");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="template-container">
      <h1>Quản lý Template</h1>
      <button 
        className="add-button"
        onClick={() => setAddModalOpen(true)}
        aria-label="Thêm template mới"
        aria-haspopup="dialog"
        disabled={user?.role !== "ADMIN"}
      >
        Thêm Template
      </button>

      {loading && <div className="loading-overlay">Đang tải...</div>}
      {error && <p className="error">{error}</p>}

      <table className="template-table">
        <thead>
          <tr>
            <th>Template ID</th>
            <th>Template Name</th>
            <th>File</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {templates.map(template => (
            <tr key={template.id}>
              <td>{template.templateID}</td>
              <td>{template.name}</td>
              <td>
                {/* Accessibility: clear label; Clean code: call dedicated handler */}
                <button
                  onClick={() => handleDownload(template)}
                  aria-label={`Tải xuống ${template.templateID}.xlsx`}
                >
                  Tải xuống
                </button>
              </td>
              <td>
                <button onClick={() => handleEdit(template)} className="edit-button" disabled={user?.role !== "ADMIN"}>Sửa</button>
                <button 
                  onClick={() => handleDelete(template.id)}
                  className="delete-button"  disabled={user?.role !== "ADMIN"}
                >
                  Xóa
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Edit Modal */}
      <Modal
        isOpen={editModalOpen}
        onRequestClose={() => setEditModalOpen(false)}
        contentLabel="Edit Template"
        className="my-modal"
        overlayClassName="my-overlay"
        ariaHideApp={false}
        shouldFocusAfterRender={true}
        shouldReturnFocusAfterClose={true}
        role="dialog"
        aria-modal="true"
        aria-labelledby="edit-modal-title"
      >
        <h2 id="edit-modal-title">Sửa Template</h2>
        <textarea
          id="editSchemaJson"
          name="editSchemaJson"
          value={selectedTemplate?.schemaJson || ""}
          onChange={e => setSelectedTemplate(prev => ({
            ...prev,
            schemaJson: e.target.value
          }))}
          rows={20}
          cols={50}
          aria-label="Schema JSON"
        />
        <div className="modal-buttons">
          <button onClick={handleUpdate}>Cập nhật</button>
          <button onClick={() => setEditModalOpen(false)}>Đóng</button>
        </div>
      </Modal>

      {/* Add Modal */}
      {!addModalOpen && console.log("Modal đang đóng")}
      {addModalOpen && console.log("Modal đang mở")}
      <Modal
        isOpen={addModalOpen}
        onRequestClose={() => setAddModalOpen(false)}
        contentLabel="Add Template"
        className="my-modal"
        overlayClassName="my-overlay"
        ariaHideApp={false}
        shouldFocusAfterRender={true}
        shouldReturnFocusAfterClose={true}
        role="dialog"
        aria-modal="true"
        aria-labelledby="add-modal-title"
      >
        <h2 id="add-modal-title">Thêm Template mới</h2>
        <div className="form-group">
          <label htmlFor="templateID">Template ID:</label>
          <input
            id="templateID"
            name="templateID"
            type="text"
            value={newTemplate.templateID}
            onChange={e => setNewTemplate(prev => ({
              ...prev,
              templateID: e.target.value
            }))}
            aria-required="true"
            ref={addInputRef}
          />
        </div>
        <div className="form-group">
          <label htmlFor="templateName">Template Name:</label>
          <input
            id="templateName"
            name="templateName"
            type="text"
            value={newTemplate.name}
            onChange={e => setNewTemplate(prev => ({
              ...prev,
              name: e.target.value
            }))}
            aria-required="true"
          />
        </div>
        <div className="schema-editor">
          <h3>Schema</h3>
          <textarea
            id="schemaJson"
            name="schemaJson"
            value={newTemplate.schemaJson}
            onChange={e => setNewTemplate(prev => ({
              ...prev,
              schemaJson: e.target.value
            }))}
            rows={10}
            cols={50}
            aria-label="Schema JSON"
          />
          <div className="add-field">
            <input
              id="newFieldName"
              name="newFieldName"
              type="text"
              placeholder="Tên trường"
              value={newField.name}
              onChange={e => setNewField(prev => ({
                ...prev,
                name: e.target.value
              }))}
              aria-label="Tên trường mới"
            />
            <select
              id="newFieldType"
              name="newFieldType"
              value={newField.type}
              onChange={e => setNewField(prev => ({
                ...prev,
                type: e.target.value
              }))}
              aria-label="Loại dữ liệu"
            >
              <option value="string">String</option>
              <option value="integer">Integer</option>
              <option value="boolean">Boolean</option>
            </select>
            <input
              id="newFieldRequired"
              name="newFieldRequired"
              type="checkbox"
              checked={newField.required}
              onChange={e => setNewField(prev => ({
                ...prev,
                required: e.target.checked
              }))}
              aria-label="Bắt buộc"
            />
            <button onClick={handleAddField}>Thêm trường</button>
          </div>
        </div>
        <div className="modal-buttons">
          <button onClick={handleCreate}>Tạo</button>
          <button onClick={() => setAddModalOpen(false)}>Đóng</button>
        </div>
      </Modal>
    </div>
  );
};

export default TemplateManager;