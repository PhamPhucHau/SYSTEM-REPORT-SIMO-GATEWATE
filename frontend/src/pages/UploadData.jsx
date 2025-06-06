import React, { useState, useEffect } from "react"; // Thêm useEffect
import * as XLSX from "xlsx";
import axios from "axios";
import "../css/UploadData.css";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useAuth } from "../services/AuthContext";
import { Spinner, Alert } from "react-bootstrap"; // Thêm Spinner và Alert để hiển thị trạng thái loading/error

// Cài đặt chế độ: true: dùng mock data, false: dùng API thật
const USE_MOCK_DATA = false; // Giữ nguyên hoặc thay đổi nếu cần

// --- XÓA mockTemplates ---

const UploadData = () => {
  const { user } = useAuth();
  const [file, setFile] = useState(null);
  const [selectedTemplate, setSelectedTemplate] = useState(null);
  const [parsedData, setParsedData] = useState([]);
  const [validationErrors, setValidationErrors] = useState([]);
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [templates, setTemplates] = useState([]); // State để lưu templates từ API
  const [loadingTemplates, setLoadingTemplates] = useState(false); // State loading cho templates
  const [errorTemplates, setErrorTemplates] = useState(null); // State error cho templates
  const [loadingSubmit, setLoadingSubmit] = useState(false); // State loading khi submit
  const [submitError, setSubmitError] = useState(null); // State lỗi khi submit
  const [submitSuccess, setSubmitSuccess] = useState(null); // State thành công khi submit


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

  // Chọn file Excel
  const handleFileUpload = async  (event) => {
    const uploadedFile = event.target.files[0];
    setFile(uploadedFile);
    // Reset trạng thái khi chọn file mới
    setParsedData([]);
    setValidationErrors([]);
    setSubmitError(null);
    setSubmitSuccess(null);
    // Tạo formData và upload file gốc
  const formData = new FormData();
  formData.append('file', uploadedFile);

  try {
    const response = await axios.post(
      `${import.meta.env.VITE_SIMO_APP_API_URL}/api/upload`, 
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': 'Bearer ' + user?.token,
        },
      }
    );
    console.log("Upload file Excel thành công:", response.data);
  } catch (error) {
    console.error("Lỗi khi upload file Excel:", error);
    alert("Không thể upload file Excel. Vui lòng thử lại.");
  }
  };

  // Chọn template từ dropdown
  const handleTemplateSelect = (event) => {
    const templateId = event.target.value;
    // --- THAY ĐỔI: Tìm template trong state 'templates' ---
    const template = templates.find((t) => t.id === templateId); // Giả sử API trả về 'id'
    setSelectedTemplate(template);
    // Reset trạng thái khi chọn template mới
    setParsedData([]);
    setValidationErrors([]);
    setSubmitError(null);
    setSubmitSuccess(null);
  };

  // Hàm mapping dữ liệu từ file Excel sang cấu trúc của API 1.6
  // Giữ nguyên hàm này nếu cấu trúc mapping vẫn đúng 
  const mapDataForAPI16 = (data) => { 
      return data.map((row, rowIndex) => ({
        Cif: row["Số CIF"] || row["CIF"] || row["Cif"] || "",
        SoID: row["Số ID"] || row["SoID"] || "",
        LoaiID: row["Loại ID"] || row["LoaiID"] || "",
        TenKhachHang: row["Tên khách hàng"] || row["TenKhachHang"] || "",
        NgaySinh: row["Ngày sinh"] || row["NgaySinh"] || "",
        GioiTinh: row["Giới tính"] || row["GioiTinh"] || "",
        MaSoThue: row["Mã số thuế"] || row["MaSoThue"] || "",
        SoDienThoaiDangKyDichVu: row["Số điện thoại đăng ký dịch vụ Mobile banking"] || row["SoDienThoaiDangKyDichVu"] || "",
        DiaChi: row["Địa chỉ"] || row["DiaChi"] || "",
        SoTaiKhoan: row["Số tài khoản"] || row["SoTaiKhoan"] || "",
        LoaiTaiKhoan: row["Loại tài khoản"] || row["LoaiTaiKhoan"] || "",
        TrangThaiHoatDongTaiKhoan: row["Trạng thái hoạt động của tài khoản"] || row["TrangThaiHoatDongTaiKhoan"] || "",
        NgayMoTaiKhoan: row["Ngày mở TK "] || row["NgayMoTaiKhoan"] || "",
        PhuongThucMoTaiKhoan: row["Phương thức mở TKTT"] || row["PhuongThucMoTaiKhoan"] || "",
        QuocTich: row["Quốc tịch"] || row["QuocTich"] || "",
        DiaChiKiemSoatTruyCap: row["Địa chỉ kiểm soát truy cập phương tiện truyền thông"] || row["DiaChiKiemSoatTruyCap"] || "",
      }));
    };
      const mapDataForAPI19 = (data) => { 
      return data.map((row, rowIndex) => ({
        Cif: row["Số CIF"] || row["CIF"] || row["Cif"] || "",
        SoID: row["Số ID"] || row["SoID"] || "",
        LoaiID: row["Loại ID"] || row["LoaiID"] || "",
        TenKhachHang: row["Tên khách hàng"] || row["TenKhachHang"] || "",
        NgaySinh: row["Ngày sinh"] || row["NgaySinh"] || "",
        GioiTinh: row["Giới tính"] || row["GioiTinh"] || "",
        MaSoThue: row["Mã số thuế"] || row["MaSoThue"] || "",
        SoDienThoaiDangKyDichVu: row["Số điện thoại đăng ký dịch vụ Mobile banking"] || row["SoDienThoaiDangKyDichVu"] || "",
        DiaChi: row["Địa chỉ"] || row["DiaChi"] || "",
        SoTaiKhoan: row["Số tài khoản"] || row["SoTaiKhoan"] || "",
        LoaiTaiKhoan: row["Loại tài khoản"] || row["LoaiTaiKhoan"] || "",
        TrangThaiHoatDongTaiKhoan: row["Trạng thái hoạt động của tài khoản"] || row["TrangThaiHoatDongTaiKhoan"] || "",
        NgayMoTaiKhoan: row["Ngày mở TK "] || row["NgayMoTaiKhoan"] || "",
        PhuongThucMoTaiKhoan: row["Phương thức mở TKTT"] || row["PhuongThucMoTaiKhoan"] || "",
        QuocTich: row["Quốc tịch"] || row["QuocTich"] || "",
        DiaChiKiemSoatTruyCap: row["Địa chỉ kiểm soát truy cập phương tiện truyền thông"] || row["DiaChiKiemSoatTruyCap"] || "",
         GhiChu: row["Ghi chú"] || row["GhiChu"] || ""
      }));
    };
const mapDataForAPI17 = (data) => {
  return data.map((row, rowIndex) => ({
    Cif: row["Số CIF"] || row["CIF"] || row["Cif"] || "",
    SoTaiKhoan: row["Số tài khoản"] || row["SoTaiKhoan"] || "",
    TenKhachHang: row["Tên khách hàng"] || row["TenKhachHang"] || "",
    TrangThaiHoatDongTaiKhoan: row["Trạng thái hoạt động của tài khoản"] || row["TrangThaiHoatDongTaiKhoan"] || "",
    NghiNgo: row["Nghi ngờ"] || row["NghiNgo"] || "",
    GhiChu: row["Ghi chú"] || row["GhiChu"] || ""
  }));
};
const mapDataForAPI18 = (data) => {
  return data.map((row, rowIndex) => ({
    Cif: row["Số CIF"] || row["CIF"] || row["Cif"] || "",
    SoTaiKhoan: row["Số tài khoản"] || row["SoTaiKhoan"] || "",
    TenKhachHang: row["Tên khách hàng"] || row["TenKhachHang"] || "",
    TrangThaiHoatDongTaiKhoan: row["Trạng thái hoạt động của tài khoản"] || row["TrangThaiHoatDongTaiKhoan"] || "",
    NghiNgo: row["Nghi ngờ"] || row["NghiNgo"] || "",
    GhiChu: row["Ghi chú"] || row["GhiChu"] || "",
    LyDoCapNhat: row["Lý do cập nhật"] || row["LyDoCapNhat"] || ""
    
  }));
};
  // Validate dữ liệu dựa trên schema của template
  function parseSchemaJson(schemaJsonString) {
    try {
      if (typeof schemaJsonString !== 'string' || !schemaJsonString.trim()) {
        throw new Error("Schema không hợp lệ (không phải chuỗi hoặc rỗng).");
      }
  
      let parsed = JSON.parse(schemaJsonString); // Lần parse đầu tiên
  
      // Nếu kết quả là string tiếp (nghĩa là JSON lồng chuỗi), parse thêm lần nữa
      if (typeof parsed === 'string') {
        parsed = JSON.parse(parsed); // Lần parse thứ hai (nếu cần)
      }
  
      // Kiểm tra lần cuối
      if (typeof parsed !== 'object' || parsed === null) {
        throw new Error("Schema không hợp lệ (không phải object sau khi parse).");
      }
  
      return parsed;
    } catch (e) {
      console.error("Không thể phân tích schemaJson:", e.message);
      return null;
    }
  }
  // Validate dữ liệu dựa trên schema của template
  const validateData = (data, schemaJsonString) => { // Đổi tên tham số cho rõ ràng
    let errors = [];

    // --- CẬP NHẬT: Parse schemaJson theo định dạng mới ---
    const schema = parseSchemaJson(schemaJsonString);
    if (!schema) {
      errors.push("Lỗi cấu trúc Schema Template: Không thể đọc định nghĩa cột. Vui lòng kiểm tra lại cấu hình template.");
      return errors;
    }

    // --- Phần validate dữ liệu dựa trên schema đã parse ---
    data.forEach((row, rowIndex) => {
      const excelRowNumber = rowIndex + 4; // Dòng 1-3 bỏ qua, dòng 4 là header, dữ liệu bắt đầu từ dòng 5 Excel = rowIndex 0 + 5

      Object.entries(schema).forEach(([key, rules]) => {
        // Bỏ qua key '_id' hoặc các key hệ thống khác nếu có trong schema
        // mà không yêu cầu có trong file Excel
                // --- THÊM ĐIỀU KIỆN ĐỂ BỎ QUA CỘT "Key" ---
        if (key === 'Key') {
          // console.log(`Validation skipped for field: ${key} at row ${excelRowNumber}`); // Ghi log nếu cần debug
          return; // Bỏ qua lần lặp này nếu key là 'Key'
        }
        // ------------------------------------------

        // Bỏ qua các key hệ thống khác nếu có
        if (key === '_id' || key === '__v') return;

        const value = row[key]; // Lấy giá trị từ dữ liệu đã map/parse

        // 1. Kiểm tra trường bắt buộc (required)
        if (rules.required && (value === undefined || value === null || value === "")) {
          errors.push(`Dòng ${excelRowNumber}: Trường "${key}" là bắt buộc, không được để trống.`);
        }
        // Chỉ thực hiện các kiểm tra khác nếu trường là bắt buộc HOẶC trường không bắt buộc nhưng CÓ giá trị
        else if (rules.required || (value !== undefined && value !== null && value !== "")) {

          // 2. Kiểm tra kiểu dữ liệu (type)
          const valueAsString = String(value); // Chuyển sang string để kiểm tra length và number

          if (rules.type === "integer") {
            // Cho phép số nguyên âm, nhưng không cho phép chữ hoặc ký tự đặc biệt (trừ dấu - ở đầu)
            if (!/^-?\d+$/.test(valueAsString) || isNaN(parseInt(valueAsString))) {
              errors.push(`Dòng ${excelRowNumber}: Trường "${key}" (${valueAsString}) phải là số nguyên.`);
            }
          } else if (rules.type === "number") { // Nếu có kiểu số thực
             if (isNaN(Number(value))) {
                errors.push(`Dòng ${excelRowNumber}: Trường "${key}" (${valueAsString}) phải là kiểu số.`);
             }
          } else if (rules.type === "string") {
            // Thường không cần kiểm tra type string vì dữ liệu từ excel thường là string
            // Nhưng có thể thêm nếu cần ép kiểu chặt chẽ
          } else if (rules.type === "boolean") {
            // Có thể kiểm tra nếu giá trị là true/false hoặc 0/1 tùy quy ước
          }
          // Thêm các kiểu dữ liệu khác nếu cần (ví dụ: date)
          // else if (rules.type === "date") {
          //    // Kiểm tra định dạng ngày tháng YYYY-MM-DD hoặc DD/MM/YYYY...
          //    const datePattern = /^\d{4}-\d{2}-\d{2}$/; // Ví dụ YYYY-MM-DD
          //    if (!datePattern.test(valueAsString)) {
          //        errors.push(`Dòng ${excelRowNumber}: Trường "${key}" (${valueAsString}) phải đúng định dạng YYYY-MM-DD.`);
          //    }
          // }


          // 3. Kiểm tra độ dài tối đa (maxLength) - áp dụng cho cả số và chuỗi
          if (rules.maxLength && valueAsString.length > rules.maxLength) {
            errors.push(`Dòng ${excelRowNumber}: Trường "${key}" (${valueAsString.length} ký tự) vượt quá độ dài tối đa cho phép (${rules.maxLength}).`);
          }

           // 4. Kiểm tra độ dài tối thiểu (minLength) - nếu có
           if (rules.minLength && valueAsString.length < rules.minLength) {
               errors.push(`Dòng ${excelRowNumber}: Trường "${key}" (${valueAsString.length} ký tự) chưa đủ độ dài tối thiểu yêu cầu (${rules.minLength}).`);
           }

           // 5. Kiểm tra giá trị trong danh sách cho phép (enum) - nếu có
           // if (rules.enum && Array.isArray(rules.enum) && !rules.enum.includes(value)) {
           //     errors.push(`Dòng ${excelRowNumber}: Trường "${key}" (${valueAsString}) phải là một trong các giá trị: ${rules.enum.join(', ')}.`);
           // }

            // 6. Kiểm tra định dạng (pattern/regex) - nếu có
            // if (rules.pattern) {
            //     try {
            //         const regex = new RegExp(rules.pattern);
            //         if (!regex.test(valueAsString)) {
            //             errors.push(`Dòng ${excelRowNumber}: Trường "${key}" (${valueAsString}) không khớp với định dạng yêu cầu.`);
            //         }
            //     } catch (regexError) {
            //         console.error("Invalid regex pattern in schema:", rules.pattern, regexError);
            //         // Có thể thêm lỗi vào errors nếu regex trong schema bị sai
            //         errors.push(`Lỗi cấu hình schema: Pattern không hợp lệ cho trường "${key}".`);
            //     }
            // }

        }
      });

      // (Tùy chọn) Kiểm tra các cột thừa trong file Excel không có trong schema
      Object.keys(row).forEach(excelKey => {
          if (!schema.hasOwnProperty(excelKey)) {
              // Bỏ qua nếu cột thừa không có giá trị
              if (row[excelKey] !== undefined && row[excelKey] !== null && row[excelKey] !== "") {
                   // Có thể ghi log cảnh báo hoặc thêm vào errors nếu muốn báo lỗi cột lạ
                   console.warn(`Dòng ${excelRowNumber}: Cột "${excelKey}" có trong file nhưng không được định nghĩa trong schema template "${selectedTemplate?.name || ''}". Giá trị "${row[excelKey]}" sẽ bị bỏ qua.`);
                   // errors.push(`Dòng ${excelRowNumber}: Cột "${excelKey}" không được định nghĩa trong schema.`);
              }
          }
      });

    });
    return errors;
  };

  // Parse file Excel
  const handleFileParse = () => {
    if (!file) return alert("Vui lòng chọn file Excel!");
    if (!selectedTemplate) return alert("Vui lòng chọn template!");

    setValidationErrors([]); // Reset lỗi trước khi parse
    setParsedData([]);
    setSubmitError(null);
    setSubmitSuccess(null);

    const reader = new FileReader();
    reader.readAsBinaryString(file);
    reader.onload = (e) => {
      try {
        const workbook = XLSX.read(e.target.result, { type: "binary", cellDates: true }); // Thêm cellDates: true để xử lý ngày tháng tốt hơn
        const sheetName = workbook.SheetNames[0];
        const sheet = workbook.Sheets[sheetName];

        // Bỏ qua 0 dòng đầu, dùng dòng 1 làm header
        const jsonData = XLSX.utils.sheet_to_json(sheet, { range: 0, defval: "" }); // defval: "" để ô trống thành chuỗi rỗng

        console.log("Raw JSON data:", jsonData);

        let mappedData = jsonData;
        // --- THAY ĐỔI: Sử dụng templateID từ selectedTemplate ---
        switch(selectedTemplate.templateID)
        {
          case "API_1_6_TTDS_TKTT_DK":
            {
              mappedData = mapDataForAPI16(jsonData);
              break; 
            }
            case  "API_1_7_TTDS_TKTT_NNGL":
            {
              mappedData = mapDataForAPI17(jsonData); 
              break;  
            }
             case "API_1_9_UPDATE_TTDS_TKTT_DK":
            {
              mappedData = mapDataForAPI19(jsonData);
              break; 
            }
            case  "API_1_8_UPDATE_TTDS_TKTT_NNGL":
            {
              mappedData = mapDataForAPI18(jsonData); 
              console.log(mappedData);
              break;  
            }
          
        };
        // Thêm các điều kiện mapping khác nếu cần
        // else if (selectedTemplate.templateID === "SOME_OTHER_ID") {
        //    mappedData = mapDataForOtherAPI(jsonData);
        // }

        console.log("Mapped data:", mappedData);

        // --- THAY ĐỔI: Validate dùng schemaJson từ selectedTemplate ---
        // Đảm bảo schemaJson tồn tại trước khi gọi validate
        if (!selectedTemplate.schemaJson) {
            alert("Template được chọn không có thông tin schema để validate.");
            return;
        }
        const errors = validateData(mappedData, selectedTemplate.schemaJson);
        setValidationErrors(errors);

        if (errors.length === 0) {
          setParsedData(mappedData);
          console.log("Parsed data (validated):", mappedData);
        } else {
          setParsedData([]);
          console.log("Validation Errors:", errors);
        }
      } catch (error) {
          console.error("Error parsing Excel file:", error);
          alert(`Đã xảy ra lỗi khi đọc file Excel: ${error.message}`);
          setValidationErrors([`Lỗi đọc file: ${error.message}`]);
          setParsedData([]);
      }
    };
    reader.onerror = (error) => {
        console.error("FileReader error:", error);
        alert("Không thể đọc file.");
        setValidationErrors(["Lỗi đọc file."]);
        setParsedData([]);
    }
  };

  // Hàm xử lý submit (không thay đổi nhiều, chỉ thêm loading/error state)
  const handleSubmit = async () => {
    if (validationErrors.length > 0) {
      alert("Dữ liệu không hợp lệ, vui lòng kiểm tra lại!");
      return;
    }
    if (parsedData.length === 0) {
        alert("Không có dữ liệu hợp lệ để tải lên.");
        return;
    }
    if (!selectedTemplate) {
      alert("Vui lòng chọn template!");
      return;
    }
    if (!file) {
      alert("Vui lòng chọn file để tải lên!");
      return;
    }

    setLoadingSubmit(true); // Bắt đầu loading
    setSubmitError(null);
    setSubmitSuccess(null);

    // Chuyển đổi ngày thành MMYYYY
    const monthPart = String(selectedDate.getMonth() + 1).padStart(2, '0'); // Lấy tháng (0-11) + 1, đảm bảo 2 chữ số
    const yearPart = selectedDate.getFullYear();
    const formattedMonthYear = `${monthPart}${yearPart}`; // Định dạng MMYYYY

    const requestData = {
      templateID: selectedTemplate.templateID,
      templateName: selectedTemplate.name,
      monthYear: formattedMonthYear,
      data: parsedData,
      total_record: parsedData.length,
      userId: user?.id || "Unknown",
      username: user?.name || "Unknown",
      fileName: file?.name || "Unknown",
      fileType: file?.name?.split(".").pop() || "Unknown",
    };

    console.log("Dữ liệu gửi lên:", requestData);

    if (USE_MOCK_DATA) {
      console.log("Mock upload data:", requestData);
      setTimeout(() => { // Giả lập độ trễ mạng
          setSubmitSuccess("Mock Upload Thành Công!");
          setLoadingSubmit(false);
          // Reset state sau khi thành công? (Tùy chọn)
          // setFile(null);
          // setSelectedTemplate(null);
          // setParsedData([]);
          // document.querySelector('.file-input').value = ''; // Reset input file
      }, 1500);
    } else {
      try {
        const response = await axios.post(
          `${import.meta.env.VITE_SIMO_APP_API_URL}/api/upload_history`,
          requestData,
          {
            headers: {
              "Content-Type": "application/json",
              "Authorization": "Bearer " + user?.token,
            },
          }
        );
        console.log("Phản hồi từ server:", response.data);
        setSubmitSuccess("Tải lên thành công!");
         // Reset state sau khi thành công? (Tùy chọn)
         // setFile(null);
         // setSelectedTemplate(null);
         // setParsedData([]);
         // document.querySelector('.file-input').value = ''; // Reset input file
      } catch (error) {
        console.error("Lỗi khi tải lên:", error);
        const errorMsg = error.response?.data?.message || error.message || "Lỗi không xác định";
        setSubmitError(`Tải lên thất bại! ${errorMsg}`);
        // alert("Tải lên thất bại! Kiểm tra console để biết thêm chi tiết.");
      } finally {
        setLoadingSubmit(false); // Kết thúc loading
      }
    }
  };

  return (
    <div className="upload-container">
      <h1 className="title">Upload Dữ Liệu</h1>

      {/* Chọn Template */}
      <div className="form-group">
        <label>Chọn Template:</label>
        <select
            onChange={handleTemplateSelect}
            className="dropdown"
            // --- THAY ĐỔI: Sử dụng selectedTemplate?.id ---
            value={selectedTemplate?.id || ""} // Sử dụng id từ state template, hoặc rỗng nếu chưa chọn
            disabled={loadingTemplates} // Disable khi đang tải templates
        >
          <option value="">-- Chọn Template --</option>
          {/* --- THAY ĐỔI: Lặp qua state 'templates' --- */}
          {templates.map((template) => (
            // --- THAY ĐỔI: key và value dùng template.id ---
            <option key={template.id} value={template.id}>
              {template.name}
            </option>
          ))}
        </select>
        {/* Hiển thị trạng thái loading/error của templates */}
        {loadingTemplates && <Spinner animation="border" size="sm" className="ms-2" />}
        {errorTemplates && <Alert variant="danger" className="mt-2">{errorTemplates}</Alert>}
      </div>

      {/* DatePicker for Month & Year */}
      <div className="form-group">
        <label>Chọn Tháng & Năm:</label>
        <DatePicker
          selected={selectedDate}
          onChange={(date) => setSelectedDate(date)}
          showMonthYearPicker
          dateFormat="MM/yyyy" // Định dạng hiển thị
          // monthFormat="MM" // Thuộc tính này không chuẩn, dateFormat là đủ
          dropdownMode="select"
          className="form-control" // Thêm class để dễ style
        />
      </div>

      {/* Upload File */}
      <div className="form-group">
        <label>Chọn File Excel:</label>
        <input
          type="file"
          accept=".xlsx, .xls"
          onChange={handleFileUpload}
          className="file-input form-control" // Thêm class form-control
        />
      </div>

      <button
        onClick={handleFileParse}
        className="parse-btn btn btn-secondary" // Thêm class Bootstrap
        disabled={!file || !selectedTemplate} // Disable nếu chưa chọn file hoặc template
      >
        Phân Tích File
      </button>

       {/* Hiển thị lỗi validation */}
      {validationErrors.length > 0 && (
        <div className="error-box alert alert-warning mt-3"> {/* Dùng alert Bootstrap */}
          <h3>⚠️ Lỗi Dữ Liệu Trong File:</h3>
          <ul>
            {validationErrors.map((error, index) => (
              <li key={index}>{error}</li>
            ))}
          </ul>
        </div>
      )}

      {/* Hiển thị dữ liệu đã parse và nút Upload */}
      {parsedData.length > 0 && validationErrors.length === 0 && (
        <>
          <h3 className="mt-4">Dữ Liệu Đã Phân Tích (Hợp lệ):</h3>
              {/* Di chuyển nút Submit lên đây */}
    <div className="d-flex justify-content-between align-items-center mb-3">
      <div>
        {loadingSubmit && <Spinner animation="border" className="me-2" />}
        {submitError && <Alert variant="danger" className="d-inline-block mb-0">{submitError}</Alert>}
        {submitSuccess && <Alert variant="success" className="d-inline-block mb-0">{submitSuccess}</Alert>}
      </div>
      <button
        onClick={handleSubmit}
        className="upload-btn btn btn-primary"
        disabled={loadingSubmit}
      >
        {loadingSubmit ? 'Đang tải lên...' : 'Upload Dữ Liệu'}
      </button>
    </div>
          <div className="table-responsive"> {/* Bọc table để responsive */}
            <table className="data-table table table-striped table-bordered table-hover"> {/* Thêm class Bootstrap */}
              <thead>
                <tr>
                  {/* --- THAY ĐỔI: Lấy header từ schema nếu có, hoặc từ dòng đầu tiên --- */}
                  {/* Cách 1: Lấy từ schema (nếu schemaJson đã parse) */}
                  {selectedTemplate && selectedTemplate.schemaJson && (() => {
                      try {
                          const schema = JSON.parse(selectedTemplate.schemaJson.replace(/^'|'$/g, ''));
                          return Object.keys(schema).map((key) => <th key={key}>{key}</th>);
                      } catch {
                          // Nếu lỗi parse schema, fallback về cách 2
                          return Object.keys(parsedData[0]).map((key) => <th key={key}>{key}</th>);
                      }
                  })()}
                  {/* Cách 2: Lấy từ dữ liệu (nếu không có schema hoặc lỗi parse) */}
                  {(!selectedTemplate || !selectedTemplate.schemaJson) && Object.keys(parsedData[0]).map((key) => (
                      <th key={key}>{key}</th>
                  ))}
                </tr>
              </thead>
              <tbody>
                {parsedData.map((row, index) => (
                  <tr key={index}>
                     {/* Đảm bảo thứ tự cột khớp với header */}
                     {selectedTemplate && selectedTemplate.schemaJson && (() => {
                      try {
                          const schema = JSON.parse(selectedTemplate.schemaJson.replace(/^'|'$/g, ''));
                          return Object.keys(schema).map((key) => <td key={key}>{row[key]}</td>);
                      } catch {
                          return Object.values(row).map((value, i) => <td key={i}>{String(value)}</td>); // Chuyển thành String để tránh lỗi render object/null
                      }
                     })()}
                     {(!selectedTemplate || !selectedTemplate.schemaJson) && Object.values(row).map((value, i) => (
                      <td key={i}>{String(value)}</td> // Chuyển thành String để tránh lỗi render object/null
                    ))}
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Hiển thị thông báo submit */}
          {loadingSubmit && <Spinner animation="border" className="mt-3" />}
          {submitError && <Alert variant="danger" className="mt-3">{submitError}</Alert>}
          {submitSuccess && <Alert variant="success" className="mt-3">{submitSuccess}</Alert>}


          <button
            onClick={handleSubmit}
            className="upload-btn btn btn-primary mt-3" // Thêm class Bootstrap
            disabled={loadingSubmit} // Disable khi đang submit
          >
            {loadingSubmit ? 'Đang tải lên...' : 'Upload Dữ Liệu'}
          </button>
        </>
      )}
    </div>
  );
};

export default UploadData;