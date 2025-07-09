var db = db.getSiblingDB('SIMO');

var collections = [
  'API_1_6_tktt_dinh_ky',
  'LIST_FILE_UPLOAD',
  'ReportSystemSIMODSVNChannel',
  'permissions',
  'role_permissions',
  'roles',
  'templates',
  'users'
];

// Tạo các collection nếu chưa có
collections.forEach(function (col) {
  if (!db.getCollectionNames().includes(col)) {
    db.createCollection(col);
  }
});

// Insert templates nếu chưa có
if (db.templates.countDocuments({}) === 0) {
  db.templates.insertMany([
    {
      _id: ObjectId("68007d741bb8984db902d305"),
      templateID: "API_1_6_TTDS_TKTT_DK",
      name: "API 1.6 - TKTT Định Kỳ",
      schemaJson: JSON.stringify({
        Key: { type: "string", required: false },
        Cif: { type: "string", maxLength: 36, required: true },
        SoID: { type: "string", maxLength: 15, required: true },
        LoaiID: { type: "integer", enum: [1, 2, 3, 4, 5, 6, 7], required: true },
        TenKhachHang: { type: "string", maxLength: 150, required: true },
        NgaySinh: { type: "string", pattern: "^\\d{2}/\\d{2}/\\d{4}$", maxLength: 10, required: true },
        GioiTinh: { type: "integer", enum: [0, 1, 2], required: true },
        MaSoThue: { type: "string", minLength: 10, maxLength: 13, required: false },
        SoDienThoaiDangKyDichVu: { type: "string", maxLength: 15, required: true },
        DiaChi: { type: "string", maxLength: 300, required: false },
        DiaChiKiemSoatTruyCap: { type: "string", maxLength: 60, required: true },
        MaSoNhanDangThietBiDiDong: { type: "string", maxLength: 36, required: false },
        SoTaiKhoan: { type: "string", maxLength: 36, required: true },
        LoaiTaiKhoan: { type: "integer", enum: [1, 2], required: false },
        TrangThaiHoatDongTaiKhoan: { type: "integer", enum: [1, 2, 3, 4, 5], required: true },
        NgayMoTaiKhoan: { type: "string", pattern: "^\\d{2}/\\d{2}/\\d{4}$", maxLength: 10, required: true },
        PhuongThucMoTaiKhoan: { type: "integer", enum: [1, 2], required: false },
        NgayXacThucTaiQuay: { type: "string", pattern: "^\\d{2}/\\d{2}/\\d{4}$", maxLength: 10, required: false },
        QuocTich: { type: "string", maxLength: 36, required: true }
      }),
      _class: "SHINHAN_PORTAL.REPORT_SIMO.domain.entity.Template"
    },
    {
      _id: ObjectId("68182bb2ed79e56becefa044"),
      templateID: "API_1_7_TTDS_TKTT_NNGL",
      name: "API 1.7 Dịch vụ thu thập danh sách TKTT nghi ngờ gian lận",
      schemaJson: JSON.stringify({
        Key: { type: "string", required: false },
        Cif: { type: "string", maxLength: 36, required: true },
        SoTaiKhoan: { type: "string", maxLength: 36, required: true },
        TenKhachHang: { type: "string", maxLength: 150, required: true },
        TrangThaiHoatDongTaiKhoan: { type: "integer", required: true, enum: [1, 2, 3, 4, 5] },
        NghiNgo: { type: "integer", required: true, enum: [0, 1, 2, 3, 4, 5, 6, 7, 8] },
        GhiChu: { type: "string", maxLength: 500, required: false }
      }),
      _class: "SHINHAN_PORTAL.REPORT_SIMO.domain.entity.Template"
    },
    {
      _id: ObjectId("682bf67902badf663a6167d9"),
      templateID: "API_1_8_UPDATE_TTDS_TKTT_NNGL",
      name: "API 1.8 Dịch vụ cập nhật lại thông tin TKTT nghi ngờ gian lận",
      schemaJson: JSON.stringify({
        Key: { type: "string", required: false },
        Cif: { type: "string", maxLength: 36, required: true },
        SoTaiKhoan: { type: "string", maxLength: 36, required: true },
        TenKhachHang: { type: "string", maxLength: 150, required: true },
        TrangThaiHoatDongTaiKhoan: {type: "integer",required: true, enum: [1, 2, 3, 4, 5] },
        NghiNgo: {type: "integer", required: true, enum: [0, 1, 2, 3, 4, 5, 6, 7, 8]},
        GhiChu: { type: "string", maxLength: 500, required: false },
        LyDoCapNhat: { type: "string", required: true }
      }),
      _class: "SHINHAN_PORTAL.REPORT_SIMO.domain.entity.Template"
    },
    {
      _id: ObjectId("682bf63502badf663a6167d8"),
      templateID: "API_1_9_UPDATE_TTDS_TKTT_DK",
      name: "API 1.9 Dịch vụ cập nhật thông tin khách hàng mở TKTT",
      schemaJson: JSON.stringify({
        Key: { type: "string", required: false },
        Cif: { type: "string", maxLength: 36, required: true },
        SoID: { type: "string", maxLength: 15, required: true },
        LoaiID: { type: "integer", enum: [1, 2, 3, 4, 5, 6, 7], required: true },
        TenKhachHang: { type: "string", maxLength: 150, required: true },
        NgaySinh: { type: "string", pattern: "^\\d{2}/\\d{2}/\\d{4}$", maxLength: 10, required: true },
        GioiTinh: { type: "integer", enum: [0, 1, 2], required: true },
        MaSoThue: { type: "string", minLength: 10, maxLength: 13, required: false },
        SoDienThoaiDangKyDichVu: { type: "string", maxLength: 15, required: true },
        DiaChi: { type: "string", maxLength: 300, required: false },
        DiaChiKiemSoatTruyCap: { type: "string", maxLength: 60, required: true },
        MaSoNhanDangThietBiDiDong: { type: "string", maxLength: 36, required: false },
        SoTaiKhoan: { type: "string", maxLength: 36, required: true },
        LoaiTaiKhoan: { type: "integer", enum: [1, 2], required: false },
        TrangThaiHoatDongTaiKhoan: { type: "integer", enum: [1, 2, 3, 4, 5], required: true },
        NgayMoTaiKhoan: { type: "string", pattern: "^\\d{2}/\\d{2}/\\d{4}$", maxLength: 10, required: true },
        PhuongThucMoTaiKhoan: { type: "integer", enum: [1, 2], required: false },
        NgayXacThucTaiQuay: { type: "string", pattern: "^\\d{2}/\\d{2}/\\d{4}$", maxLength: 10, required: false },
        QuocTich: { type: "string", maxLength: 36, required: true },
        GhiChu: { type: "string", maxLength: 500, required: false }
      }),
      _class: "SHINHAN_PORTAL.REPORT_SIMO.domain.entity.Template"
    }
  ]);
}

// Insert users nếu chưa có
if (db.users.countDocuments({}) === 0) {
  db.users.insertMany([
    {
      _id: ObjectId("67cfda4bb991f49c4acdce94"),
      name: "Pham Phuc Hau",
      username: "BankerID",
      phone_no: "1234567890",
      email: "hau@example.com",
      createdAat: ISODate("2025-03-11T06:38:03.653Z"),
      isActive: true,
      roleId: ObjectId("65f2c8a9e8a6e9b8f0a1b002"),
      passwordHash: "$2a$10$v27UpLM9HFDuAOpNeRAGh.p3KlOl8lBj8STWZNptNVLlh1twuVlKi",
      role: "ADMIN"
    },
    {
      _id: ObjectId("684a40f04454bb008b470fd8"),
      name: "checker",
      username: "checker",
      passwordHash: "$2a$10$ivbmoYf8mxzo/6SxmLeLg.0ENJtAz2FHfpQ660chCUyxlnmFvfiyO",
      createdAt: ISODate("2025-06-12T02:52:32.029Z"),
      phoneNo: "0773541433",
      email: "checker@gmail.com",
      isActive: true,
      role: "CHECKER",
      _class: "SHINHAN_PORTAL.REPORT_SIMO.domain.entity.User"
    },
    {
      _id: ObjectId("684a41184454bb008b470fd9"),
      name: "maker",
      username: "maker",
      passwordHash: "$2a$10$wuyy5Gp4o4l6vL8kENVUDOWbkXoPrU8dylokqgJ8C89A15vjhBBu2",
      createdAt: ISODate("2025-06-12T02:53:12.327Z"),
      phoneNo: "0773541433",
      email: "maker@gmail.com",
      isActive: true,
      role: "MAKER",
      _class: "SHINHAN_PORTAL.REPORT_SIMO.domain.entity.User"
    },
    {
      _id: ObjectId("684a41314454bb008b470fda"),
      name: "maker1",
      username: "maker1",
      passwordHash: "$2a$10$CTM4kegsHPUBlQTn/pIlK.eUsJNB6VP.NyZ57/Bo.VmVczB4al/pq",
      createdAt: ISODate("2025-06-12T02:53:37.499Z"),
      phoneNo: "0773541433",
      email: "maker1@gmail.com",
      isActive: true,
      role: "MAKER",
      _class: "SHINHAN_PORTAL.REPORT_SIMO.domain.entity.User"
    }
  ]);
}
