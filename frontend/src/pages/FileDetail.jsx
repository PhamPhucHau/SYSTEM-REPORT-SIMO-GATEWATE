import { useLocation, useNavigate } from "react-router-dom";
import "../css/FileDetail.css";
import {
  sbx_LoaiID,
  sbx_GioiTinh,
  sbx_LoaiTaiKhoan,
  sbx_TrangThaiHoatDongTaiKhoan,
  sbx_PhuongThucMoTaiKhoan,
  getLabelFromValue
} from "../resource/selectbox";

const FileDetails = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const data = location.state?.fileData || [];

  const selectboxMapping = {
    GioiTinh: sbx_GioiTinh,
    LoaiID: sbx_LoaiID,
    LoaiTaiKhoan: sbx_LoaiTaiKhoan,
    TrangThaiHoatDongTaiKhoan: sbx_TrangThaiHoatDongTaiKhoan,
    PhuongThucMoTaiKhoan: sbx_PhuongThucMoTaiKhoan,
  };

  return (
    <div className="file-details-container">
      <h1>Chi Tiết File</h1>
      {data.length > 0 ? (
        <div className="table-wrapper">
          <table className="details-table">
            <thead>
              <tr>
                {Object.keys(data[0]).map((key) => (
                  <th key={key}>{key}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {data.map((row, index) => (
                <tr key={index}>
                  {Object.keys(row).map((key) => (
                    <td key={key}>
                      {selectboxMapping[key]
                        ? getLabelFromValue(row[key], selectboxMapping[key])
                        : row[key]}
                    </td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <p>Không có dữ liệu.</p>
      )}
      <button onClick={() => navigate(-1)} className="back-btn">Quay Lại</button>
    </div>
  );
};

export default FileDetails;
