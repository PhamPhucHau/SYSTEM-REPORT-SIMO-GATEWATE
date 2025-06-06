import { useState } from "react";
import { getHistoryByCif } from "../services/historyService";

const HistoryQuery = () => {
  const [cif, setCif] = useState("");
  const [history, setHistory] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSearch = () => {
    setIsLoading(true);
    setError(null);
    getHistoryByCif(cif)
      .then((data) => setHistory(data))
      .catch((err) => {
        console.error("Error:", err);
        setError("Không thể tải lịch sử");
        setHistory([]);
      })
      .finally(() => setIsLoading(false));
  };

  return (
    <div>
      <input
        type="text"
        placeholder="Nhập CIF"
        value={cif}
        onChange={(e) => setCif(e.target.value)}
      />
      <button onClick={handleSearch} disabled={isLoading}>
        {isLoading ? "Đang tìm..." : "Tìm kiếm"}
      </button>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {history.length > 0 ? (
        history.map((entry, index) => (
          <div key={index}>
            <strong>CIF: {entry.cif}</strong>
            <ul>
              {entry.history.map((item, i) => (
                <li key={i}>{item}</li>
              ))}
            </ul>
          </div>
        ))
      ) : (
        <p>Không có dữ liệu</p>
      )}
    </div>
  );
};

export default HistoryQuery;