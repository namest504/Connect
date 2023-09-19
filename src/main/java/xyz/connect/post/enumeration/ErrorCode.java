package xyz.connect.post.enumeration;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //Common
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Z001", "Unknown Server Error"),
    INVALID_API_PARAMETER(HttpStatus.BAD_REQUEST, "Z002", ""), //ExceptionHandler 에서 메시지 작성

    //Post
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "P001", "게시글에 대한 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
