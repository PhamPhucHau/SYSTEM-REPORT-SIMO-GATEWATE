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
}