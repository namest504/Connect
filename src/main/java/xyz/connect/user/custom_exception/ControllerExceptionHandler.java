package xyz.connect.user.custom_exception;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.connect.post.enumeration.ErrorCode;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    // Api 비즈니스 로직 예외
    @ExceptionHandler(UserApiException.class)
    public ResponseEntity<ErrorResponse> unauthorizedExceptionHandler(UserApiException e) {
        return makeErrorResponseEntity(e.getErrorCode());
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
