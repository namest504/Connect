package xyz.connect.post.enumeration;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //Common
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Z001", "Unknown Server Error"),
    THIRD_PARTY_API_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Z002", "내부 API 오류. 관리자에게 문의하세요."),
    NOT_SUPPORTED_METHOD(HttpStatus.BAD_REQUEST, "Z003", "지원하지 않는 Http method 입니다."),

    //Post API
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "P001", "리소스에 대한 권한이 없습니다."),
    MAX_UPLOAD_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "P002", "파일 업로드 용량이 초과되었습니다."),
    INVALID_MEDIA_TYPE(HttpStatus.BAD_REQUEST, "P003", "허용되지 않는 이미지 미디어 타입 입니다."),
    INVALID_API_PARAMETER(HttpStatus.BAD_REQUEST, "Z004", ""), //ExceptionHandler 에서 메시지 작성
    EXCEED_MAX_UPLOAD_COUNT(HttpStatus.BAD_REQUEST, "Z005", ""),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Z006", "리소스가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
