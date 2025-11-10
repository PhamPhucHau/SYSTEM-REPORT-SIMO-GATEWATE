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
import * as ExcelJS from "exceljs";
import { saveAs } from "file-saver";

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

  // Hàm tạo và download file Excel
  const handleDownloadExcel = async () => {
    if (data.length === 0) return;

    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet("Chi tiết file");

    // Lấy header từ object đầu tiên
    const headers = Object.keys(data[0]);
    const headerRow = worksheet.addRow(headers);

    // Style cho header
    headerRow.font = { bold: true, color: { argb: "FFFFFFFF" } };
    headerRow.fill = {
      type: "pattern",
      pattern: "solid",
      fgColor: { argb: "FF1f4e79" },
    };
    headerRow.alignment = { vertical: "middle", horizontal: "center" };

    // Thêm dữ liệu
    data.forEach((row) => {
      const rowData = headers.map((key) => {
        return selectboxMapping[key]
          ? getLabelFromValue(row[key], selectboxMapping[key])
          : row[key];
      });
      worksheet.addRow(rowData);
    });

    // Tự động điều chỉnh độ rộng cột
    worksheet.columns = headers.map((header) => ({
      header,
      key: header,
      width: Math.max(header.length, 15),
    }));

    // Tạo file và download
    const buffer = await workbook.xlsx.writeBuffer();
    const blob = new Blob([buffer], {
      type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    });
    saveAs(blob, `Chi_tiet_file_${new Date().getTime()}.xlsx`);
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

      <div className="button-group">
        <button onClick={() => navigate(-1)} className="back-btn">
          Quay Lại 
        </button>
        <span> </span>
        <button onClick={handleDownloadExcel} className="download-btn">
          Download Excel
        </button>
      </div>
    </div>
  );
};

export default FileDetails;