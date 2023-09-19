package xyz.connect.post.custom_exception;

import lombok.Getter;
import xyz.connect.post.enumeration.ErrorCode;

@Getter
public class PostApiException extends RuntimeException {

    private final ErrorCode errorCode;

    public PostApiException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public PostApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
