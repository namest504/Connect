package xyz.connect.post.custom_exception;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.connect.post.enumeration.ErrorCode;
import xyz.connect.post.web.model.response.ErrorResponse;

@Slf4j
@RestControllerAdvice(basePackages = "xyz.connect.post.web.controller")
public class ControllerExceptionHandler {

    // Api 비즈니스 로직 예외
    @ExceptionHandler(PostApiException.class)
    public ResponseEntity<ErrorResponse> unauthorizedExceptionHandler(PostApiException e) {
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

    //알 수 없는 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unknownExceptionHandler(Exception e) {
        log.warn("[Unexpected Error] " + e.getMessage());
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
