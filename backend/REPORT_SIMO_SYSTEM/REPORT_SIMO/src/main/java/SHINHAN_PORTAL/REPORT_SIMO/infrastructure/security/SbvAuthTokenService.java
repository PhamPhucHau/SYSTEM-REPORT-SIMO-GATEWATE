package SHINHAN_PORTAL.REPORT_SIMO.infrastructure.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
public class SbvAuthTokenService {
    @Value("${app.user}")
    private String user;

    @Value("${app.password}")
    private String password;

    @Value("${app.key}")
    private String key;

    @Value("${app.secret}")
    private String secret;
    @Value("${app.simo_URL}")
    private String simo_URL ;
    private static final Logger logger = LoggerFactory.getLogger(SbvAuthTokenService.class);
    public String getSbvAuthToken() {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", getBasicAuthHeader());

        MultiValueMap<String, String> requestBody  = new LinkedMultiValueMap<>();
        requestBody.add("username", user);
        requestBody.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = rest.exchange(simo_URL +"/token", HttpMethod.POST,request, Map.class);
        return (String)response.getBody().get("access_token");
    }
    private String getBasicAuthHeader()
    {
        // Prevent String pool
        StringBuilder auth = new StringBuilder();
        auth.append(key);
        auth.append(":");
        auth.append(secret);
        return "Basic " + Base64.getEncoder().encodeToString(auth.toString().getBytes(StandardCharsets.UTF_8));
    }
    public String refreshSbvAuthToken(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();
        // Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", getBasicAuthHeader());
        // Body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("refresh_token", refreshToken);
        // Request
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(simo_URL+"/token", HttpMethod.POST, request, Map.class);
        return (String) response.getBody().get("access_token");
    }
}