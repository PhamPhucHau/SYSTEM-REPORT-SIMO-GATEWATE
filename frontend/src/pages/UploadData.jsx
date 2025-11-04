import React, { useState, useEffect } from "react"; // Thêm useEffect
import * as XLSX from "xlsx";
import axios from "axios";
import "../css/UploadData.css";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useAuth } from "../services/AuthContext";
import { Spinner, Alert } from "react-bootstrap"; // Thêm Spinner và Alert để hiển thị trạng thái loading/error
import Swal from 'sweetalert2';
// Cài đặt chế độ: true: dùng mock data, false: dùng API thật
const USE_MOCK_DATA = false; // Giữ nguyên hoặc thay đổi nếu cần

// --- XÓA mockTemplates ---

const UploadData = () => {
  const { user } = useAuth();
  const [file, setFile] = useState(null);
  const [selectedTemplate, setSelectedTemplate] = useState(null);
  const [parsedData, setParsedData] = useState([]);
  const [validationErrors, setValidationErrors] = useState([]);
  const [selectedDate, setSelectedDate] = useState(() => {
    const today = new Date();
    return new Date(today.getFullYear(), today.getMonth() - 1, 1); // Lùi 1 tháng
  });
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
          // 'FileName': selectedTemplate?.name + ' - ' + (uploadedFile?.name || 'unknown') // Thêm tiền tố templateID vào tên file
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
  // const mapDataForAPI16 = (data) => { 
  //     console.log(data);
  //     return data.map((row, rowIndex) => ({
  //       Cif: row["Số CIF"] || row["CIF"] || row["Cif"] || "",
  //       SoID: row["Số ID"] || row["SoID"] || "",
  //       LoaiID: row["Loại ID"] || row["LoaiID"] || "",
  //       TenKhachHang: row["Tên khách hàng"] || row["TenKhachHang"] || "",
  //       NgaySinh: row["Ngày sinh"] || row["NgaySinh"] || "",
  //       GioiTinh: row["Giới tính"] || row["GioiTinh"] || "",
  //       MaSoThue: row["Mã số thuế"] || row["MaSoThue"] || "",
  //       SoDienThoaiDangKyDichVu: row["Số điện thoại đăng ký dịch vụ Mobile banking"] || row["SoDienThoaiDangKyDichVu"] || "",
  //       DiaChi: row["Địa chỉ"] || row["DiaChi"] || "",
  //       SoTaiKhoan: row["Số tài khoản"] || row["SoTaiKhoan"] || "",
  //       LoaiTaiKhoan: row["Loại tài khoản"] || row["LoaiTaiKhoan"] || "",
  //       TrangThaiHoatDongTaiKhoan: row["Trạng thái hoạt động của tài khoản"] || row["TrangThaiHoatDongTaiKhoan"] || "",
  //       NgayMoTaiKhoan: row["Ngày mở TK "] || row["NgayMoTaiKhoan"] || "",
  //       PhuongThucMoTaiKhoan: row["Phương thức mở TKTT"] || row["PhuongThucMoTaiKhoan"] || "",
  //       QuocTich: row["Quốc tịch"] || row["QuocTich"] || "",
  //       DiaChiKiemSoatTruyCap: row["Địa chỉ kiểm soát truy cập phương tiện truyền thông"] || row["DiaChiKiemSoatTruyCap"] || "",
  //     }));
  //   };
  const mapDataForAPI16 = (data) => {
    return data.map((row, rowIndex) => {
      if (rowIndex === 7) {
        console.log("🔍 Debug row 7:", row);
        var GioiTinh1 = row["Giới tính"] || row["GioiTinh"] || "" ; 
        console.log(row["GioiTinh"]);
        console.log(GioiTinh1);
      }
  
      return {
        Cif: row["Số CIF"] || row["CIF"] || row["Cif"] || "",
        SoID: row["Số ID"] || row["SoID"] || "",
        LoaiID: row["Loại ID"] || row["LoaiID"] || "",
        TenKhachHang: row["Tên khách hàng"] || row["TenKhachHang"] || "",
        NgaySinh: row["Ngày sinh"] || row["NgaySinh"] || "",
        GioiTinh: row["Giới tính"] ?? row["GioiTinh"] ?? "",
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
      };
    });
  };
      const mapDataForAPI19 = (data) => { 
      return data.map((row, rowIndex) => ({
        Cif: row["Số CIF"] || row["CIF"] || row["Cif"] || "",
        SoID: row["Số ID"] || row["SoID"] || "",
        LoaiID: row["Loại ID"] || row["LoaiID"] || "",
        TenKhachHang: row["Tên khách hàng"] || row["TenKhachHang"] || "",
        NgaySinh: row["Ngày sinh"] || row["NgaySinh"] || "",
        GioiTinh:  row["Giới tính"] ?? row["GioiTinh"] ?? "",
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
  const validateData = (data, schemaJsonString) => {
    let errors = [];
  
    const schema = parseSchemaJson(schemaJsonString);
    if (!schema) {
      errors.push("Lỗi cấu trúc Schema Template: Không thể đọc định nghĩa cột.");
      return errors;
    }
  
    data.forEach((row, rowIndex) => {
      const excelRowNumber = rowIndex + 4;
      const CIF = row["Cif"];
  
      Object.entries(schema).forEach(([key, rules]) => {
        if (["Key", "_id", "__v"].includes(key)) return;
  
        const value = row[key];
  
        if (rules.required && (value === undefined || value === null || value === "")) {
          errors.push(`Dòng ${excelRowNumber}: Trường "${key}" có CIF "${CIF}" là bắt buộc, không được để trống.`);
          return;
        }
  
        // Chỉ tiếp tục nếu có giá trị
        if (value !== undefined && value !== null && value !== "") {
          const valueAsString = String(value);
  
          // 1. Kiểm tra kiểu dữ liệu
          switch (rules.type) {
            case "integer":
              if (!/^-?\d+$/.test(valueAsString) || isNaN(parseInt(valueAsString))) {
                errors.push(`Dòng ${excelRowNumber}: Trường "${key}" có CIF "${CIF}" phải là số nguyên.`);
              }
              break;
            case "number":
              if (isNaN(Number(value))) {
                errors.push(`Dòng ${excelRowNumber}: Trường "${key}" có CIF "${CIF}" phải là số.`);
              }
              break;
            case "boolean":
              if (!(valueAsString === "true" || valueAsString === "false" || valueAsString === "1" || valueAsString === "0")) {
                errors.push(`Dòng ${excelRowNumber}: Trường "${key}" có CIF "${CIF}" phải là giá trị boolean (true/false hoặc 1/0).`);
              }
              break;
            case "string":
              // Không cần kiểm tra
              break;
            default:
              errors.push(`Dòng ${excelRowNumber}: Trường "${key}" có kiểu không xác định trong schema.`);
          }
  
          // 2. Kiểm tra độ dài
          if (rules.maxLength && valueAsString.length > rules.maxLength) {
            errors.push(`Dòng ${excelRowNumber}: Trường "${key}" có CIF "${CIF}" có value ${valueAsString} dài ${valueAsString.length} ký tự, vượt quá giới hạn ${rules.maxLength}. `);
          }
  
          if (rules.minLength && valueAsString.length < rules.minLength) {
            errors.push(`Dòng ${excelRowNumber}: Trường "${key}" có CIF "${CIF}" dài ${valueAsString.length} ký tự, ngắn hơn yêu cầu tối thiểu ${rules.minLength}.`);
          }
  
          // 3. Kiểm tra pattern (nếu có)
          if (rules.pattern) {
            try {
              const regex = new RegExp(rules.pattern);
              if (!regex.test(valueAsString)) {
                errors.push(`Dòng ${excelRowNumber}: Trường "${key}" có CIF "${CIF}" không khớp định dạng yêu cầu.`);
              }
            } catch (e) {
              errors.push(`Lỗi định dạng regex trong schema trường "${key}".`);
            }
          }
  
          // 4. Kiểm tra giá trị enum (nếu có)
          if (rules.enum && Array.isArray(rules.enum)) {
            const parsedValue = rules.type === "integer" || rules.type === "number" ? Number(value) : value;
            if (!rules.enum.includes(parsedValue)) {
              errors.push(`Dòng ${excelRowNumber}: Trường "${key}" có CIF "${CIF}" phải là một trong các giá trị sau: ${rules.enum.join(", ")}.`);
            }
          }
        }
      });
  
      // Kiểm tra các cột không định nghĩa trong schema
      Object.keys(row).forEach(excelKey => {
        if (!schema.hasOwnProperty(excelKey)) {
          const extraValue = row[excelKey];
          if (extraValue !== undefined && extraValue !== null && extraValue !== "") {
            console.warn(`Dòng ${excelRowNumber}: Cột "${excelKey}" có CIF "${CIF}" không nằm trong schema, sẽ bị bỏ qua.`);
            // errors.push(`Dòng ${excelRowNumber}: Cột "${excelKey}" không nằm trong định nghĩa schema.`);
          }
        }
      });
    });
  
    return errors;
  };
  
  // Parse file Excel
  // const handleFileParse = () => {
  //   if (!file) return alert("Vui lòng chọn file Excel!");
  //   if (!selectedTemplate) return alert("Vui lòng chọn template!");

  //   setValidationErrors([]); // Reset lỗi trước khi parse
  //   setParsedData([]);
  //   setSubmitError(null);
  //   setSubmitSuccess(null);

  //   const reader = new FileReader();
  //   reader.readAsBinaryString(file);
  //   reader.onload = (e) => {
  //     try {
  //       const workbook = XLSX.read(e.target.result, { type: "binary", cellDates: true }); // Thêm cellDates: true để xử lý ngày tháng tốt hơn
  //       const sheetName = workbook.SheetNames[0];
  //       const sheet = workbook.Sheets[sheetName];

  //       // Bỏ qua 0 dòng đầu, dùng dòng 1 làm header
  //       const jsonData = XLSX.utils.sheet_to_json(sheet, { range: 0, defval: "" }); // defval: "" để ô trống thành chuỗi rỗng

  //       console.log("Raw JSON data:", jsonData);

  //       let mappedData = jsonData;
  //       // --- THAY ĐỔI: Sử dụng templateID từ selectedTemplate ---
  //       switch(selectedTemplate.templateID)
  //       {
  //         case "API_1_6_TTDS_TKTT_DK":
  //           {
  //             mappedData = mapDataForAPI16(jsonData);
  //             break; 
  //           }
  //           case  "API_1_7_TTDS_TKTT_NNGL":
  //           {
  //             mappedData = mapDataForAPI17(jsonData); 
  //             break;  
  //           }
  //            case "API_1_9_UPDATE_TTDS_TKTT_DK":
  //           {
  //             mappedData = mapDataForAPI19(jsonData);
  //             break; 
  //           }
  //           case  "API_1_8_UPDATE_TTDS_TKTT_NNGL":
  //           {
  //             mappedData = mapDataForAPI18(jsonData); 
  //             console.log(mappedData);
  //             break;  
  //           }
          
  //       };
  //       // Thêm các điều kiện mapping khác nếu cần
  //       // else if (selectedTemplate.templateID === "SOME_OTHER_ID") {
  //       //    mappedData = mapDataForOtherAPI(jsonData);
  //       // }

  //       console.log("Mapped data:", mappedData);

  //       // --- THAY ĐỔI: Validate dùng schemaJson từ selectedTemplate ---
  //       // Đảm bảo schemaJson tồn tại trước khi gọi validate
  //       if (!selectedTemplate.schemaJson) {
  //           alert("Template được chọn không có thông tin schema để validate.");
  //           return;
  //       }
  //       const errors = validateData(mappedData, selectedTemplate.schemaJson);
  //       setValidationErrors(errors);

  //       if (errors.length === 0) {
  //         setParsedData(mappedData);
  //         console.log("Parsed data (validated):", mappedData);
  //       } else {
  //         setParsedData([]);
  //         console.log("Validation Errors:", errors);
  //       }
  //     } catch (error) {
  //         console.error("Error parsing Excel file:", error);
  //         alert(`Đã xảy ra lỗi khi đọc file Excel: ${error.message}`);
  //         setValidationErrors([`Lỗi đọc file: ${error.message}`]);
  //         setParsedData([]);
  //     }
  //   };
  //   reader.onerror = (error) => {
  //       console.error("FileReader error:", error);
  //       alert("Không thể đọc file.");
  //       setValidationErrors(["Lỗi đọc file."]);
  //       setParsedData([]);
  //   }
  // };
  const handleFileParse = () => {
    if (!file) return alert("Vui lòng chọn file Excel!");
    if (!selectedTemplate) return alert("Vui lòng chọn template!");
  
    setValidationErrors([]);
    setParsedData([]);
    setSubmitError(null);
    setSubmitSuccess(null);
  
    const reader = new FileReader();
    reader.readAsBinaryString(file);
  
    reader.onload = (e) => {
      try {
        const workbook = XLSX.read(e.target.result, { type: "binary", cellDates: true });
        const sheetName = workbook.SheetNames[0];
        const sheet = workbook.Sheets[sheetName];
  
        const jsonData = XLSX.utils.sheet_to_json(sheet, { range: 0, defval: "" }); // Dùng dòng 1 làm header
        console.log("Raw JSON data:", jsonData);
  
        const mappedData = jsonData; // Không cần mapping nếu header khớp
  
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
    };
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
              //'FileName': selectedTemplate?.id + '_' + (requestData?.fileName || 'unknown') // Thêm tiền tố templateID vào tên file
            },
          }
        );
        console.log("Phản hồi từ server:", response.data);
        setSubmitSuccess("Tải lên thành công!");
        Swal.fire({
          icon: 'success',
          title: 'Thành công!',
          text: 'Xác nhận tải lên thành công.',
          confirmButtonText: 'OK'
        });
         // Reset state sau khi thành công? (Tùy chọn)
         // setFile(null);
         // setSelectedTemplate(null);
         // setParsedData([]);
         // document.querySelector('.file-input').value = ''; // Reset input file
      } catch (error) {
        console.error("Lỗi khi tải lên:", error);
        const errorMsg = error.response?.data?.message || error.message || "Lỗi không xác định";
        setSubmitError(`Tải lên thất bại! ${errorMsg}`);
        Swal.fire({
          icon: 'error',
          title: 'Lỗi!',
          text: "Lỗi khi tải lên:",
          confirmButtonText: 'Đóng'
        });
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
          <h3 className="mt-4">Dữ Liệu Đã Phân Tích (Hợp lệ) </h3>
          <h3 className="mt-4">Tổng cộng {parsedData.length } dòng </h3>
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