package SHINHAN_PORTAL.REPORT_SIMO.application.exception;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.coyote.BadRequestException;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(ResourceNotFoundException ex) {
        return buildResponse(ex.getMessage(), "99", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        return buildResponse(ex.getMessage(), "99", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return buildResponse("An unexpected error occurred","99", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ResponseEntity<ErrorResponse> buildResponse(String message, String error_code, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(message,error_code, LocalDateTime.now()) ;
        return new ResponseEntity<>(errorResponse, status);
    }
    @ExceptionHandler(io.jsonwebtoken.ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwt(ExpiredJwtException ex) {
        return buildResponse("Token đã hết hạn", "401", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(io.jsonwebtoken.SignatureException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJwt(SignatureException ex) {
        return buildResponse("Token không hợp lệ", "401", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        return buildResponse("Bạn không có quyền truy cập", "403", HttpStatus.FORBIDDEN);
    }
    // Bắt lỗi Token sai chữ ký
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSignature(SignatureException ex) {
        return buildResponse("Token không hợp lệ (sai chữ ký)", "401", HttpStatus.UNAUTHORIZED);
    }

    // Bắt lỗi Token sai định dạng (Malformed), không hỗ trợ, hoặc rỗng
    @ExceptionHandler({MalformedJwtException.class, UnsupportedJwtException.class})
    public ResponseEntity<ErrorResponse> handleBadJwtFormat(Exception ex) {
        return buildResponse("Token sai định dạng hoặc không hợp lệ", "401", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler( {IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleBadFormat(Exception ex) {
        return buildResponse("Format không hợp lệ", "400", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthenticationException ex) {
        return buildResponse("Chưa xác thực hoặc token lỗi", "401", HttpStatus.UNAUTHORIZED);
    }

}
