package com.lim1t.email.global.exception;

import lombok.Getter;

@Getter
public class MailApiException extends RuntimeException {

    private final ErrorCode errorCode;
    
    public MailApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
