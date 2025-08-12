package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.ReportSimoApplication;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.*;
import SHINHAN_PORTAL.REPORT_SIMO.application.exception.FileStorageException;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.SimoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.nio.file.*;
@Service
public class SimoServiceImpl implements SimoService {

    private final RestTemplate restTemplate;

    @Value("${simo.token.url}")
    private String tokenUrl;

    @Value("${simo.tktt.upload.url}")
    private String tkttUploadUrl;
    @Value("${simo.nggl1_7.upload.url}")
    private String tkttUploadUrl_api_1_7;
    @Value("${simo.api_1_8_update_nggl.url}")
    private String api_1_8_update_nggl_Url;
    @Value("${simo.api_1_9_update_tktt.url}")
    private String api_1_9_update_tktt_Url;
    @Value("${simo.api_1_23_upload_tochuc.url}")
    private String api_1_23_upload_tochuc_Url;
    @Value("${simo.api_1_24_upload_tochuc_nggl.url}")
    private String api_1_24_upload_tochuc_nggl_Url;
    @Value("${simo.api_1_25_update_upload_tochuc_nggl.url}")
    private String api_1_25_update_upload_tochuc_nggl_Url;
    @Value("${simo.api_1_26_update_upload_tochuc.url}")
    private String api_1_26_update_upload_tochuc_Url;
    @Value("${simo.api_1_27_upload_dvcntt.url}")
    private String api_1_27_upload_dvcntt_Url;
    
    @Value("${simo.api_1_28_upload_nggl_dvcntt.url}")
    private String api_1_28_upload_nggl_dvcntt_Url;
    
    @Value("${simo.api_1_29_update_nggl_dvcntt.url}")
    private String api_1_29_update_nggl_dvcntt_Url;
    
    @Value("${simo.api_1_30_update_dvcntt.url}")
    private String api_1_30_update_dvcntt_Url;
    @Value("${simo.username}")
    private String username;

    @Value("${simo.password}")
    private String password;

    @Value("${simo.consumer.key}")
    private String consumerKey;

    @Value("${simo.consumer.secret}")
    private String consumerSecret;

    private String cachedToken;
    private long tokenExpiryTime;
    @Value("${file.upload-dir}")
    private String uploadDir;

	private static final Logger logger = LoggerFactory.getLogger(ReportSimoApplication.class);
    public SimoServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public TokenResponseDTO getToken(String username, String password, String consumerKey, String consumerSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String auth = consumerKey + ":" + consumerSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        headers.set("Authorization", "Basic " + encodedAuth);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<TokenResponseDTO> response = restTemplate.postForEntity(tokenUrl, request, TokenResponseDTO.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                TokenResponseDTO tokenResponse = response.getBody();
                if (tokenResponse != null) {
                    cachedToken = tokenResponse.getAccessToken();
                    tokenExpiryTime = System.currentTimeMillis() + (tokenResponse.getExpiresIn() * 1000);
                }
                return tokenResponse;
            } else {
                throw new RuntimeException("Failed to get token: " + response.getStatusCode());
            }
        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof java.net.SocketTimeoutException) {
                throw new RuntimeException("Timeout khi gọi lấy token từ SIMO", e);
            } else if (e.getCause() instanceof java.net.ConnectException) {
                throw new RuntimeException("Không thể kết nối đến máy chủ SIMO", e);
            }
            throw new RuntimeException("Lỗi kết nối SIMO không xác định", e);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Lỗi phản hồi khi lấy token: " + e.getStatusCode(), e);
        }
    }

    private String getValidToken() {
        if (cachedToken == null || System.currentTimeMillis() >= tokenExpiryTime) {
            TokenResponseDTO tokenResponse = getToken(username, password, consumerKey, consumerSecret);
            return tokenResponse.getAccessToken();
        }
        return cachedToken;
    }

    @Override
    public TKTTResponseDTO uploadTKTTReport(String token, String maYeuCau, String kyBaoCao, List<TKTTRequestDTO> tkttList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        headers.set("maYeuCau", maYeuCau);
        headers.set("kyBaoCao", kyBaoCao);
        System.out.println(">>> Sending TKTT Report:");
        System.out.println(" - kyBaoCao = " + kyBaoCao);
        System.out.println(" - maYeuCau = " + maYeuCau);
        System.out.println(" - token = Bearer " + token);
        System.out.println(" - Headers = " + headers.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = "";
        try {
            jsonContent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tkttList);
        } catch (JsonProcessingException e) {
            // Xử lý lỗi nếu có
            logger.error("Lỗi khi chuyển tkttList thành JSON: " + e.getMessage());
            throw new RuntimeException(e);  // hoặc xử lý tùy bạn
        }
        try {
            // Tạo thư mục nếu chưa tồn tại
            Files.createDirectories(Paths.get(uploadDir));

            // Tạo đường dẫn đích, ví dụ: report-tktt.json
            String fileName = "API1_6_" +kyBaoCao.replace("/","_")+"_"+maYeuCau+".json";
            Path filePath = Paths.get(uploadDir, fileName);

            // Ghi nội dung JSON vào file
            Files.write(filePath, jsonContent.getBytes(StandardCharsets.UTF_8));

            logger.info(">>> JSON saved to file: " + filePath.toString());
        } catch (IOException e) {
            throw new FileStorageException("Không thể lưu file JSON", e);
        }
        try {
            String jsonBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tkttList);
            logger.info(">>> Request Body (JSON):");
            logger.info(jsonBody);
        } catch (JsonProcessingException e) {
            logger.info("Error while converting request body to JSON: " + e.getMessage());
        }
        HttpEntity<List<TKTTRequestDTO>> request = new HttpEntity<>(tkttList, headers);
        try {
            ResponseEntity<?> response = restTemplate.postForEntity(tkttUploadUrl, request, TKTTResponseDTO.class);
            logger.info("Response" + response.getBody().toString());
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Response API 1.6: " + response.getBody().toString());
                return (TKTTResponseDTO) response.getBody();
            } else {
                throw new RuntimeException("Failed to upload TKTT report: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                // Token expired, retry with new token
                cachedToken = null; // Clear cached token
                String newToken = getValidToken();
                return uploadTKTTReport(newToken, maYeuCau, kyBaoCao, tkttList);
            }
            throw new RuntimeException("Failed to upload TKTT report: " + e.getMessage());
        }
    }
    @Override
    public TKTTResponseDTO api_1_7_uploadNGGL(String token, String maYeuCau, String kyBaoCao, List<API_1_7_tktt_nggl_DT0> tkttList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        headers.set("maYeuCau", maYeuCau);
        headers.set("kyBaoCao", kyBaoCao);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = "";
        try {
            jsonContent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tkttList);
        } catch (JsonProcessingException e) {
            // Xử lý lỗi nếu có
            logger.error("Lỗi khi chuyển tkttList thành JSON: " + e.getMessage());
            throw new RuntimeException(e);  // hoặc xử lý tùy bạn
        }
        try {
            // Tạo thư mục nếu chưa tồn tại
            Files.createDirectories(Paths.get(uploadDir));

            // Tạo đường dẫn đích, ví dụ: report-tktt.json
            String fileName = "API1_7_" +kyBaoCao.replace("/","_")+"_"+maYeuCau+".json";
            Path filePath = Paths.get(uploadDir, fileName);

            // Ghi nội dung JSON vào file
            Files.write(filePath, jsonContent.getBytes(StandardCharsets.UTF_8));

            logger.info(">>> JSON saved to file: " + filePath.toString());
        } catch (IOException e) {
            throw new FileStorageException("Không thể lưu file JSON", e);
        }
        try {
            String jsonBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tkttList);
            logger.info(">>> Request Body (JSON):");
            logger.info(jsonBody);
        } catch (JsonProcessingException e) {
            logger.info("Error while converting request body to JSON: " + e.getMessage());
        }
        HttpEntity<List<API_1_7_tktt_nggl_DT0>> request = new HttpEntity<>(tkttList, headers);

        try {
            ResponseEntity<TKTTResponseDTO> response = restTemplate.postForEntity(tkttUploadUrl_api_1_7, request, TKTTResponseDTO.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to upload TKTT report: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                // Token expired, retry with new token
                cachedToken = null; // Clear cached token
                String newToken = getValidToken();
                return api_1_7_uploadNGGL(newToken, maYeuCau, kyBaoCao, tkttList);
            }
            throw new RuntimeException("Failed to upload TKTT report: " + e.getMessage());
        }
    }

    @Override
    public TKTTResponseDTO api_1_8_update_uploadNGGL(String token, String maYeuCau, String kyBaoCao, List<API_1_8_update_tktt_nggl_DT0> tkttList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        headers.set("maYeuCau", maYeuCau);
        headers.set("kyBaoCao", kyBaoCao);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = "";
        try {
            jsonContent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tkttList);
        } catch (JsonProcessingException e) {
            // Xử lý lỗi nếu có
            logger.error("Lỗi khi chuyển tkttList thành JSON: " + e.getMessage());
            throw new RuntimeException(e);  // hoặc xử lý tùy bạn
        }
        try {
            // Tạo thư mục nếu chưa tồn tại
            Files.createDirectories(Paths.get(uploadDir));

            // Tạo đường dẫn đích, ví dụ: report-tktt.json
            String fileName = "API1_8_" +kyBaoCao.replace("/","_")+"_"+maYeuCau+".json";
            Path filePath = Paths.get(uploadDir, fileName);

            // Ghi nội dung JSON vào file
            Files.write(filePath, jsonContent.getBytes(StandardCharsets.UTF_8));

            logger.info(">>> JSON saved to file: " + filePath.toString());
        } catch (IOException e) {
            throw new FileStorageException("Không thể lưu file JSON", e);
        }
        try {
            String jsonBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tkttList);
            logger.info(">>> Request Body (JSON):");
            logger.info(jsonBody);
        } catch (JsonProcessingException e) {
            logger.info("Error while converting request body to JSON: " + e.getMessage());
        }
        HttpEntity<List<API_1_8_update_tktt_nggl_DT0>> request = new HttpEntity<>(tkttList, headers);

        try {
            ResponseEntity<TKTTResponseDTO> response = restTemplate.postForEntity(api_1_8_update_nggl_Url, request, TKTTResponseDTO.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to upload TKTT report: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                // Token expired, retry with new token
                cachedToken = null; // Clear cached token
                String newToken = getValidToken();
                return api_1_8_update_uploadNGGL(newToken, maYeuCau, kyBaoCao, tkttList);
            }
            throw new RuntimeException("Failed to upload TKTT report: " + e.getMessage());
        }
    }

    @Override
    public TKTTResponseDTO api_1_9_update_uploadTKTT(String token, String maYeuCau, String kyBaoCao, List<API_1_9_update_tktt_nggl_DT0> tkttList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        headers.set("maYeuCau", maYeuCau);
        headers.set("kyBaoCao", kyBaoCao);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = "";
        try {
            jsonContent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tkttList);
        } catch (JsonProcessingException e) {
            // Xử lý lỗi nếu có
            logger.error("Lỗi khi chuyển tkttList thành JSON: " + e.getMessage());
            throw new RuntimeException(e);  // hoặc xử lý tùy bạn
        }
        try {
            // Tạo thư mục nếu chưa tồn tại
            Files.createDirectories(Paths.get(uploadDir));

            // Tạo đường dẫn đích, ví dụ: report-tktt.json
            String fileName = "API1_9_" +kyBaoCao.replace("/","_")+"_"+maYeuCau+".json";
            Path filePath = Paths.get(uploadDir, fileName);

            // Ghi nội dung JSON vào file
            Files.write(filePath, jsonContent.getBytes(StandardCharsets.UTF_8));

            logger.info(">>> JSON saved to file: " + filePath.toString());
        } catch (IOException e) {
            throw new FileStorageException("Không thể lưu file JSON", e);
        }
        try {
            String jsonBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tkttList);
            logger.info(">>> Request Body (JSON):");
            logger.info(jsonBody);
        } catch (JsonProcessingException e) {
            logger.info("Error while converting request body to JSON: " + e.getMessage());
        }
        HttpEntity<List<API_1_9_update_tktt_nggl_DT0>> request = new HttpEntity<>(tkttList, headers);

        try {
            ResponseEntity<TKTTResponseDTO> response = restTemplate.postForEntity(api_1_9_update_tktt_Url, request, TKTTResponseDTO.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Response:" + response.toString());
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to upload TKTT report: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                logger.info("UnAuthorization: " + e.getMessage().toString()); 
                // Token expired, retry with new token
                cachedToken = null; // Clear cached token
                String newToken = getValidToken();
                return api_1_9_update_uploadTKTT(newToken, maYeuCau, kyBaoCao, tkttList);
            }
            throw new RuntimeException("Failed to upload TKTT report: " + e.getMessage());
        }
    }

    public TKTTResponseDTO uploadTKTTReportAutoToken(String maYeuCau, String kyBaoCao, List<TKTTRequestDTO> tkttList) {
        String token = getValidToken();
        return uploadTKTTReport(token, maYeuCau, kyBaoCao, tkttList);
    }
    public TKTTResponseDTO api_1_7_uploadNGGL_autoToken(String maYeuCau, String kyBaoCao, List<API_1_7_tktt_nggl_DT0> tkttList) {
        String token = getValidToken();
        return api_1_7_uploadNGGL(token, maYeuCau, kyBaoCao, tkttList);
    }
    public TKTTResponseDTO api_1_8_update_uploadNGGL_autoToken(String maYeuCau, String kyBaoCao, List<API_1_8_update_tktt_nggl_DT0> tkttList) {
        String token = getValidToken();
        return api_1_8_update_uploadNGGL(token, maYeuCau, kyBaoCao, tkttList);
    }
    public TKTTResponseDTO api_1_9_TKTT_autoToken(String maYeuCau, String kyBaoCao, List<API_1_9_update_tktt_nggl_DT0> tkttList) {
        String token = getValidToken();
        return api_1_9_update_uploadTKTT(token, maYeuCau, kyBaoCao, tkttList);
    }
    // Phần mở rộng cho 4 API mới (1.27 đến 1.30)

@Override
public TKTTResponseDTO api_1_27_uploadDVCNTT(String token, String maYeuCau, String kyBaoCao, List<API_1_27_TT_DVCNTT_DTO> dvcnttList) {
    return postToSimoApi(token, maYeuCau, kyBaoCao, dvcnttList, api_1_27_upload_dvcntt_Url, "API1_27_");
}

@Override
public TKTTResponseDTO api_1_28_uploadNGGL_DVCNTT(String token, String maYeuCau, String kyBaoCao, List<API_1_28_TT_DVCNTT_NGGL_DTO> dvcnttList) {
    return postToSimoApi(token, maYeuCau, kyBaoCao, dvcnttList, api_1_28_upload_nggl_dvcntt_Url, "API1_28_");
}

@Override
public TKTTResponseDTO api_1_29_updateNGGL_DVCNTT(String token, String maYeuCau, String kyBaoCao, List<API_1_29_UPDATE_DVCNTT_NGGL_DTO> dvcnttList) {
    return postToSimoApi(token, maYeuCau, kyBaoCao, dvcnttList, api_1_29_update_nggl_dvcntt_Url, "API1_29_");
}

@Override
public TKTTResponseDTO api_1_30_updateDVCNTT(String token, String maYeuCau, String kyBaoCao, List<API_1_30_UPDATE_DVCNTT_DTO> dvcnttList) {
    return postToSimoApi(token, maYeuCau, kyBaoCao, dvcnttList, api_1_30_update_dvcntt_Url, "API1_30_");
}

@Override
public TKTTResponseDTO api_1_27_uploadDVCNTT_autoToken(String maYeuCau, String kyBaoCao, List<API_1_27_TT_DVCNTT_DTO> dvcnttList) {
    return api_1_27_uploadDVCNTT(getValidToken(), maYeuCau, kyBaoCao, dvcnttList);
}

@Override
public TKTTResponseDTO api_1_28_uploadNGGL_DVCNTT_autoToken(String maYeuCau, String kyBaoCao, List<API_1_28_TT_DVCNTT_NGGL_DTO> dvcnttList) {
    return api_1_28_uploadNGGL_DVCNTT(getValidToken(), maYeuCau, kyBaoCao, dvcnttList);
}

@Override
public TKTTResponseDTO api_1_29_updateNGGL_DVCNTT_autoToken(String maYeuCau, String kyBaoCao, List<API_1_29_UPDATE_DVCNTT_NGGL_DTO> dvcnttList) {
    return api_1_29_updateNGGL_DVCNTT(getValidToken(), maYeuCau, kyBaoCao, dvcnttList);
}

@Override
public TKTTResponseDTO api_1_30_updateDVCNTT_autoToken(String maYeuCau, String kyBaoCao, List<API_1_30_UPDATE_DVCNTT_DTO> dvcnttList) {
    return api_1_30_updateDVCNTT(getValidToken(), maYeuCau, kyBaoCao, dvcnttList);
}

@Override
public TKTTResponseDTO api_1_23_uploadToChuc_autoToken(String maYeuCau, String kyBaoCao, List<API_1_23_TOCHUC_DTO> dataList) {
    return postToSimoApi(getValidToken(), maYeuCau, kyBaoCao, dataList, api_1_23_upload_tochuc_Url, "API1_23_");
}
@Override
public TKTTResponseDTO api_1_24_uploadToChucNGGL_autoToken(String maYeuCau, String kyBaoCao, List<API_1_24_TOCHUC_NGGL_DTO> dataList) {
    return postToSimoApi(getValidToken(), maYeuCau, kyBaoCao, dataList, api_1_24_upload_tochuc_nggl_Url, "API1_24_");
}
@Override
public TKTTResponseDTO api_1_25_update_uploadToChucNGGL_autoToken(String maYeuCau, String kyBaoCao, List<API_1_25_UPDATE_TOCHUC_NGGL_DTO> dataList) {
    return postToSimoApi(getValidToken(), maYeuCau, kyBaoCao, dataList, api_1_25_update_upload_tochuc_nggl_Url, "API1_25_");
}
@Override
public TKTTResponseDTO api_1_26_update_uploadToChuc_autoToken(String maYeuCau, String kyBaoCao, List<API_1_26_UPDATE_TOCHUC_DTO> dataList) {
    return postToSimoApi(getValidToken(), maYeuCau, kyBaoCao, dataList, api_1_26_update_upload_tochuc_Url, "API1_26_");
}

// --- Đoạn này bạn nên đặt trong private method chung ---

private <T> TKTTResponseDTO postToSimoApi(String token, String maYeuCau, String kyBaoCao, List<T> listData, String url, String prefixFileName) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + token);
    headers.set("maYeuCau", maYeuCau);
    headers.set("kyBaoCao", kyBaoCao);

    ObjectMapper objectMapper = new ObjectMapper();
    try {
        Files.createDirectories(Paths.get(uploadDir));
        String fileName = prefixFileName + kyBaoCao.replace("/", "_") + "_" + maYeuCau + ".json";
        Path filePath = Paths.get(uploadDir, fileName);

        String jsonContent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listData);
        Files.write(filePath, jsonContent.getBytes(StandardCharsets.UTF_8));
        logger.info(">>> JSON saved to file: " + filePath);

        HttpEntity<List<T>> request = new HttpEntity<>(listData, headers);
        ResponseEntity<TKTTResponseDTO> response = restTemplate.postForEntity(url, request, TKTTResponseDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to upload report: " + response.getStatusCode());
        }
    } catch (IOException e) {
        throw new FileStorageException("Không thể ghi file JSON", e);
    } catch (HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            cachedToken = null;
            String newToken = getValidToken();
            return postToSimoApi(newToken, maYeuCau, kyBaoCao, listData, url, prefixFileName);
        }
        throw new RuntimeException("Lỗi khi goi SIMO API: " + e.getMessage());
    }
}



}