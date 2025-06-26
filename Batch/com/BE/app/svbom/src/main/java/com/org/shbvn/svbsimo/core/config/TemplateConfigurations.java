package com.org.shbvn.svbsimo.core.config;

import com.org.shbvn.svbsimo.core.annotation.TemplateModel;
import com.org.shbvn.svbsimo.core.annotation.TemplateModels;
import com.org.shbvn.svbsimo.core.model.BankCommonTemplate;

/**
 * Configuration class that defines all template models
 * The annotation processor will generate model classes based on these definitions
 */
@TemplateModels({
    @TemplateModel(
        code = "API_1_6_TTDS_TKTT_DK",
        columns = "ROW_ID,Cif,SoID,LoaiID,TenKhachHang,NgaySinh,GioiTinh,MaSoThue,SoDienThoaiDangKyDichVu,DiaChi,DiaChiKiemSoatTruyCap,MaSoNhanDangThietBiDiDong,SoTaiKhoan,LoaiTaiKhoan,TrangThaiHoatDongTaiKhoan,NgayMoTaiKhoan,PhuongThucMoTaiKhoan,NgayXacThucTaiQuay,QuocTich",
        baseClass = BankCommonTemplate.class
    ),
    @TemplateModel(
        code = "API_1_7_TTDS_TKTT_NNGL",
        columns = "ROW_ID,Cif,SoTaiKhoan,TenKhachHang,TrangThaiHoatDongTaiKhoan,NghiNgo,GhiChu",
        baseClass = BankCommonTemplate.class
    ),
    @TemplateModel(
        code = "API_1_8_UPDATE_TTDS_TKTT_NNGL",
        columns = "ROW_ID,Cif,TenKhachHang,SoTaiKhoan,TrangThaiHoatDongTaiKhoan,NghiNgo,GhiChu,LyDoCapNhat",
        baseClass = BankCommonTemplate.class
    ),
    @TemplateModel(
        code = "API_1_9_UPDATE_TTDS_TKTT_DK",
        columns = "ROW_ID,Cif,SoID,LoaiID,TenKhachHang,NgaySinh,GioiTinh,MaSoThue,SoDienThoaiDangKyDichVu,DiaChi,DiaChiKiemSoatTruyCap,MaSoNhanDangThietBiDiDong,SoTaiKhoan,TrangThaiHoatDongTaiKhoan,NgayXacThucTaiQuay,LoaiTaiKhoan,NgayMoTaiKhoan,PhuongThucMoTaiKhoan,GhiChu,QuocTich",
        baseClass = BankCommonTemplate.class
    ),
        @TemplateModel(
        code = "API_1_22_WDR",
        columns = "ThoiGianDuLieu,ThoiGianGuiBaoCao,tkdbttTaiNHHT[MaToChucTGTT,SoHieuTKDBTT,SoDuTKDBTT]",
        baseClass = BankCommonTemplate.class
    )
})
public class TemplateConfigurations {
    // This class only serves as a container for annotations
}
