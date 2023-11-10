package xyz.connect.user.exception;

import lombok.Getter;

@Getter
public class UserApiException extends RuntimeException {

    private final ErrorCode errorCode;
    
    public UserApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
