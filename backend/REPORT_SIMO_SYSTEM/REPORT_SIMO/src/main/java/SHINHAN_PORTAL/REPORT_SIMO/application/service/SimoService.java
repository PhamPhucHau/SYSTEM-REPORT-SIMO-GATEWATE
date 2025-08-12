package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.*;

import java.util.List;

public interface SimoService {
    TokenResponseDTO getToken(String username, String password, String consumerKey, String consumerSecret);
    TKTTResponseDTO uploadTKTTReport(String token, String maYeuCau, String kyBaoCao, List<TKTTRequestDTO> tkttList);
    TKTTResponseDTO uploadTKTTReportAutoToken(String maYeuCau, String kyBaoCao, List<TKTTRequestDTO> tkttList);
    TKTTResponseDTO api_1_7_uploadNGGL(String token, String maYeuCau, String kyBaoCao, List<API_1_7_tktt_nggl_DT0> tkttList);
    TKTTResponseDTO api_1_8_update_uploadNGGL(String token, String maYeuCau, String kyBaoCao, List<API_1_8_update_tktt_nggl_DT0> tkttList);
    TKTTResponseDTO api_1_9_update_uploadTKTT(String token, String maYeuCau, String kyBaoCao, List<API_1_9_update_tktt_nggl_DT0> tkttList);
    TKTTResponseDTO api_1_7_uploadNGGL_autoToken(String maYeuCau, String kyBaoCao, List<API_1_7_tktt_nggl_DT0> tkttList);
    TKTTResponseDTO api_1_8_update_uploadNGGL_autoToken(String maYeuCau, String kyBaoCao, List<API_1_8_update_tktt_nggl_DT0> tkttList);
    TKTTResponseDTO api_1_9_TKTT_autoToken(String maYeuCau, String kyBaoCao, List<API_1_9_update_tktt_nggl_DT0> tkttList);
    // Upload định kỳ DVCNTT
    TKTTResponseDTO api_1_27_uploadDVCNTT(String token, String maYeuCau, String kyBaoCao, List<API_1_27_TT_DVCNTT_DTO> dvcnttList);

    // Upload nghi ngờ gian lận DVCNTT
    TKTTResponseDTO api_1_28_uploadNGGL_DVCNTT(String token, String maYeuCau, String kyBaoCao, List<API_1_28_TT_DVCNTT_NGGL_DTO> dvcnttList);

    // Update nghi ngờ gian lận DVCNTT
    TKTTResponseDTO api_1_29_updateNGGL_DVCNTT(String token, String maYeuCau, String kyBaoCao, List<API_1_29_UPDATE_DVCNTT_NGGL_DTO> dvcnttList);

    // Update doanh nghiệp/hộ kinh doanh DVCNTT
    TKTTResponseDTO api_1_30_updateDVCNTT(String token, String maYeuCau, String kyBaoCao, List<API_1_30_UPDATE_DVCNTT_DTO> dvcnttList);
    TKTTResponseDTO api_1_27_uploadDVCNTT_autoToken(String maYeuCau, String kyBaoCao, List<API_1_27_TT_DVCNTT_DTO> dvcnttList);

    TKTTResponseDTO api_1_28_uploadNGGL_DVCNTT_autoToken(String maYeuCau, String kyBaoCao, List<API_1_28_TT_DVCNTT_NGGL_DTO> dvcnttList);

    TKTTResponseDTO api_1_29_updateNGGL_DVCNTT_autoToken(String maYeuCau, String kyBaoCao, List<API_1_29_UPDATE_DVCNTT_NGGL_DTO> dvcnttList);

    TKTTResponseDTO api_1_30_updateDVCNTT_autoToken(String maYeuCau, String kyBaoCao, List<API_1_30_UPDATE_DVCNTT_DTO> dvcnttList);

    TKTTResponseDTO api_1_23_uploadToChuc_autoToken(String maYeuCau, String kyBaoCao, List<API_1_23_TOCHUC_DTO> dataList);
    TKTTResponseDTO api_1_24_uploadToChucNGGL_autoToken(String maYeuCau, String kyBaoCao, List<API_1_24_TOCHUC_NGGL_DTO> dataList);
    TKTTResponseDTO api_1_25_update_uploadToChucNGGL_autoToken(String maYeuCau, String kyBaoCao, List<API_1_25_UPDATE_TOCHUC_NGGL_DTO> dataList);
    TKTTResponseDTO api_1_26_update_uploadToChuc_autoToken(String maYeuCau, String kyBaoCao, List<API_1_26_UPDATE_TOCHUC_DTO> dataList);
}