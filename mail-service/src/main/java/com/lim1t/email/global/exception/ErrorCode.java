package com.lim1t.email.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {


    //Common
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "M001", "Unknown Server Error"),
    THIRD_PARTY_API_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "M002", ""),
    NOT_SUPPORTED_METHOD(HttpStatus.BAD_REQUEST, "M003", "지원하지 않는 Http method 입니다."),
    INVALID_API_PARAMETER(HttpStatus.BAD_REQUEST, "M004", "파라미터를 확인해주세요."),
    UNABLE_ACCESS_SERVICE(HttpStatus.BAD_REQUEST, "M005", "서비스 서버에 문제가 발생했습니다."),

    //SendMail
    FAILED_SEND_MAIL(HttpStatus.BAD_REQUEST, "M100", "이메일 전송에 실패했습니다."),

    //Mail
    NO_CERTIFICATION(HttpStatus.BAD_REQUEST, "M201", "인증 링크가 만료되었거나, 잘못된 코드입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
