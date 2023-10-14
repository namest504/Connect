package xyz.connect.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {


    //Common
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Z001", "Unknown Server Error"),
    THIRD_PARTY_API_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Z002", ""),
    NOT_SUPPORTED_METHOD(HttpStatus.BAD_REQUEST, "Z003", "지원하지 않는 Http method 입니다."),
    NOT_SUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Z004", "사용 할수 없는 MediaType 입니다."),
    INVALID_API_PARAMETER(HttpStatus.BAD_REQUEST, "Z005", "파라미터를 확인해주세요."),
    //Sign-up
    CONFLICT(HttpStatus.CONFLICT, "Z100", "중복된 이메일 입니다.");

    //Login


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
