export const sbx_LoaiID = [
    { value: "1", label: "Thẻ căn cước công dân" },
    { value: "2", label: "Thẻ căn cước" },
    { value: "3", label: "Chứng minh nhân dân" },
    { value: "4", label: "Hộ chiếu" },
    { value: "5", label: "Giấy chứng nhận căn cước" },
    { value: "6", label: "Tài khoản định danh và xác thực điện tử" },
    { value: "7", label: "Giấy tờ khác" }
];
export const sbx_GioiTinh = [
    { value: "0", label: "Nữ" },
    { value: "1", label: "Nam" },
    { value: "2", label: "Giá trị khác" }
];
export const sbx_LoaiTaiKhoan = [
    { value: "1", label: "VND" },
    { value: "2", label: "Ngoại tệ" }
];
export const sbx_TrangThaiHoatDongTaiKhoan = [
    { value: "1", label: "Đang hoạt động" },
    { value: "2", label: "Tạm ngừng cung cấp dịch vụ ngân hàng điện tử" },
    { value: "3", label: "Tạm khóa" },
    { value: "4", label: "Phong tỏa" },
    { value: "5", label: "Đã đóng" }
];
export const sbx_PhuongThucMoTaiKhoan = [
    { value: "1", label: "Mở tại quầy" },
    { value: "2", label: "Mở qua eKYC" }
];
export const sbx_NghiNgo_TKTT = [
    { value: "0", label: "Không nghi ngờ gian lận" },
    { value: "1", label: "Thông tin trong hồ sơ mở TKTT của chủ tài khoản không trùng khớp với thông tin của cá nhân đó trong Cơ sở dữ liệu quốc gia về dân cư" },
    { value: "2", label: "TKTT nằm trong danh sách được quảng cáo, mua, bán, trao đổi trên các website, hội nhóm trên không gian mạng" },
    { value: "3", label: "TKTT nhận tiền từ nhiều TKTT khác nhau và được chuyển đi hoặc rút ra ngay trong thời gian rất ngắn (không để lại số dư hoặc để lại rất ít)" },
    { value: "4", label: "TKTT có hơn 03 giao dịch nhận tiền từ các TKTT nằm trong danh sách có dấu hiệu nghi ngờ liên quan đến lừa đảo, gian lận, giả mạo,..." },
    { value: "5", label: "Khách hàng thuộc danh sách cảnh báo của NHNN, Cơ quan Công an hoặc các cơ quan có thẩm quyền" }
];
export const sbx_LoaiVdt = [
    { value: "1", label: "VĐT khách hàng cá nhân (không phải là ĐVCNTT)" },
    { value: "2", label: "VĐT khách hàng cá nhân là ĐVCNTT" },
    { value: "3", label: "VĐT mở cho khách hàng là tổ chức (không phải là ĐVCNTT)" },
    { value: "4", label: "VĐT mở cho khách hàng là tổ chức là ĐVCNTT" }
];
export const sbx_TrangThaiHoatDongVdt = [
    { value: "1", label: "Đang hoạt động" },
    { value: "2", label: "Tạm ngừng cung cấp dịch vụ" }, // Dịch vụ 1.18
    { value: "2", label: "Đang tạm khóa hoặc phong tỏa" }, // Dịch vụ 1.19
    { value: "3", label: "Tạm khóa" }, // Dịch vụ 1.18
    { value: "3", label: "Đóng" }, // Dịch vụ 1.19
    { value: "4", label: "Đã đóng" } // Dịch vụ 1.18
];
export const sbx_NghiNgo_VDT = [
    { value: "0", label: "Không nghi ngờ gian lận" },
    { value: "1", label: "Thông tin trong hồ sơ mở Ví điện tử của chủ Ví điện tử không trùng khớp với thông tin của cá nhân đó trong Cơ sở dữ liệu quốc gia về dân cư" },
    { value: "2", label: "Ví điện tử nằm trong danh sách được quảng cáo, mua, bán, trao đổi trên các website, hội nhóm trên không gian mạng" },
    { value: "3", label: "Ví điện tử nhận tiền từ nhiều Ví điện tử, tài khoản đồng Việt Nam, thẻ ghi nợ, thẻ trả trước định danh khác nhau và được chuyển đi hoặc rút ra ngay trong thời gian rất ngắn (không để lại số dư hoặc để lại rất ít)" }
];

// Hàm lấy label từ danh sách
export const getLabelFromValue = (value, list) => {
    const item = list.find((item) => item.value == value);
    return item ? `${value} - ${item.label}` : value;
};