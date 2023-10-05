package xyz.connect.user.custom_exception;

import lombok.Getter;
import xyz.connect.post.enumeration.ErrorCode;

@Getter
public class UserApiException extends RuntimeException {

    private final ErrorCode errorCode;

    public UserApiException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public UserApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
