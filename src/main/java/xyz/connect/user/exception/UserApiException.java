package xyz.connect.user.exception;

import lombok.Getter;

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
