package xyz.connect.post.custom_exception;

import com.amazonaws.AmazonServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import xyz.connect.post.enumeration.ErrorCode;
import xyz.connect.post.web.model.response.ErrorResponse;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    // Api 비즈니스 로직 예외
    @ExceptionHandler(PostApiException.class)
    public ResponseEntity<ErrorResponse> unauthorizedExceptionHandler(PostApiException e) {
        return makeErrorResponseEntity(e.getErrorCode());
    }


    // 게시글 이미지 용량 초과
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> maxUploadSizeExceededException() {
        ErrorCode errorCode = ErrorCode.MAX_UPLOAD_SIZE_EXCEEDED;
        return makeErrorResponseEntity(errorCode, errorCode.getMessage() + " 최대 파일 사이즈: " + maxFileSize);
    }
  
    //API 요청 파라미터 값의 유효성 위반
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalidArgumentExceptionHandler(
            MethodArgumentNotValidException e) {
        String msg = e.getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return makeErrorResponseEntity(ErrorCode.INVALID_API_PARAMETER, msg);
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<ErrorResponse> amazonServiceExceptionHandler(AmazonServiceException e) {
        log.warn("[AmazonS3 API Exception] " + e.getMessage());
        return makeErrorResponseEntity(ErrorCode.THIRD_PARTY_API_EXCEPTION, e.getMessage());
    }

    //지원하지 않는 Http method
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> notSupportedMethodExceptionHandler() {
        return makeErrorResponseEntity(ErrorCode.NOT_SUPPORTED_METHOD);
    }

    //알 수 없는 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unknownExceptionHandler(Exception e) {
        log.warn("[Unexpected Error] " + e.getMessage());
        e.printStackTrace();
        return makeErrorResponseEntity(ErrorCode.SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(ErrorCode errorCode) {
        return makeErrorResponseEntity(errorCode, errorCode.getMessage());
    }

    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(ErrorCode errorCode,
            String message) {
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getStatus().toString(), message,
                errorCode.getCode());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }
}
